/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Controller;

import KR.Models.SparqlModel;
import KR.GeneralView.GeneralView;
import javafx.event.ActionEvent;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author YoucTagh
 */
public class SparqlController {

    SparqlModel sparqlModel;
    GeneralView generalView;

    private static final Pattern XML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*)(\\w+)([^<>]*)(\\h*/?>))"
            + "|(?<COMMENT><!--[^<>]+-->)");

    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")");

    private static final int GROUP_OPEN_BRACKET = 2;
    private static final int GROUP_ELEMENT_NAME = 3;
    private static final int GROUP_ATTRIBUTES_SECTION = 4;
    private static final int GROUP_CLOSE_BRACKET = 5;
    private static final int GROUP_ATTRIBUTE_NAME = 1;
    private static final int GROUP_EQUAL_SYMBOL = 2;
    private static final int GROUP_ATTRIBUTE_VALUE = 3;

    private static final String sampleCode = String.join("\n", new String[]{
        "SELECT ?name1 ?name2 ",
        "WHERE { ",
        "?name1 Ns:shoesize ?a1 .",
        " ?name2 Ns:shoesize ?a2 .",
        "FILTER(",
        "(xsd:integer(?a1)=xsd:integer(?a2))",
        "&&(?name1!=?name2)}"
    });

    public SparqlController(GeneralView generalView, SparqlModel sparqlModel) {

        this.sparqlModel = sparqlModel;
        this.generalView = generalView;

        initCodeAreaSparqlQuery();
        initCodeAreaOntoTextViewer();
        initClearFeedBackButton();
        initExecuteQueryButton();
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {

        Matcher matcher = XML_TAG.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            if (matcher.group("COMMENT") != null) {
                spansBuilder.add(Collections.singleton("comment"), matcher.end() - matcher.start());
            } else {
                if (matcher.group("ELEMENT") != null) {
                    String attributesText = matcher.group(GROUP_ATTRIBUTES_SECTION);

                    spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_OPEN_BRACKET) - matcher.start(GROUP_OPEN_BRACKET));
                    spansBuilder.add(Collections.singleton("anytag"), matcher.end(GROUP_ELEMENT_NAME) - matcher.end(GROUP_OPEN_BRACKET));

                    if (!attributesText.isEmpty()) {

                        lastKwEnd = 0;

                        Matcher amatcher = ATTRIBUTES.matcher(attributesText);
                        while (amatcher.find()) {
                            spansBuilder.add(Collections.emptyList(), amatcher.start() - lastKwEnd);
                            spansBuilder.add(Collections.singleton("attribute"), amatcher.end(GROUP_ATTRIBUTE_NAME) - amatcher.start(GROUP_ATTRIBUTE_NAME));
                            spansBuilder.add(Collections.singleton("tagmark"), amatcher.end(GROUP_EQUAL_SYMBOL) - amatcher.end(GROUP_ATTRIBUTE_NAME));
                            spansBuilder.add(Collections.singleton("avalue"), amatcher.end(GROUP_ATTRIBUTE_VALUE) - amatcher.end(GROUP_EQUAL_SYMBOL));
                            lastKwEnd = amatcher.end();
                        }
                        if (attributesText.length() > lastKwEnd) {
                            spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
                        }
                    }

                    lastKwEnd = matcher.end(GROUP_ATTRIBUTES_SECTION);

                    spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_CLOSE_BRACKET) - lastKwEnd);
                }
            }
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private void initCodeAreaSparqlQuery() {
        this.generalView.getCodeAreaSparqlQuery().setParagraphGraphicFactory(LineNumberFactory.get(this.generalView.getCodeAreaSparqlQuery()));

        this.generalView.getCodeAreaSparqlQuery().textProperty().addListener((obs, oldText, newText) -> {
            this.generalView.getCodeAreaSparqlQuery().setStyleSpans(0, computeHighlighting(newText));
        });
        this.generalView.getCodeAreaSparqlQuery().replaceText(0, 0, sampleCode);
        this.generalView.getCodeAreaSparqlQuery().setPrefHeight(300);
        this.generalView.getQueryHolder().getChildren().add(0, this.generalView.getCodeAreaSparqlQuery());
    }

    private void initCodeAreaOntoTextViewer() {
        this.generalView.getCodeAreaOntoTextViewer().setParagraphGraphicFactory(LineNumberFactory.get(this.generalView.getCodeAreaOntoTextViewer()));

        this.generalView.getCodeAreaOntoTextViewer().textProperty().addListener((obs, oldText, newText) -> {
            this.generalView.getCodeAreaOntoTextViewer().setStyleSpans(0, computeHighlighting(newText));
        });
        this.generalView.getCodeAreaOntoTextViewer().setPrefSize(this.generalView.getOntoTextViewerHolder().getWidth(),
                this.generalView.getOntoTextViewerHolder().getHeight());
        this.generalView.getOntoTextViewerHolder().getChildren().add(this.generalView.getCodeAreaOntoTextViewer());
    }

    private void initClearFeedBackButton() {
        this.generalView.getClearFeedBackButton().addEventHandler(ActionEvent.ACTION, (e) -> {
            this.generalView.getSparqlFeedBack().setText("");
        });
    }

    private void initExecuteQueryButton() {
        this.generalView.getExecuteSpQueryButton().addEventHandler(ActionEvent.ACTION, (e) -> {
            this.generalView.getSparqlFeedBack().setText(this.sparqlModel.requete(
                    this.generalView.getCodeAreaOntoTextViewer().getText(), this.generalView.getCodeAreaSparqlQuery().getText()));
        });
    }
}

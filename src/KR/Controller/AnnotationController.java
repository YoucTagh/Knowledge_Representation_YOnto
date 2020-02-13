/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Controller;

import KR.GeneralView.GeneralView;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

/**
 *
 * @author YoucTagh
 */
public class AnnotationController {

    public static boolean isTabActif;
    static GeneralView generalView;

    public AnnotationController(GeneralView generalView) {
        this.generalView = generalView;
        isTabActif = false;
        generalView.getAnnotationTab().selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                isTabActif = newValue;
            }

        });

    }

    public static void getIndividualAnnotation(String individualName) {
        HashMap<String, String> hm = new HashMap<>();
        generalView.getIndividualInfoVbox().getChildren().clear();
        String file = "file:\\" + generalView.getOntoPath().getText();
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
        m.read(file, "RDF/XML");
        ExtendedIterator classes = m.listClasses();
        while (classes.hasNext()) {

            OntClass currentClass = (OntClass) classes.next();
            ExtendedIterator instances = currentClass.listInstances();
            while (instances.hasNext()) {
                Individual thisInstance = (Individual) instances.next();

                if (thisInstance.getLocalName().equals(individualName)) {

                    for (StmtIterator stmts = thisInstance.listProperties(); stmts.hasNext();) {
                        thisInstance = stmts.next().getSubject().as(Individual.class);

                        System.out.println("  " + thisInstance.getURI());

                        for (StmtIterator j = thisInstance.listProperties(); j.hasNext();) {
                            Statement s = j.next();
                            if (hm.containsKey(s.getPredicate().getLocalName())) {
                                if (s.getPredicate().getLocalName().equals("type")) {
                                    if (!hm.containsKey(s.getObject().toString())) {
                                        hm.put(s.getObject().toString(), "");
                                    } else {
                                        continue;
                                    }
                                }
                                else{
                                    continue;
                                }
                            }
                            hm.put(s.getPredicate().getLocalName(), "");
                            System.out.print("    " + s.getPredicate().getLocalName() + " -> ");
                            String label = s.getPredicate().getLocalName() + " -> ";

                            if (s.getObject().isLiteral()) {
                                System.out.println(s.getLiteral().getLexicalForm());
                                Label l = new Label(label + s.getLiteral().getLexicalForm());
                                generalView.getIndividualInfoVbox().getChildren().add(l);
                            } else {
                                System.out.println(s.getObject().toString());
                                Label l = new Label(label + s.getObject());
                                generalView.getIndividualInfoVbox().getChildren().add(l);
                            }
                        }
                    }
                    return;
                }

            }
        }
    }

    public static void getClassAnnotation(String className) {
        generalView.getClassInfoVbox().getChildren().clear();
        String file = "file:\\" + generalView.getOntoPath().getText();
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
        m.read(file, "RDF/XML");
        ExtendedIterator classes = m.listClasses();
        while (classes.hasNext()) {

            OntClass currentClass = (OntClass) classes.next();

            if (currentClass.getLocalName().equals(className)) {

                for (StmtIterator j = currentClass.listProperties(); j.hasNext();) {
                    Statement s = j.next();
                    System.out.print("    " + s.getPredicate().getLocalName() + " -> ");
                    String label = s.getPredicate().getLocalName() + " -> ";
                    if (s.getObject().isLiteral()) {
                        System.out.println(s.getLiteral().getLexicalForm());
                        Label l = new Label(label + s.getLiteral().getLexicalForm());
                        generalView.getClassInfoVbox().getChildren().add(l);
                    } else {
                        System.out.println(s.getObject());
                        Label l = new Label(label + s.getObject());
                        generalView.getClassInfoVbox().getChildren().add(l);

                    }
                }

                return;
            }

        }
    }
}

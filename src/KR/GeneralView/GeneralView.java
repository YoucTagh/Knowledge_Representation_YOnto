/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.GeneralView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;

/**
 *
 * @author YoucTagh
 */
public class GeneralView {

    public static boolean isOntoCharged = false;

    public GeneralView() {
        codeAreaSparqlQuery = new CodeArea();
        codeAreaOntoTextViewer = new CodeArea();
    }

    private CodeArea codeAreaSparqlQuery;

    private CodeArea codeAreaOntoTextViewer;

    @FXML
    private Button ApplyChangesButton;

    @FXML
    private VBox QueryHolder;

    @FXML
    private MenuItem MenuOpenFile;

    @FXML
    private Menu OpenRecentMenu;

    @FXML
    private Button ExecuteSpQueryButton;

    @FXML
    private Button ClearFeedBackButton;

    @FXML
    private Label SparqlFeedBack;

    @FXML
    private Pane OntoTextViewerHolder;

    @FXML
    private TreeView<String> OntoTreeView;

    @FXML
    private StackPane GraphVisualizerZone;

    @FXML
    private Tab StartVisButton;

    @FXML
    private Label isCharged;

    @FXML
    private Label OntoPath;

    @FXML
    private VBox OntoIndividuals;

    @FXML
    private VBox classInfoVbox;

    @FXML
    private VBox individualInfoVbox;

    @FXML
    private Tab AnnotationTab;

    public Tab getAnnotationTab() {
        return AnnotationTab;
    }

    public void setAnnotationTab(Tab AnnotationTab) {
        this.AnnotationTab = AnnotationTab;
    }
    
    

    public StackPane getGraphVisualizerZone() {
        return GraphVisualizerZone;
    }

    public void setGraphVisualizerZone(StackPane GraphVisualizerZone) {
        this.GraphVisualizerZone = GraphVisualizerZone;
    }

    public Tab getStartVisButton() {
        return StartVisButton;
    }

    public void setStartVisButton(Tab StartVisButton) {
        this.StartVisButton = StartVisButton;
    }

    public VBox getQueryHolder() {
        return QueryHolder;
    }

    public void setQueryHolder(VBox QueryHolder) {
        this.QueryHolder = QueryHolder;
    }

    public Button getExecuteSpQueryButton() {
        return ExecuteSpQueryButton;
    }

    public Label getOntoPath() {
        return OntoPath;
    }

    public void setOntoPath(Label OntoPath) {
        this.OntoPath = OntoPath;
    }

    public void setExecuteSpQueryButton(Button ExecuteSpQueryButton) {
        this.ExecuteSpQueryButton = ExecuteSpQueryButton;
    }

    public Button getClearFeedBackButton() {
        return ClearFeedBackButton;
    }

    public void setClearFeedBackButton(Button ClearFeedBackButton) {
        this.ClearFeedBackButton = ClearFeedBackButton;
    }

    public Label getSparqlFeedBack() {
        return SparqlFeedBack;
    }

    public void setSparqlFeedBack(Label SparqlFeedBack) {
        this.SparqlFeedBack = SparqlFeedBack;
    }

    public MenuItem getMenuOpenFile() {
        return MenuOpenFile;
    }

    public void setMenuOpenFile(MenuItem MenuOpenFile) {
        this.MenuOpenFile = MenuOpenFile;
    }

    public CodeArea getCodeAreaSparqlQuery() {
        return codeAreaSparqlQuery;
    }

    public void setCodeAreaSparqlQuery(CodeArea codeAreaSparqlQuery) {
        this.codeAreaSparqlQuery = codeAreaSparqlQuery;
    }

    public CodeArea getCodeAreaOntoTextViewer() {
        return codeAreaOntoTextViewer;
    }

    public void setCodeAreaOntoTextViewer(CodeArea codeAreaOntoTextViewer) {
        this.codeAreaOntoTextViewer = codeAreaOntoTextViewer;
    }

    public Pane getOntoTextViewerHolder() {
        return OntoTextViewerHolder;
    }

    public void setOntoTextViewerHolder(Pane OntoTextViewerHolder) {
        this.OntoTextViewerHolder = OntoTextViewerHolder;
    }

    public TreeView<String> getOntoTreeView() {
        return OntoTreeView;
    }

    public void setOntoTreeView(TreeView<String> OntoTreeView) {
        this.OntoTreeView = OntoTreeView;
    }

    public Button getApplyChangesButton() {
        return ApplyChangesButton;
    }

    public void setApplyChangesButton(Button ApplyChangesButton) {
        this.ApplyChangesButton = ApplyChangesButton;
    }

    public Label getIsCharged() {
        return isCharged;
    }

    public void setIsCharged(Label isCharged) {
        this.isCharged = isCharged;
    }

    public Menu getOpenRecentMenu() {
        return OpenRecentMenu;
    }

    public void setOpenRecentMenu(Menu OpenRecentMenu) {
        this.OpenRecentMenu = OpenRecentMenu;
    }

    public VBox getOntoIndividuals() {
        return OntoIndividuals;
    }

    public void setOntoIndividuals(VBox OntoIndividuals) {
        this.OntoIndividuals = OntoIndividuals;
    }

    public VBox getClassInfoVbox() {
        return classInfoVbox;
    }

    public void setClassInfoVbox(VBox classInfoVbox) {
        this.classInfoVbox = classInfoVbox;
    }

    public VBox getIndividualInfoVbox() {
        return individualInfoVbox;
    }

    public void setIndividualInfoVbox(VBox individualInfoVbox) {
        this.individualInfoVbox = individualInfoVbox;
    }

}

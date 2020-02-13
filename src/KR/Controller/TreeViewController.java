/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Controller;

import KR.Models.TreeViewModel;
import KR.GeneralView.GeneralView;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;

/**
 *
 * @author YoucTagh
 */
public class TreeViewController {

    TreeViewModel treeViewModel;
    static GeneralView generalView;

    public TreeViewController(GeneralView generalView, TreeViewModel treeViewModel) {

        this.treeViewModel = treeViewModel;
        this.generalView = generalView;
    }

    public static void buildTreeView(String file) {

        TreeItem<String> root = new TreeItem<>("Owl:Thing");
        generalView.getOntoTreeView().setRoot(root);

        generalView.getOntoTreeView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;

                if (selectedItem != null && !selectedItem.getValue().equals("Owl:Thing")) {
                    System.out.println("Selected Class : " + selectedItem.getValue());
                    getIndividuals(selectedItem.getValue());
                }

            }

        });

        buildTreeViewRec(file, root, null);
    }

    public static void buildTreeViewRec(String file, TreeItem<String> parent, OntClass parentS) {
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
        m.read("file:" + file, "RDF/XML");

        if (parentS == null) {
            ExtendedIterator classes = m.listClasses();
            while (classes.hasNext()) {
                OntClass currentClass = (OntClass) classes.next();
                if (!currentClass.hasSuperClass()) {
                    TreeItem<String> node = new TreeItem<>(currentClass.getLocalName());
                    parent.getChildren().add(node);
                    buildTreeViewRec(file, node, currentClass);
                }
            }
        } else {
            ExtendedIterator classes = m.listClasses();
            while (classes.hasNext()) {
                OntClass currentClass = (OntClass) classes.next();
                if (currentClass.hasSuperClass(parentS)) {
                    TreeItem<String> node = new TreeItem<>(currentClass.getLocalName());
                    parent.getChildren().add(node);
                    buildTreeViewRec(file, node, currentClass);
                }
            }
        }

    }
    

    private static void getIndividuals(String className) {


        
        generalView.getOntoIndividuals().getChildren().clear();
        String file = "file:\\" + generalView.getOntoPath().getText();
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
        m.read(file, "RDF/XML");
        ExtendedIterator classes = m.listClasses();
        while (classes.hasNext()) {
            OntClass currentClass = (OntClass) classes.next();
            if (currentClass.getLocalName().equals(className)) {

                ExtendedIterator instances = currentClass.listInstances();
                while (instances.hasNext()) {
                    Individual thisInstance = (Individual) instances.next();
                    System.out.println("Has Individuals : " + thisInstance.getLocalName());
                    populateIndividualsBox(thisInstance.getLocalName());

                }
                AnnotationController.getClassAnnotation(className);
                return;
            }
        }
    }

    private static void populateIndividualsBox(String individual) {
        Button ind = new Button(individual);
        ind.setPrefSize(generalView.getOntoIndividuals().getWidth(), 10);
        ind.addEventHandler(ActionEvent.ACTION, (e) -> {
            AnnotationController.getIndividualAnnotation(individual);
        });
        generalView.getOntoIndividuals().getChildren().add(ind);
    }

}

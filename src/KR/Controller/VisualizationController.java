/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Controller;

import KR.Models.VisualizationModel;
import KR.GeneralView.GeneralView;
import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.GrappaAdapter;
import att.grappa.GrappaPanel;
import att.grappa.GrappaSupport;
import att.grappa.Node;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;

/**
 *
 * @author YoucTagh
 */
public class VisualizationController {

    VisualizationModel visualizationModel;
    static GeneralView generalView;

    public VisualizationController(GeneralView generalView, VisualizationModel visualizationModel) {

        this.visualizationModel = visualizationModel;
        this.generalView = generalView;

        //initVisualizationTab();
    }

    private void initVisualizationTab() {
        generalView.getStartVisButton().selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && generalView.isOntoCharged) {

                }

            }
        });
    }

    public static void buildGraph(String file) {

        SwingNode s = new SwingNode();
        Graph g = new Graph("Graphique");
        Node n = new Node(g);
        n.setAttribute("color", "yellow");
        n.setAttribute("style", "filled");
        n.setAttribute("label", "Owl:Thing");

        buildGraphRec(file, n, null, g, new HashMap<>());

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("bin/dot.exe");//path to dot
            GrappaSupport.filterGraph(g, proc);//att.grappa.GrappaSupport;

        } catch (Exception ex) {
        }
        GrappaPanel graphPane = new GrappaPanel(g);
        graphPane.addGrappaListener(new GrappaAdapter());

        s.setContent(graphPane);

        generalView.getGraphVisualizerZone().getChildren().add(s);
    }

    public static void buildGraphRec(String file, Node parent, OntClass parentS, Graph g, HashMap<String, Node> hs) {
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
        m.read("file:" + file, "RDF/XML");

        if (parentS == null) {
            ExtendedIterator classes = m.listClasses();
            while (classes.hasNext()) {
                OntClass currentClass = (OntClass) classes.next();
                if (!currentClass.hasSuperClass()) {
                    Node n;
                    if (hs.containsKey(currentClass.getLocalName())) {
                        n = hs.get(currentClass.getLocalName());
                    } else {
                        n = new Node(g);
                        n.setAttribute("color", "yellow");
                        n.setAttribute("style", "filled");
                        n.setAttribute("label", currentClass.getLocalName());
                        hs.put(currentClass.getLocalName(), n);
                    }
                    Edge e1 = new Edge(g, parent, n);
                    e1.setAttribute("color", "black");
                    e1.setAttribute("label", "Has SubClass");

                    ExtendedIterator instances = currentClass.listInstances();
                    while (instances.hasNext()) {
                        Individual thisInstance = (Individual) instances.next();
                        Node n2;
                        if (hs.containsKey(thisInstance.getLocalName())) {
                            n2 = hs.get(thisInstance.getLocalName());
                        } else {
                            n2 = new Node(g);
                            n2.setAttribute("color", "green");
                            n2.setAttribute("style", "filled");
                            n2.setAttribute("label", thisInstance.getLocalName());
                            n2.setAttribute("shape", "box");
                            hs.put(thisInstance.getLocalName(), n2);
                        }
                        Edge e2 = new Edge(g, n, n2);//import att.grappa.Edge;
                        e2.setAttribute("color", "blue");
                        e2.setAttribute("label", "Has Instance");
                    }

                    buildGraphRec(file, n, currentClass, g, hs);

                }
            }
        } else {

            ExtendedIterator classes = m.listClasses();
            while (classes.hasNext()) {
                OntClass currentClass = (OntClass) classes.next();
                if (currentClass.hasSuperClass(parentS)) {

                    Node n;
                    if (hs.containsKey(currentClass.getLocalName())) {
                        n = hs.get(currentClass.getLocalName());
                    } else {
                        n = new Node(g);
                        n.setAttribute("color", "yellow");
                        n.setAttribute("style", "filled");
                        n.setAttribute("label", currentClass.getLocalName());
                        hs.put(currentClass.getLocalName(), n);
                    }
                    Edge e1 = new Edge(g, parent, n);
                    e1.setAttribute("color", "black");
                    e1.setAttribute("label", "Has SubClass");

                    ExtendedIterator instances = currentClass.listInstances();
                    while (instances.hasNext()) {
                        Individual thisInstance = (Individual) instances.next();

                        Node n2;
                        if (hs.containsKey(thisInstance.getLocalName())) {
                            n2 = hs.get(thisInstance.getLocalName());
                        } else {
                            n2 = new Node(g);
                            n2.setAttribute("color", "green");
                            n2.setAttribute("style", "filled");
                            n2.setAttribute("label", thisInstance.getLocalName());
                            n2.setAttribute("shape", "box");
                            hs.put(thisInstance.getLocalName(), n2);
                        }

                        Edge e2 = new Edge(g, n, n2);//import att.grappa.Edge;
                        e2.setAttribute("color", "blue");
                        e2.setAttribute("label", "Has Instance");
                    }

                    buildGraphRec(file, n, currentClass, g, hs);

                }

            }

        }

    }

}

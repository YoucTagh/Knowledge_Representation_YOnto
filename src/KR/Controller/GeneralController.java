/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Controller;

import KR.Models.MenuModel;
import KR.Models.SparqlModel;
import KR.Models.TreeViewModel;
import KR.Models.VisualizationModel;
import KR.GeneralView.GeneralView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.annotation.Generated;

/**
 * FXML Controller class
 *
 * @author YoucTagh
 */
public class GeneralController {

    public GeneralController(GeneralView generalView) {

        SparqlModel sparqlModel = new SparqlModel();
        SparqlController c = new SparqlController(generalView, sparqlModel);

        MenuModel menuModel = new MenuModel();
        MenuController menuController = new MenuController(generalView, menuModel);

        TreeViewModel treeViewModel = new TreeViewModel();
        TreeViewController treeViewController = new TreeViewController(generalView, treeViewModel);

        VisualizationModel visualizationModel = new VisualizationModel();
        VisualizationController visualizationController = new VisualizationController(generalView, visualizationModel);

        AnnotationController annotationController = new AnnotationController(generalView);

    }


}

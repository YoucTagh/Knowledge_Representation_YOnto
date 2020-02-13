/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Controller;

import KR.Models.MenuModel;
import KR.GeneralView.GeneralView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 *
 * @author YoucTagh
 */
public class MenuController {

    MenuModel menuModel;
    GeneralView generalView;

    public MenuController(GeneralView generalView, MenuModel menuModel) {

        this.menuModel = menuModel;
        this.generalView = generalView;
        initOpenFileMenu();
        initApplyChanges();
        initOpenRecentFiles();
    }

    private void initOpenFileMenu() {

        this.generalView.getMenuOpenFile().addEventHandler(ActionEvent.ACTION, (e) -> {
            readOnto();
        }
        );
    }

    private void initApplyChanges() {
        this.generalView.getApplyChangesButton().addEventHandler(ActionEvent.ACTION, (e) -> {
            applyOnto();
        }
        );

    }

    private void applyOnto() {
        File file = new File("tmp.rdf");

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = generalView.getCodeAreaOntoTextViewer().getText().getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeViewController.buildTreeView(file.getAbsolutePath());
        VisualizationController.buildGraph(file.getAbsolutePath());
        generalView.isOntoCharged = true;
        generalView.getIsCharged().setText("Changes Applied");
        generalView.getIsCharged().setTextFill(Color.BROWN);

    }

    private void readOnto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("rdfs Onto Filter", "*rdfs"),
                new FileChooser.ExtensionFilter("owl Onto Filter", "*owl"),
                new FileChooser.ExtensionFilter("xml Onto Filter", "*xml"),
                new FileChooser.ExtensionFilter("rdf Onto Filter", "*rdf"),
                new FileChooser.ExtensionFilter("No Filter", "*")

        );
        fileChooser.setTitle("Chemin de l'ontologie");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            OpenOnto(file.getAbsolutePath());
        } else {
            System.out.println("Not Valide File");
        }

    }

    private void OpenOnto(String path) {
        String fullTxt = "";
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                fullTxt += st + "\n";
            }
            TreeViewController.buildTreeView(file.getAbsolutePath());
            VisualizationController.buildGraph(file.getAbsolutePath());
            generalView.isOntoCharged = true;
            generalView.getIsCharged().setText("Onto Charged");
            generalView.getIsCharged().setTextFill(Color.GREEN);
            addToRecentFiles(path);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.generalView.getCodeAreaOntoTextViewer().replaceText(fullTxt);

    }

    public void initOpenRecentFiles() {
        File file = new File("recentFiles.sz");

        try {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            } else {

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));

                    String st;
                    while ((st = br.readLine()) != null) {
                        MenuItem item = new MenuItem(st);
                        item.setOnAction((event) -> {
                            OpenOnto(item.getText());
                        });
                        generalView.getOpenRecentMenu().getItems().add(item);
                    }
                    generalView.getOntoPath().setText("No Path in buffer ");

                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }

            System.out.println("FileCreated");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToRecentFiles(String filePath) {
        File file = new File("recentFiles.sz");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st, fullPaths = "";
            HashSet<String> hs = new HashSet<>();
            while ((st = br.readLine()) != null) {
                hs.add(st);
            }
            hs.add(filePath);

            if (!hs.contains(filePath)) {
                //Add NewItemToMenu
                MenuItem item = new MenuItem(filePath);
                item.setOnAction((event) -> {
                    OpenOnto(filePath);
                });
                generalView.getOpenRecentMenu().getItems().add(item);

            }

            hs.stream().distinct();

            try (FileOutputStream fop = new FileOutputStream(file)) {

                // if file doesn't exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                for (String path : hs) {
                    fullPaths += path + "\n";
                }

                // get the content in bytes
                byte[] contentInBytes = fullPaths.getBytes();

                fop.write(contentInBytes);
                fop.flush();
                fop.close();

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }
            generalView.getOntoPath().setText(filePath);

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("FileAddedToRecent");
    }

}

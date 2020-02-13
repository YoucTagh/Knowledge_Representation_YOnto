package KR.Application;

import KR.Controller.GeneralController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Application.launch;

/**
 * @Author YoucTagh Created On 13/02/2020.
 */
public class Main extends Application {

    @Override
    public void start(Stage window) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/KR/GeneralView/GeneralView.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.setTitle("YOnto 1.0");



            window.setResizable(false);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

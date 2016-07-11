/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author kalt
 */
public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) {
        configureUI(primaryStage);
    }

    private void configureUI(Stage s) {
        try {
            //s.initStyle(StageStyle.UNDECORATED);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/UI.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/Style.css");
            s.setScene(scene);
            s.setResizable(false);

            s.centerOnScreen();
            s.setTitle("Simple ScreenCast");
            s.show();
        } catch (IOException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

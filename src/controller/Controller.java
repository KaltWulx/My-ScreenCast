/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.ScreenRecordUtils;

/**
 *
 * @author kalt
 */
public class Controller {

    @FXML
    private Button bRecord, bSaveTo;
    @FXML
    private AnchorPane anchorPane;
    private static String path = null;
    private Stage s;
    
    public Controller() {
        ScreenRecordUtils.sizeWindow();
    }

    @FXML
    protected void clicButtonRecord(MouseEvent e) {
        s = (Stage) anchorPane.getScene().getWindow();
        if (bRecord.getText().equals("Record")) {
            s.setIconified(true);
            bRecord.setText("Stop");
            bSaveTo.setDisable(true);
            
            Task t = new Task() {
                @Override
                protected Void call() {
                    ScreenRecordUtils.runCommand();
                    
                    return null;
                }
            };
            new Thread(t).start();
            
            Platform.setImplicitExit(false);
            s.setOnCloseRequest((WindowEvent event) -> {
                
               if(s.getTitle().equals("Recording...")) {
                   event.consume();
                   Alert alert = new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Operacion no permitida: Gabracion en curso.");
                   alert.setHeaderText("La grabacion sigue en curso.");
                   alert.setContentText("Debe parar la grabacion en el boton de Stop.");
                   alert.showAndWait();
               } else {
                   Platform.exit();
               }
            });
            s.setTitle("Recording...");
        } else if (bRecord.getText().equals("Stop")) {
            ScreenRecordUtils.commandExit();
            bRecord.setText("Record");
            bRecord.setDisable(true);
            bSaveTo.setDisable(false);
            s.setTitle("Simple ScreenCast");
        }
    }

    @FXML
    protected void clicButtonSave(MouseEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Guardar en...");
        File selectDirectory = dc.showDialog(s);
        if (selectDirectory != null && selectDirectory.isDirectory()) {
            File[] listFiles = selectDirectory.listFiles();
            path = selectDirectory.getAbsolutePath();
            for (File f : listFiles) {
                if (f.toString().equals(path + "/out.mkv")) {
                    f.delete();
                }
            }
            ScreenRecordUtils.modifyCommand(path);
            bRecord.setDisable(false);
        }
    }
}

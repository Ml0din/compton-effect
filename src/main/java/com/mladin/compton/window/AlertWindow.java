package com.mladin.compton.window;

import com.jfoenix.controls.JFXButton;
import com.mladin.compton.ComptonEffectApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertWindow {
    protected Stage applicationStage;
    protected String title;
    protected String alertInfo;
    public Stage stage;
    public AnchorPane parent;
    public AlertWindow(Stage applicationStage, String title, String alertInfo) {
        this.applicationStage = applicationStage;
        this.title = title;
        this.alertInfo = alertInfo;

        try {
            this.parent = (AnchorPane) FXMLLoader.load(ComptonEffectApplication.class.getResource("alert.fxml"));

            getLabel("#title_label").setText(title);
            getLabel("#information_label").setText(alertInfo);
            getJFXButton("#close_button").setOnAction(actionEvent -> stage.close());

            this.stage = new Stage();

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Alert");

            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(applicationStage);

            stage.getIcons().add(new Image(ComptonEffectApplication.class.getResourceAsStream("icons/icon.png")));
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }

    public Label getLabel(String ID) {
        return (Label) parent.lookup(ID);
    }

    public JFXButton getJFXButton(String ID) {
        return (JFXButton) parent.lookup("#close_button");
    }
}

package com.mladin.compton.window;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.mladin.compton.ComptonEffectApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class WindowManager {
    protected ComptonEffectApplication comptonEffectApplication;
    protected Stage stage;
    protected AnchorPane parent;
    public WindowManager(ComptonEffectApplication comptonEffectApplication, Stage stage, AnchorPane parent) {
        this.comptonEffectApplication = comptonEffectApplication;
        this.stage = stage;
        this.parent = parent;
    }

    public void initialize() {
        initializeButtons();
    }

    public void initializeButtons() {
        getButton("#data_button").setOnMouseClicked(actionEvent -> comptonEffectApplication.getLayoutManager().changeCurrentWindow("#data_window"));
        getButton("#graph_button").setOnMouseClicked(actionEvent -> comptonEffectApplication.getLayoutManager().changeCurrentWindow("#graph_window"));
        getButton("#license_button").setOnMouseClicked(actionEvent -> comptonEffectApplication.getLayoutManager().changeCurrentWindow("#license_window"));
        getButton("#info_button").setOnMouseClicked(actionEvent -> comptonEffectApplication.getLayoutManager().changeCurrentWindow("#info_window"));
        getButton("#exit_button").setOnMouseClicked(actionEvent -> System.exit(1));

        getJFXButton("#update_button").setOnAction(actionEvent -> {
            comptonEffectApplication.getComptonEffectCalculations().setSpreadAngle(getJFXSlider("#angle_slider").getValue());
            comptonEffectApplication.getComptonEffectCalculations().setInitialEnergy(getJFXSlider("#energy_slider").getValue());
            comptonEffectApplication.getComptonEffectCalculations().calculateParameters();

            comptonEffectApplication.getComptonGraphSimulation().setAnimationSpeed(getJFXSlider("#animation_slider").getValue());

            AlertWindow alertWindow = new AlertWindow(stage, "Date", "Au fost actualizate datele cunoscute.");
            alertWindow.show();
        });
    }

    public void enableNavigationButtons() {
        getButton("#data_button").setDisable(false);
        getButton("#graph_button").setDisable(false);
        getButton("#license_button").setDisable(false);
        getButton("#info_button").setDisable(false);
    }

    public void disableNavigationButtons() {
        getButton("#data_button").setDisable(true);
        getButton("#graph_button").setDisable(true);
        getButton("#license_button").setDisable(true);
        getButton("#info_button").setDisable(true);
    }

    public AnchorPane getButton(String ID) {
        return (AnchorPane) parent.lookup(ID);
    }

    public JFXButton getJFXButton(String ID) {
        return (JFXButton) parent.lookup(ID);
    }

    public JFXSlider getJFXSlider(String ID) {
        return (JFXSlider) parent.lookup(ID);
    }

    public JFXTextArea getJFXTextArea(String ID) {
        return (JFXTextArea) parent.lookup(ID);
    }

    public boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        }catch (Exception exception) {
            return false;
        }
    }
}

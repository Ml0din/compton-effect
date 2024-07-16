package com.mladin.compton.animation;

import com.jfoenix.controls.JFXButton;
import com.mladin.compton.ComptonEffectApplication;
import com.mladin.compton.window.AlertWindow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;

public class ComptonGraphSimulation {
    protected ComptonEffectApplication comptonEffectApplication;
    protected Stage stage;
    protected AnchorPane parent;
    protected DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    protected double animationSpeed = 1.0;
    public ComptonGraphSimulation(ComptonEffectApplication comptonEffectApplication, Stage stage, AnchorPane parent) {
        this.comptonEffectApplication = comptonEffectApplication;
        this.stage = stage;
        this.parent = parent;

        updatePhotonInfo();
        updateElectronInfo();

        GraphicsContext photonGraphics = getGraphicsContext("#photon_canvas");
        photonGraphics.setLineWidth(4);
        photonGraphics.setStroke(Paint.valueOf("#fca2b8"));

        Circle photon = getCirle("#photon");
        AnchorPane photonInfo = getAnchorPane("#photon_info");
        photon.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                photonGraphics.strokeLine(number.doubleValue(), photon.getLayoutY(), t1.doubleValue(), photon.getLayoutY());
                getLabel("#photon_x").setText(decimalFormat.format(t1.doubleValue()));
                photonInfo.setLayoutX(photonInfo.getLayoutX() + t1.doubleValue() - number.doubleValue());
            }
        });

        photon.layoutYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                photonGraphics.strokeLine(photon.getLayoutX(), number.doubleValue(), photon.getLayoutX(), t1.doubleValue());
                getLabel("#photon_y").setText(decimalFormat.format(t1.doubleValue()));
                photonInfo.setLayoutY(photonInfo.getLayoutY() + t1.doubleValue() - number.doubleValue());
            }
        });

        GraphicsContext electronGraphics = getGraphicsContext("#electron_canvas");
        electronGraphics.setLineWidth(4);
        electronGraphics.setStroke(Paint.valueOf("#b5ccff"));

        Circle electron = getCirle("#electron");
        AnchorPane electronInfo = getAnchorPane("#electron_info");
        electron.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                electronGraphics.strokeLine(number.doubleValue(), electron.getLayoutY(), t1.doubleValue(), electron.getLayoutY());
                getLabel("#electron_x").setText(decimalFormat.format(t1.doubleValue()));
                electronInfo.setLayoutX(electronInfo.getLayoutX() + t1.doubleValue() - number.doubleValue());
            }
        });

        electron.layoutYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                electronGraphics.strokeLine(electron.getLayoutX(), number.doubleValue(), electron.getLayoutX(), t1.doubleValue());
                getLabel("#electron_y").setText(decimalFormat.format(t1.doubleValue()));
                electronInfo.setLayoutY(electronInfo.getLayoutY() + t1.doubleValue() - number.doubleValue());
            }
        });

        getJFXButton("#simulation_button").setOnAction(actionEvent -> start());
    }

    public void start() {
        comptonEffectApplication.getWindowManager().disableNavigationButtons();
        getJFXButton("#simulation_button").setDisable(true);

        Timeline photonReachElectronTimeline = new Timeline();
        KeyValue photon = new KeyValue(getCirle("#photon").layoutXProperty(), 374);
        KeyFrame photonFrame = new KeyFrame(Duration.seconds(2 / this.animationSpeed), photon);
        photonReachElectronTimeline.getKeyFrames().add(photonFrame);
        photonReachElectronTimeline.playFromStart();

        photonReachElectronTimeline.setOnFinished(actionEvent -> {
            Timeline spreadTimeline = new Timeline();

            KeyValue photonX = new KeyValue(getCirle("#photon").layoutXProperty(), comptonEffectApplication.getComptonEffectCalculations().getPhotonX());
            KeyValue photonY = new KeyValue(getCirle("#photon").layoutYProperty(), comptonEffectApplication.getComptonEffectCalculations().getPhotonY());
            KeyFrame photonSpreadFrame = new KeyFrame(Duration.seconds(2 / this.animationSpeed), photonX, photonY);

            KeyValue electronX = new KeyValue(getCirle("#electron").layoutXProperty(), comptonEffectApplication.getComptonEffectCalculations().getElectronX());
            KeyValue electronY = new KeyValue(getCirle("#electron").layoutYProperty(), comptonEffectApplication.getComptonEffectCalculations().getElectronY());
            KeyFrame electronSpreadFrame = new KeyFrame(Duration.seconds(2 / this.animationSpeed), electronX, electronY);

            spreadTimeline.getKeyFrames().addAll(photonSpreadFrame, electronSpreadFrame);
            spreadTimeline.playFromStart();

            spreadTimeline.setOnFinished(finishedAction -> {
                getCirle("#photon").setLayoutX(70);
                getCirle("#photon").setLayoutY(278);

                getCirle("#electron").setLayoutX(374);
                getCirle("#electron").setLayoutY(278);

                GraphicsContext photonGraphics = getGraphicsContext("#photon_canvas");
                photonGraphics.clearRect(0,0, photonGraphics.getCanvas().getWidth(), photonGraphics.getCanvas().getHeight());

                GraphicsContext electronGraphics = getGraphicsContext("#electron_canvas");
                electronGraphics.clearRect(0,0, electronGraphics.getCanvas().getWidth(), electronGraphics.getCanvas().getHeight());

                comptonEffectApplication.getWindowManager().enableNavigationButtons();
                getJFXButton("#simulation_button").setDisable(false);

                AlertWindow alertWindow = new AlertWindow(stage, "Simulare", "Simularea s-a terminat.");
                alertWindow.show();
            });
        });
    }

    public Circle getCirle(String ID) {
        return (Circle) parent.lookup(ID);
    }

    public Label getLabel(String ID) {
        return (Label) parent.lookup(ID);
    }

    public AnchorPane getAnchorPane(String ID) {
        return (AnchorPane) parent.lookup(ID);
    }

    public JFXButton getJFXButton(String ID) {
        return (JFXButton) parent.lookup(ID);
    }

    public GraphicsContext getGraphicsContext(String ID) {
        return ((Canvas) parent.lookup(ID)).getGraphicsContext2D();
    }

    public void updatePhotonInfo() {
        getLabel("#photon_x").setText(getCirle("#photon").getLayoutX() + "");
        getLabel("#photon_y").setText(getCirle("#photon").getLayoutY() + "");
    }

    public void updateElectronInfo() {
        getLabel("#electron_x").setText(getCirle("#electron").getLayoutX() + "");
        getLabel("#electron_y").setText(getCirle("#electron").getLayoutY() + "");
    }

    public void setAnimationSpeed(double animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}

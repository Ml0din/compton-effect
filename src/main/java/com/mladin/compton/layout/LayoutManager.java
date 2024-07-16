package com.mladin.compton.layout;

import com.mladin.compton.ComptonEffectApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LayoutManager {
    protected ComptonEffectApplication comptonEffectApplication;
    protected Stage stage;
    protected AnchorPane parent;

    protected AnchorPane currentWindow;
    public LayoutManager(ComptonEffectApplication comptonEffectApplication, Stage stage, AnchorPane parent) {
        this.comptonEffectApplication = comptonEffectApplication;
        this.stage = stage;
        this.parent = parent;
    }

    public void initialize() {
        this.currentWindow = (AnchorPane) parent.lookup("#data_window");
        updateCurrentWindowLayoutX();
        updateCurrentWindowLayoutY();

        parent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                updateCurrentWindowLayoutX();
            }
        });

        parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                updateCurrentWindowLayoutY();
            }
        });
    }

    public void updateCurrentWindowLayoutX() {
        currentWindow.setLayoutX(180 + parent.getWidth() / 2 - currentWindow.getWidth() / 2);
    }

    public void updateCurrentWindowLayoutY() {
        currentWindow.setLayoutY(parent.getHeight() / 2 - currentWindow.getHeight() / 2);
    }

    public void changeCurrentWindow(String ID) {
        if(!ID.equalsIgnoreCase(currentWindow.getId())) {
            currentWindow.setVisible(false);
            currentWindow.setDisable(true);

            this.currentWindow = (AnchorPane) parent.lookup(ID);
            currentWindow.setDisable(false);
            currentWindow.setVisible(true);

            updateCurrentWindowLayoutX();
            updateCurrentWindowLayoutY();
        }
    }
}

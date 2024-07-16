package com.mladin.compton;

import com.mladin.compton.animation.ComptonGraphSimulation;
import com.mladin.compton.calculations.ComptonEffectCalculations;
import com.mladin.compton.layout.LayoutManager;
import com.mladin.compton.window.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ComptonEffectApplication {
    protected ComptonEffect comptonEffect;
    protected Stage mainApplication;
    protected LayoutManager layoutManager;
    protected WindowManager windowManager;
    protected ComptonEffectCalculations comptonEffectCalculations;
    protected ComptonGraphSimulation comptonGraphSimulation;
    public ComptonEffectApplication(ComptonEffect comptonEffect, Stage mainApplication) {
        this.comptonEffect = comptonEffect;
        this.mainApplication = mainApplication;
    }

    public void initializeApplication() throws Exception {
        Font.loadFont(this.getClass().getResourceAsStream("Panton.ttf"), 10);
        Font.loadFont(this.getClass().getResourceAsStream("Poppins.otf"), 10);

        AnchorPane parent = FXMLLoader.load(this.getClass().getResource("application.fxml"));

        this.layoutManager = new LayoutManager(this, this.mainApplication, parent);
        layoutManager.initialize();

        this.windowManager = new WindowManager(this, this.mainApplication, parent);
        windowManager.initialize();

        this.comptonEffectCalculations = new ComptonEffectCalculations(this, 4, 50);
        this.comptonGraphSimulation = new ComptonGraphSimulation(this, mainApplication, parent);

        Scene scene = new Scene(parent);
        mainApplication.setTitle("Efectul Compton | Mladin Alexandru");
        mainApplication.setScene(scene);

        mainApplication.setMinWidth(1039);
        mainApplication.setMinHeight(612);

        mainApplication.getIcons().add(new Image(this.getClass().getResourceAsStream("icons/icon.png")));
    }

    public void show() {
        mainApplication.show();
        layoutManager.updateCurrentWindowLayoutX();
        layoutManager.updateCurrentWindowLayoutY();
    }

    public LayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    public WindowManager getWindowManager() {
        return this.windowManager;
    }

    public ComptonEffectCalculations getComptonEffectCalculations() {
        return this.comptonEffectCalculations;
    }

    public ComptonGraphSimulation getComptonGraphSimulation() {
        return this.comptonGraphSimulation;
    }
}

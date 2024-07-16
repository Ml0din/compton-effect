package com.mladin.compton;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class ComptonEffect extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getDateTime() + "Compton Effect | Mladin Alexandru-Mihai");
        System.out.println(getDateTime() + "Loading the app...");

        ComptonEffectApplication comptonEffectApplication = new ComptonEffectApplication(this, stage);
        comptonEffectApplication.initializeApplication();
        comptonEffectApplication.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public String getDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return "[" + fromInt(localDateTime.getDayOfMonth()) + "." + fromInt(localDateTime.getMonthValue()) + "." + fromInt(localDateTime.getYear()) + " - " + fromInt(localDateTime.getHour()) + ":" + fromInt(localDateTime.getMinute()) + ":" + fromInt(localDateTime.getSecond()) + "] ";
    }

    public String fromInt(int value) {
        if(value <= 9) {
            return "0" + value;
        }else {
            return String.valueOf(value);
        }
    }
}

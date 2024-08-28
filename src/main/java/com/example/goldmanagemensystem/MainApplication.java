package com.example.goldmanagemensystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Get primary screen dimensions
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Set scene dimensions to full-screen
         stage.setScene(scene);
        stage.setTitle("Gold Management System!");
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreen(true); // Set stage to full-screen

        stage.show();

        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(stage);

        AddSalesController.setMainStage(stage);

        AddDataController.setMainStage(stage);

        ReportsController.setMainStage(stage);

        ReportsDisplayController.setMainStage(stage);

        Stock.setMainStage(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}
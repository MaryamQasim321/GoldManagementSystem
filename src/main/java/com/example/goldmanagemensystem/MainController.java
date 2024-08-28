package com.example.goldmanagemensystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;
    private static Stage mainStage;

    public void setMainStage(Stage stage){
        mainStage = stage;
    }

    @FXML
    protected void onClickPurchaseButton() throws Exception{
        //we want to open the add gold page
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AddData.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System!");
        mainStage.setScene(scene);
        mainStage.show();
        AddDataController addDataController = fxmlLoader.getController();

    }

    @FXML
    protected void onClickSalesButton() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(MainApplication.class.getResource("AddSales.fxml"));
        Scene scene= new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System");
        mainStage.setScene(scene);
        mainStage.show();

    }

    @FXML
    protected void onClickStockButton() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(MainApplication.class.getResource("Stock.fxml"));
        Scene scene= new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System");
        mainStage.setScene(scene);
        mainStage.show();

    }

    @FXML
    protected void onClickReportsButton() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(MainApplication.class.getResource("Reports.fxml"));
        Scene scene= new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System");
        mainStage.setScene(scene);
        mainStage.show();

    }

}
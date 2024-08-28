package com.example.goldmanagemensystem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ReportsController {

    private static Stage mainStage;
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }
    @FXML
    protected DatePicker datePicker1;
    @FXML
    protected DatePicker datePicker2;
   static LocalDate fromDate;
    static LocalDate toDate;

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    public void initialize() {
        // Add a listener to the DatePicker
        datePicker1.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate1, LocalDate newDate1) {
                // Handle the selected date
                if (newDate1 != null) {
                    fromDate=newDate1;


                }
            }
        }); // Add a listener to the DatePicker
        datePicker2.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate2, LocalDate newDate2) {
                // Handle the selected date
                if (newDate2 != null) {
                    toDate=newDate2;

                }
            }
        });

    }
    public static void updateDailyProfitAndLoss(String currentDate) {
        String profitFolderName = "profit_data";
        String lossFolderName = "loss_data";
        File profitDirectory = new File(profitFolderName);
        File lossDirectory = new File(lossFolderName);

        if (!profitDirectory.exists()) {
            profitDirectory.mkdir();
        }
        if (!lossDirectory.exists()) {
            lossDirectory.mkdir();
        }

        String profitFileName = profitFolderName + "/" + currentDate + ".csv";
        String lossFileName = lossFolderName + "/" + currentDate + ".csv";

        File profitFile = new File(profitFileName);
        File lossFile = new File(lossFileName);

        try (BufferedWriter profitWriter = new BufferedWriter(new FileWriter(profitFile, true));
             BufferedWriter lossWriter = new BufferedWriter(new FileWriter(lossFile, true))) {

            double totalSales = calculateTotalSales(currentDate);
            double totalPurchases = calculateTotalPurchases(currentDate);
            double profit = totalSales - totalPurchases;

            if (profitFile.length() == 0) {
                profitWriter.write("Total Sales,Total Purchases,Daily Profit");
                profitWriter.newLine();
            }
            profitWriter.write(totalSales + "," + totalPurchases + "," + profit);
            profitWriter.newLine();

            if (lossFile.length() == 0) {
                lossWriter.write("Total Sales,Total Purchases,Daily Loss");
                lossWriter.newLine();
            }
            lossWriter.write(totalSales + "," + totalPurchases + "," + (totalPurchases - totalSales));
            lossWriter.newLine();

//            calculationLabel.setText(calculationLabel.getText() + "\n Daily profit and loss updated for " + currentDate);
        } catch (IOException e) {
//            calculationLabel.setText("An error occurred while updating daily profit and loss.");
            e.printStackTrace();
        }
    }

    private static double calculateTotalSales(String currentDate) {
        // Implement logic to calculate total sales for the given date
        // This is a placeholder for the actual implementation
        return 0.0;
    }

    private static double calculateTotalPurchases(String currentDate) {
        // Implement logic to calculate total purchases for the given date
        // This is a placeholder for the actual implementation
        return 0.0;
    }

    @FXML
    protected void onClickGenerateButton() throws IOException {

        ReportsDisplayController.setDates(fromDate, toDate);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ReportsDisplay.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System");
        mainStage.setScene(scene);
        mainStage.show();
    }
    @FXML
    protected void onClickBackButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System");
        mainStage.setScene(scene);
        mainStage.show();
    }

}
package com.example.goldmanagemensystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddDataController {
    @FXML
    protected Label calculationLabel;
    @FXML
    protected TextField goldField;
    @FXML
    protected TextField labourField;
    @FXML
    protected TextField priceField;
    float sum;

    @FXML
    public void displaySum() {
        String gold = goldField.getText();
        String labour = labourField.getText();
        String price = priceField.getText();
        sum = Float.parseFloat(gold) * Float.parseFloat(price) + Float.parseFloat(labour);
        String sumString = String.valueOf(sum);
        calculationLabel.setText("Total cost price: " + Float.toString(sum));
        saveDataToFile(gold, labour, price, sumString, getCurrentDate());

        float goldAmount = Float.parseFloat(gold);

        // Update stock
        Stock.stock = Stock.readStockFromFile();
        Stock.stock += goldAmount;
        Stock.addIntoStock(Stock.stock);
    }

    private String getCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    private void saveDataToFile(String gold, String labour, String price, String sum, String currentDate) {
        String folderName = "purchase_data";
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = currentDate + ".csv";
        File file = new File(directory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() == 0) {
                writer.write("Gold amount,Labour,Price of gold,Total purchase price");
                writer.newLine();
            }
            writer.write(gold + "," + labour + "," + price + "," + sum);
            writer.newLine();
            calculationLabel.setText(calculationLabel.getText() + "\n Data saved to " + fileName);
        } catch (IOException e) {
            calculationLabel.setText("An error occurred while saving data.");
            e.printStackTrace();
        }

        ReportsController.updateDailyProfitAndLoss(currentDate);
    }

    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
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

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

public class AddSalesController {
    @FXML
    protected TextField saleLabel;
    @FXML
    protected TextField salaryLabel;
    @FXML
    protected TextField otherExpenses;
    @FXML
    protected Label calculationLabel;
    @FXML
    protected TextField goldPriceLabel;
    float sum;

    @FXML
    public void displaySum() {
        String sale = saleLabel.getText();
        String salary = salaryLabel.getText();
        String expenses = otherExpenses.getText();
        String goldPrice = goldPriceLabel.getText();

        // Validate input
        if (sale.isEmpty() || salary.isEmpty() || expenses.isEmpty() || goldPrice.isEmpty()) {
            calculationLabel.setText("Please fill all fields.");
            return;
        }

        sum = Float.parseFloat(sale) * Float.parseFloat(goldPrice) + Float.parseFloat(salary) + Float.parseFloat(expenses);
        String sumString = String.valueOf(sum);
        calculationLabel.setText("Total expenses of sale: " + Float.toString(sum));
        saveDataToFile(sale, salary, expenses, sumString, getCurrentDate());

        float saleFloat = Float.parseFloat(sale);

        // Update stock
        Stock.stock = Stock.readStockFromFile();
        Stock.stock -= saleFloat;
        Stock.addIntoStock(Stock.stock);
    }

    private String getCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    private void saveDataToFile(String sale, String salary, String expenses, String sumString, String currentDate) {
        String folderName = "sales_data";
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = currentDate + ".csv";
        File file = new File(directory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() == 0) {
                writer.write("Sale,Salary,Other Expenses,Total expenses of sale");
                writer.newLine();
            }
            writer.write(sale + "," + salary + "," + expenses + "," + sumString);
            writer.newLine();

            // Ensure UI update is done on the JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                calculationLabel.setText("Data saved successfully...");
            });

        } catch (IOException e) {
            // Ensure UI update is done on the JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                calculationLabel.setText("An error occurred while saving data.");
            });
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

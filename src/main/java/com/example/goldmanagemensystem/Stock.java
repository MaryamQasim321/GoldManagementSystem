package com.example.goldmanagemensystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;

public class Stock {

    public static float stock = 0;

    static String folderPath = "stock";
    static String filePath = "stock/stock.txt";
    @FXML
    protected Label stockLabel;

    public static void addIntoStock(float stock) {
        // Ensure the stock folder exists
        File stockFolder = new File(folderPath);
        if (!stockFolder.exists()) {
            stockFolder.mkdirs();
        }

        // Ensure the stock file exists
        File stockFile = new File(filePath);
        if (!stockFile.exists()) {
            try {
                stockFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Write the new gold quantity to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("gold:" + stock);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        stock = readStockFromFile(); // Initialize stock from file
        readStockData(); // Call the method when the FXML is initialized
    }

    @FXML
    private void readStockData() {
        StringBuilder stockData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                stockData.append(line); // Read the first line (gold stock)
            } else {
                stockData.append("No stock data available.");
            }
        } catch (IOException e) {
            stockData.append("Error reading stock file.");
            e.printStackTrace();
        }

        // Update the label with stock data
        stockLabel.setText(stockData.toString());
    }

    private static final String FILE_PATH = "stock/stock.txt"; // Updated file path

    public static float readStockFromFile() {
        float stock = 0; // Default stock value

        File stockFile = new File(FILE_PATH);

        // Check if the file exists
        if (!stockFile.exists()) {
            return stock; // Return default value if the file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(stockFile))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("gold:")) {
                stock = Float.parseFloat(line.split(":")[1].trim()); // Read and parse the stock value
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Return default value on error
        }

        return stock; // Return the stock value
    }

   private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @FXML
    protected void onClickBackButton() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(MainApplication.class.getResource("main-page.fxml"));
        Scene scene=new Scene(fxmlLoader.load(), 640, 500);
        mainStage.setTitle("Gold Management System");
        mainStage.setScene(scene);
        mainStage.show();
    }

}

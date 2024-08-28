package com.example.goldmanagemensystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportsDisplayController {
    private static Stage mainStage;
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }
    @FXML
    private TableView<GoldReportEntry> tableView;
    @FXML
    private TableColumn<GoldReportEntry, LocalDate> dateColumn;
    @FXML
    private TableColumn<GoldReportEntry, Integer> purchaseColumn;
    @FXML
    private TableColumn<GoldReportEntry, Integer> salesColumn;
    @FXML
    private TableColumn<GoldReportEntry, Double> expensesColumn;

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

   static LocalDate fromDate;
   static LocalDate toDate;

    public static void setDates(LocalDate date1, LocalDate date2){
        fromDate=date1;
        toDate=date2;

    }

    @FXML
    public void initialize() {
        // Set up the columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        salesColumn.setCellValueFactory(new PropertyValueFactory<>("sales"));
        expensesColumn.setCellValueFactory(new PropertyValueFactory<>("expenses"));

        // Fetch and load data into the table
        fetchData(fromDate, toDate);
    }

    public void fetchData(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            System.out.println("Dates are not set correctly.");
            return;
        }

        List<GoldReportEntry> entries = new ArrayList<>();

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            String formattedDate = date.format(dateFormatter);
            String purchaseFilePath = "Purchase_data/" + formattedDate + ".csv";
            String salesFilePath = "sales_data/" + formattedDate + ".csv";

            System.out.println("Fetching data for date: " + formattedDate);
            System.out.println("Purchase file: " + purchaseFilePath);
            System.out.println("Sales file: " + salesFilePath);

            int purchaseGold = getPurchaseGold(purchaseFilePath, date);
            int salesGold = getSalesGold(salesFilePath, date);
            double expenses = getExpenses(salesFilePath, date);

            entries.add(new GoldReportEntry(date, purchaseGold, salesGold, expenses));
        }

        tableView.getItems().setAll(entries);
    }

    private int getPurchaseGold(String filePath, LocalDate date) {
        int goldAmount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped=false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true; // Skip the header
                    continue;
                }
                String[] columns = line.split(",");
                goldAmount += Integer.parseInt(columns[0]);

            }
        } catch (IOException e) {
            System.out.println("File not found: " + filePath);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing line: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goldAmount;
    }

    private int getSalesGold(String filePath, LocalDate date) {
        int goldAmount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped=false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true; // Skip the header
                    continue;
                }
                String[] columns = line.split(",");


                    goldAmount += Integer.parseInt(columns[0]);

            }
        } catch (IOException e) {
            System.out.println("File not found: " + filePath);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing line: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goldAmount;
    }

    private double getExpenses(String filePath, LocalDate date) {
        double expenses = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped=false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true; // Skip the header
                    continue;
                }
                String[] columns = line.split(",");


                    expenses += Double.parseDouble(columns[1]); // Ensure this index is valid
                expenses += Double.parseDouble(columns[2]);
            }
        } catch (IOException e) {
            System.out.println("File not found: " + filePath);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing line: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenses;
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

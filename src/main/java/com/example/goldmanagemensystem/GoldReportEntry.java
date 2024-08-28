package com.example.goldmanagemensystem;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GoldReportEntry {
    private LocalDate date;
    private IntegerProperty purchase;
    private IntegerProperty sales;
    private DoubleProperty expenses;
    private SimpleStringProperty formattedDate;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public GoldReportEntry(LocalDate date, int purchase, int sales, double expenses) {
        this.date = date;
        this.purchase = new SimpleIntegerProperty(purchase);
        this.sales = new SimpleIntegerProperty(sales);
        this.expenses = new SimpleDoubleProperty(expenses);
        this.formattedDate = new SimpleStringProperty(date.format(dateFormatter));
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFormattedDate() {
        return formattedDate.get();
    }

    public IntegerProperty purchaseProperty() {
        return purchase;
    }

    public IntegerProperty salesProperty() {
        return sales;
    }

    public DoubleProperty expensesProperty() {
        return expenses;
    }

    public int getPurchase() {
        return purchase.get();
    }

    public int getSales() {
        return sales.get();
    }

    public double getExpenses() {
        return expenses.get();
    }
}

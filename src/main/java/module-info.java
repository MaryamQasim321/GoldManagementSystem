module com.example.goldmanagemensystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.goldmanagemensystem to javafx.fxml;
    exports com.example.goldmanagemensystem;
}
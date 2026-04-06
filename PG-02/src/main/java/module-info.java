module ucr.algoritmos.pg02 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.algoritmos.pg02 to javafx.fxml;
    exports ucr.algoritmos.pg02;
    exports controller;
    opens controller to javafx.fxml;
}
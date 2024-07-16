module com.mladin.compton {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.mladin.compton to javafx.fxml, com.jfoenix;
    exports com.mladin.compton;
}
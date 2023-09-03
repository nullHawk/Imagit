module com.nullhawk.imagit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.nullhawk.imagit to javafx.fxml;
    exports com.nullhawk.imagit;
}
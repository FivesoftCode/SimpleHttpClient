module com.fivesoft.simplehttpclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.fivesoft.simplehttpclient to javafx.fxml;
    exports com.fivesoft.simplehttpclient;
}
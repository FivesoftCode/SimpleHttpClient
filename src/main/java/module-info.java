module com.fivesoft.simplehttpclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires java.net.http;
    requires junit;

    opens com.fivesoft.simplehttpclient to javafx.fxml;
    exports com.fivesoft.simplehttpclient;

    opens com.fivesoft.simplehttpclient.ui to javafx.fxml;
    exports com.fivesoft.simplehttpclient.ui;

    opens com.fivesoft.simplehttpclient.test to junit;
    exports com.fivesoft.simplehttpclient.test;
    exports com.fivesoft.simplehttpclient.tabmanager;
    opens com.fivesoft.simplehttpclient.tabmanager to javafx.fxml;

}
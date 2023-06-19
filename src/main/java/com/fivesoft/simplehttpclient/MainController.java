package com.fivesoft.simplehttpclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

import java.util.List;

public class MainController {
    @FXML
    private ComboBox<String> httpMethodComboBox;

    @FXML
    private TabPane tabPane;
    @FXML
    private StackPane tabContentArea;


    public void initialize() {
        // Initialize the HTTP method ComboBox
        ObservableList<String> httpMethods = FXCollections.observableArrayList(
                "GET", "POST", "PUT", "DELETE"); // Add or modify the available HTTP methods as needed
        httpMethodComboBox.setItems(httpMethods);


        // Set the cell factory to customize the appearance of the ComboBox items
        httpMethodComboBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        // Set the string converter to convert between the string value and the actual object
        httpMethodComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });

        httpMethodComboBox.setValue("GET");

        //Tabs

        // Set the content for each tab
        TextArea responseTextArea = new TextArea("No response yet");
        responseTextArea.setEditable(false);
        responseTextArea.setWrapText(true);
        responseTextArea.getStyleClass().add("response-text-area");
        tabContentArea.getChildren().add(responseTextArea);

        TextArea bodyTextArea = new TextArea();
        bodyTextArea.setWrapText(true);
        bodyTextArea.getStyleClass().add("body-text-area");
        tabContentArea.getChildren().add(bodyTextArea);

        TableView<String> headersTableView = new TableView<>();
        TableColumn<String, String> headerColumn = new TableColumn<>("Headers");
        headersTableView.getColumns().add(headerColumn);
        tabContentArea.getChildren().add(headersTableView);

        // Listen for tab selection changes
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateTabContent(newValue);
        });

        // Select the initial tab
        tabPane.getSelectionModel().select(0);

    }

    @FXML
    private void onHttpMethodChanged() {
        // Get the selected HTTP method from the ComboBox
        String selectedMethod = httpMethodComboBox.getValue();
        System.out.println("Selected HTTP method: " + selectedMethod);

        // Perform any additional logic based on the selected method
        // For example, you can update UI elements or trigger actions accordingly
    }

    private void updateTabContent(Tab selectedTab) {
        tabContentArea.getChildren().forEach(node -> node.setVisible(false));
        int selectedIndex = tabPane.getTabs().indexOf(selectedTab);
        tabContentArea.getChildren().get(selectedIndex).setVisible(true);
    }

}
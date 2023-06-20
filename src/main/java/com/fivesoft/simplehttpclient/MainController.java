package com.fivesoft.simplehttpclient;

import com.fivesoft.simplehttpclient.ui.Header;
import com.fivesoft.simplehttpclient.ui.HeaderTableView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainController {

    private static final StringConverter<String> DEFAULT_STRING_CONVERTER = new StringConverter<>() {
        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public String fromString(String string) {
            return string;
        }
    };

    @FXML
    private ComboBox<String> httpMethodComboBox;

    @FXML
    private TabPane tabPane;
    @FXML
    private HBox tabContentArea;

    @FXML
    private TextField urlTextField;

    @FXML
    private Button requestButton;

    @FXML
    private Pane mainContent;

    private final TextArea responseTextArea;
    private final TextArea bodyTextArea;
    private final HeaderTableView headersTableView;

    /**
     * Creates a new MainController instance. Initialize pages UI elements.
     */

    public MainController() {

        responseTextArea = new TextArea("No response yet");
        responseTextArea.setEditable(false);
        responseTextArea.setWrapText(true);
        responseTextArea.getStyleClass().add("response-text-area");

        bodyTextArea = new TextArea();
        bodyTextArea.setWrapText(true);
        bodyTextArea.getStyleClass().add("body-text-area");

        headersTableView = new HeaderTableView();
        headersTableView.getStyleClass().add("headers-table-view");
        headersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Initializes all UI elements.
     */

    public void initialize() {

        //Tabs
        TabManager tabManager = new TabManager(tabPane, tabContentArea);

        tabManager.addTab("response", "Response", responseTextArea, false)
                //.addTab("body", "Body", bodyTextArea, false) // By default, the body tab is not shown (default HTTP method is GET)
                .addTab("headers", "Headers", headersTableView, false);

        tabContentArea.setFillHeight(true);
        tabContentArea.setPrefWidth(Double.MAX_VALUE);

        // Initialize the HTTP method ComboBox
        ObservableList<String> httpMethods = FXCollections.observableArrayList("GET", "POST");
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

        httpMethodComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(Objects.equals(newValue, "GET")) {
                        tabManager.removeTab("body");
                    } else {
                        if (!tabManager.hasTab("body")) {
                            tabManager.addTab("body", "Body", bodyTextArea, false);
                        }
                    }
        });

        httpMethodComboBox.setConverter(DEFAULT_STRING_CONVERTER);
        httpMethodComboBox.setValue("GET");

        //Request Button
        requestButton.setOnAction(e -> makeRequest());
    }

    private void makeRequest() {
        // Get the selected HTTP method from the ComboBox
        String selectedMethod = httpMethodComboBox.getValue();
        String url = urlTextField.getText();
        String body = bodyTextArea.getText();

        requestButton.setText("...");

        URI urlObj;
        try {
            urlObj = new URI(url);
        } catch (Throwable e) {
            responseTextArea.setText("Error. Invalid URL");
            return;
        }

        new Thread(() -> {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
            requestBuilder.uri(urlObj);
            if (selectedMethod.equals("POST")) {
                requestBuilder.GET();
            } else {
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
            }
            HttpRequest request = requestBuilder.build();

            // Get the headers from the table
            ObservableList<Header> headers = headersTableView.getItems();
            for (Header header : headers) {
                String key = header.getKey().trim();
                String value = header.getValue();
                if (!key.isEmpty() && !value.isEmpty()) {
                    requestBuilder.setHeader(key, value);
                }
            }

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response;
            try {
                response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                int code = response.statusCode();
                String httpVer = response.version().name();
                String responseBody = response.body();

                String headersString = response.headers().map().entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining("\n"));

                Platform.runLater(() -> responseTextArea.setText(
                        "Status: " + code + " (" + httpVer + ")" +
                        "\n\n***Headers***\n\n" +
                        headersString +
                        "\n\n***Body***\n\n" +
                        responseBody));

            } catch (Exception e) {
                Platform.runLater(() -> responseTextArea.setText("Error. " + e.getMessage()));
            } finally {
                Platform.runLater(() -> requestButton.setText("Request"));
            }


        }, "Request Thread").start();

    }

}
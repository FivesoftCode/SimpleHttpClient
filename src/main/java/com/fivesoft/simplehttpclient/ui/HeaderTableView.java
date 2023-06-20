package com.fivesoft.simplehttpclient.ui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

public class HeaderTableView extends TableView<Header> {
    private TableColumn<Header, String> keyColumn;
    private TableColumn<Header, String> valueColumn;

    public HeaderTableView() {
        initialize();
    }

    private void initialize() {
        // Set up the header table
        keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(data -> data.getValue().keyProperty());
        keyColumn.setCellFactory(this::createEditableTextFieldCell);

        valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(data -> data.getValue().valueProperty());
        valueColumn.setCellFactory(this::createEditableTextFieldCell);

        getColumns().addAll(keyColumn, valueColumn);

        // Enable editing of cells
        setEditable(true);

        // Add an empty header row
        for (int i = 0; i < 50; i++) {
            addEmptyHeader();
        }
    }

    private EditingCell createEditableTextFieldCell(TableColumn<Header, String> column) {
        return new EditingCell();
    }

    private void enableEditing() {
        setEditable(true);
    }

    public void addEmptyHeader() {
        getItems().add(new Header("", ""));
    }
}

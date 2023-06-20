package com.fivesoft.simplehttpclient.ui;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class EditableTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {

    public EditableTextFieldTableCell(StringConverter<T> converter) {
        super(converter);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            setText("");
        }
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
        } else {
            setText(getString());
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
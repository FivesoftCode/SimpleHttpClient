package com.fivesoft.simplehttpclient.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a header in the headers table.
 */

public class Header {
    private final StringProperty key;
    private final StringProperty value;

    /**
     * Creates a new Header instance.
     * @param key name of the header
     * @param value value of the header
     */

    public Header(String key, String value) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
    }

    /**
     * Returns the name of the header. (key)
     * @return name of the header
     */

    public String getKey() {
        return key.get();
    }

    /**
     * Sets the name of the header. (key)
     * @param key name of the header
     */

    public void setKey(String key) {
        this.key.set(key);
    }

    /**
     * Returns the value of the header.
     * @return value of the header
     */

    public String getValue() {
        return value.get();
    }

    /**
     * Sets the value of the header.
     * @param value value of the header
     */

    public void setValue(String value) {
        this.value.set(value);
    }

    /**
     * Gets the key property of the header.
     * @return key property of the header
     */

    public StringProperty keyProperty() {
        return key;
    }

    /**
     * Gets the value property of the header.
     * @return value property of the header
     */

    public StringProperty valueProperty() {
        return value;
    }
}
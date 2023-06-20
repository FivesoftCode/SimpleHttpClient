package com.fivesoft.simplehttpclient.test;

import com.fivesoft.simplehttpclient.ui.Header;
import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeaderTest {

    private Header header;

    @Before
    public void setUp() {
        header = new Header("Content-Type", "application/json");
    }

    @Test
    public void testGetKey() {
        assertEquals("Content-Type", header.getKey());
    }

    @Test
    public void testSetKey() {
        header.setKey("Authorization");
        assertEquals("Authorization", header.getKey());
    }

    @Test
    public void testGetValue() {
        assertEquals("application/json", header.getValue());
    }

    @Test
    public void testSetValue() {
        header.setValue("text/plain");
        assertEquals("text/plain", header.getValue());
    }

    @Test
    public void testKeyProperty() {
        StringProperty keyProperty = header.keyProperty();
        assertEquals("Content-Type", keyProperty.get());
    }

    @Test
    public void testValueProperty() {
        StringProperty valueProperty = header.valueProperty();
        assertEquals("application/json", valueProperty.get());
    }
}

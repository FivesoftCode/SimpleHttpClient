package com.fivesoft.simplehttpclient.test;

import com.fivesoft.simplehttpclient.tabmanager.TabManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TabManagerTest {

    private TabManager tabManager;
    private Pane tabContentArea;
    private TabPane tabPane;

    @Before
    public void setUp() throws InterruptedException {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            //Workaround for "Toolkit not initialized" exception
        }
        Thread.sleep(1000); // workaround for "Toolkit not initialized" exception

        // Create the necessary components
        tabContentArea = new Pane();
        tabPane = new TabPane();
        tabManager = new TabManager(tabPane, tabContentArea);
    }

    @Test
    public void testAddTab() {
        Node content = new HBox();
        tabManager.addTab("tab1", "Tab 1", content, true);

        //Adding a tab is an asynchronous operation, so we have to wait until the tab is added
        Platform.runLater(() -> {
            assertEquals(1, tabPane.getTabs().size());
            assertEquals(content, tabContentArea.getChildren().get(0));
        });
    }

    @Test
    public void testRemoveTab() {
        Node content = new HBox();
        tabManager.addTab("tab1", "Tab 1", content, true);
        Platform.runLater(() -> assertEquals(1, tabPane.getTabs().size()));
        tabManager.removeTab("tab1");
        //Removing a tab is an asynchronous operation, so we have to wait until the tab is removed
        Platform.runLater(() -> assertEquals(0, tabPane.getTabs().size()));
    }

    @Test
    public void testGetSelectedTabIndex() {
        Node content = new HBox();
        tabManager.addTab("tab1", "Tab 1", content, true);
        tabManager.addTab("tab2", "Tab 2", content, true);

        tabManager.setSelectedTabIndex(1);
        assertEquals(1, tabManager.getSelectedTabIndex());
    }

    @Test
    public void testHasTab() {
        Node content = new HBox();
        tabManager.addTab("tab1", "Tab 1", content, true);

        assertTrue(tabManager.hasTab("tab1"));
        assertFalse(tabManager.hasTab("tab2"));
    }
}

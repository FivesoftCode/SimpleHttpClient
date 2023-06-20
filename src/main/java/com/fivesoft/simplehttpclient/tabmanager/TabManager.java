package com.fivesoft.simplehttpclient.tabmanager;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class TabManager {

    private final Pane tabContentArea;
    private final TabPane tabPane;

    /**
     * Creates a new TabManager instance.
     *
     * @param tabPane        The tab pane, where the tabs are displayed.
     * @param tabContentArea The tab content area, where the content of the selected tab is displayed.
     */

    public TabManager(TabPane tabPane, Pane tabContentArea) {
        this.tabContentArea = tabContentArea;
        this.tabPane = tabPane;
        this.tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                            Platform.runLater(() -> {
                                if (newValue == null) {
                                    tabContentArea.getChildren().clear();
                                } else {
                                    Node content = newValue.getContent();
                                    HBox.setHgrow(content, Priority.ALWAYS);
                                    ObservableList<Node> children = tabContentArea.getChildren();
                                    children.setAll(content);
                                }
                            });
                        }
                );
    }

    /**
     * Adds a tab to the tab pane at the end.
     *
     * @param id      The unique ID of the tab.
     * @param title   The title of the tab.
     * @param content The content of the tab.
     * @return The TabManager instance for method chaining.
     */

    public TabManager addTab(String id, String title, Node content, boolean closeable) {
        Tab tab = new Tab(title, content);
        tab.setId(id);
        tab.setClosable(closeable);

        Platform.runLater(() -> {
            tabPane.getTabs()
                    .add(tab);

            if (tabPane.getTabs().size() == 1) {
                // Select the first tab by default
                tabPane.getSelectionModel().select(0);
            }

            HBox.setHgrow(content, Priority.ALWAYS);
            ObservableList<Node> children = tabContentArea.getChildren();
            children.setAll(content);
        });

        return this;
    }

    /**
     * Removes a tab from the tab pane.
     *
     * @param id The ID of the tab to remove.
     */

    public void removeTab(String id) {
        tabPane.getTabs()
                .stream()
                .filter(tab -> tab.getId().equals(id))
                .findFirst()
                .ifPresent(tab -> tabPane.getTabs().remove(tab));
    }

    /**
     * Gets the index of the currently selected tab.
     *
     * @return The index of the currently selected tab.
     */

    public int getSelectedTabIndex() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }

    /**
     * Sets the selected tab by index.
     *
     * @param index The index of the tab to select.
     */

    public void setSelectedTabIndex(int index) {
        tabPane.getSelectionModel().select(index);
    }

    /**
     * Gets the currently selected tab.
     *
     * @return The currently selected tab.
     */

    public Tab getSelectedTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * Checks if a tab with the specified ID exists.
     * @param id The ID to check.
     * @return True if a tab with the specified ID exists, false otherwise.
     */
    public boolean hasTab(String id) {
        return tabPane.getTabs()
                .stream()
                .anyMatch(tab -> tab.getId().equals(id));
    }

}

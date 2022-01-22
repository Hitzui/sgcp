package com.dysconcsa.pancasan.util;

import com.gluonhq.charm.glisten.application.AppManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public abstract class Filter {

    private String filter;

    @FXML
    public TextField searchField;
    @FXML
    public ToolBar toolBar;

    public void initialize() {
        HBox.setHgrow(searchField, Priority.ALWAYS);
    }

    @FXML
    public void search(ActionEvent actionEvent) {
        AppManager.getInstance().hideLayer(this.filter);
    }

    @FXML
    public void close(ActionEvent actionEvent) {
        searchField.clear();
        AppManager.getInstance().hideLayer(this.filter);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    boolean isFilterApplied() {
        return searchField.getText() != null && !searchField.getText().isEmpty();
    }
}

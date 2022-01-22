package com.dysconcsa.pancasan.util;

import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class TableCellEntity<T, S> extends TableCell<T, S> {
    final HBox hBox = new HBox(10);
    private T currentItem;

    public TableCellEntity(Consumer<T> edit, Consumer<T> remove) {
        var btnEdit = MaterialDesignIcon.EDIT.button();
        btnEdit.setStyle("-fx-text-fill: #000000");
        btnEdit.setOnAction(event -> edit.accept(currentItem));
        var btnRemove = MaterialDesignIcon.DELETE.button();
        btnRemove.setStyle("-fx-text-fill: #000000");
        btnRemove.setOnAction(e -> remove.accept(currentItem));
        hBox.getChildren().addAll(btnEdit, btnRemove);
    }

    public TableCellEntity() {
        super();
    }

    @Override
    protected void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            currentItem = getTableView().getItems().get(getIndex());
            setGraphic(hBox);
        }
    }
}

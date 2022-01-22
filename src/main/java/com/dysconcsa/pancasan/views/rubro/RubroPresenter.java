package com.dysconcsa.pancasan.views.rubro;

import com.dysconcsa.pancasan.PancasanApplication;
import com.dysconcsa.pancasan.dao.DaoRubro;
import com.dysconcsa.pancasan.models.Rubro;
import com.dysconcsa.pancasan.models.TipoRubro;
import com.dysconcsa.pancasan.util.TableCellEntity;
import com.gluonhq.charm.glisten.afterburner.GluonView;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public class RubroPresenter {

    @Inject
    private DaoRubro daoRubro;

    @FXML
    public View rubroView;

    @FXML
    private TableView<Rubro> tableRubro;

    @FXML
    private TableColumn<Rubro, String> colDescripcion;

    @FXML
    private TableColumn<Rubro, TipoRubro> colTipo;

    private Rubro selectedRubro;

    private FilteredList<Rubro> filteredList;

    ObservableList<Rubro> rubroProperties = FXCollections.observableArrayList();

    @SuppressWarnings("DuplicatedCode")
    public void initialize() {
        addButtonToTable();
        fillData();
        colDescripcion.setCellValueFactory(v -> v.getValue().descripcionProperty());
        colTipo.setCellValueFactory(v -> v.getValue().tipoRubroProperty());
        rubroView.setShowTransitionFactory(BounceInRightTransition::new);
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.ADD.text, e -> showData(true));
        fab.showOn(rubroView);
        rubroView.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager appManager = AppManager.getInstance();
                AppBar appBar = appManager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        appManager.getDrawer().open()));
                appBar.setTitleText("Rubro");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        AppManager.getInstance().showLayer(PancasanApplication.POPUP_FILTER_RUBRO)));

            }
        });
        AppManager.getInstance().addLayerFactory(PancasanApplication.POPUP_FILTER_RUBRO, () -> {
            GluonView filterView = new GluonView(FilterPresenter.class);
            FilterPresenter filterPresenter = (FilterPresenter) filterView.getPresenter();
            SidePopupView sidePopupView = new SidePopupView(filterView.getView(), Side.TOP, true);
            sidePopupView.showingProperty().addListener((obs, ov, nv) -> {
                if (ov && !nv) {
                    filteredList.setPredicate(filterPresenter.getPredicate());
                }
            });
            return sidePopupView;
        });
    }

    private void fillData() {
        rubroProperties = daoRubro.rubroObservableList();
        filteredList = new FilteredList<>(rubroProperties);
        tableRubro.setItems(filteredList);
    }

    private void addButtonToTable() {
        TableColumn<Rubro, Void> colBtn = new TableColumn<>("Acciones");
        colBtn.setCellFactory(c -> new TableCellEntity<>(this::edit, this::remove));
        tableRubro.getColumns().add(colBtn);
    }

    private void edit(Rubro rubro) {
        this.selectedRubro = rubro;
        showData(false);
    }

    @SuppressWarnings("DuplicatedCode")
    private void remove(Rubro rubro) {
        var r = daoRubro.findOne(rubro.getId());
        Alert alertDelete = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alertDelete.setTitleText("Eliminar");
        alertDelete.setContentText("¿Seguro desea elminar el rubro seleccionada? Esta acción no se puede revertir.");
        var option = alertDelete.showAndWait();
        if (option.isPresent()) {
            var btn = option.get();
            if (btn == ButtonType.OK) {
                daoRubro.delete(r);
                rubroProperties.remove(rubro);
                tableRubro.refresh();
            }
        }
    }

    private void showData(boolean isNew) {
        var getView = new GluonView(ViewPresenter.class);
        var viewPresenter = (ViewPresenter) getView.getPresenter();
        if (isNew) {
            viewPresenter.setRubro(null);
        } else {
            viewPresenter.setRubro(selectedRubro);
        }
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.NONE);
        alert.getButtons().clear();
        alert.setTitleText("Datos de Rubro");
        alert.setContent(getView.getView());
        viewPresenter.setAlert(alert);
        if (viewPresenter.isOk()) {
            tableRubro.refresh();
            rubroProperties.clear();
            fillData();
        }
    }
}

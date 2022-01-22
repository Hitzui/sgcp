package com.dysconcsa.pancasan.views.caja;

import com.dysconcsa.pancasan.PancasanApplication;
import com.dysconcsa.pancasan.dao.DaoCajas;
import com.dysconcsa.pancasan.properties.CajaProperty;
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
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public class CajasPresenter {

    ObservableList<CajaProperty> cajaProperties = FXCollections.observableArrayList();
    FilteredList<CajaProperty> filteredList;
    private final ListProperty<CajaProperty> cajalistProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    @FXML
    public View cajaView;
    @FXML
    public TableView<CajaProperty> tableCajas;
    @FXML
    private TableColumn<CajaProperty, String> columnCodigo;

    @FXML
    private TableColumn<CajaProperty, String> columnDescripcion;

    @FXML
    private TableColumn<CajaProperty, String> columnUbicacion;

    @Inject
    private DaoCajas daoCajas;

    private CajaProperty selectedCaja;

    public void initialize() {
        addButtonToTable();
        columnCodigo.setCellValueFactory(value -> value.getValue().getCodigo());
        columnDescripcion.setCellValueFactory(value -> value.getValue().getDescripcion());
        columnUbicacion.setCellValueFactory(value -> value.getValue().getUbicacion());
        tableCajas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectCaja(newValue));
        cajaView.setShowTransitionFactory(BounceInRightTransition::new);
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.ADD.text, e -> {
            showData(true);
        });
        fab.showOn(cajaView);
        cajaView.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager appManager = AppManager.getInstance();
                AppBar appBar = appManager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        appManager.getDrawer().open()));
                appBar.setTitleText("Cajas");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        AppManager.getInstance().showLayer(PancasanApplication.POPUP_FILTER_CAJA)));
                fillData();
            }
        });

        AppManager.getInstance().addLayerFactory(PancasanApplication.POPUP_FILTER_CAJA, () -> {
            GluonView filterView = new GluonView(FilterPresenter.class);
            FilterPresenter filterPresenter = (FilterPresenter) filterView.getPresenter();
            SidePopupView sidePopupView = new SidePopupView(filterView.getView(), Side.TOP, true);
            sidePopupView.showingProperty().addListener((obs, ov, nv) -> {
                if (ov && !nv) {
                    // 2. Filtrar los valores
                    filteredList.setPredicate(filterPresenter.getPredicate());
                }
            });
            return sidePopupView;
        });
    }

    private void selectCaja(CajaProperty newValue) {
        this.selectedCaja = newValue;
    }

    private void fillData() {
        cajaProperties = daoCajas.observableListCaja();
        filteredList = new FilteredList<>(cajaProperties);
        tableCajas.setItems(filteredList);
    }

    private void addButtonToTable() {
        TableColumn<CajaProperty, Void> colBtn = new TableColumn<>("Acciones");
        colBtn.setCellFactory(c -> new TableCellEntity<>(this::edit, this::remove));
        tableCajas.getColumns().add(colBtn);
    }

    private void edit(CajaProperty cajaProperty) {
        selectedCaja = cajaProperty;
        showData(false);
    }

    private void remove(CajaProperty cajaProperty) {
        var caja = daoCajas.findOne(cajaProperty.getId().getValue());
        Alert alertDelete = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alertDelete.setTitleText("Eliminar");
        alertDelete.setContentText("¿Seguro desea elminar la caja seleccionada? Esta acción no se puede revertir.");
        var option = alertDelete.showAndWait();
        if (option.isPresent()) {
            var btn = option.get();
            if (btn == ButtonType.OK) {
                daoCajas.delete(caja);
                cajaProperties.remove(cajaProperty);
                tableCajas.refresh();
            }
        }
    }

    private void showData(boolean isNew) {
        var getView = new GluonView(ViewPresenter.class);
        ViewPresenter viewPresenter = (ViewPresenter) getView.getPresenter();
        if (isNew) {
            viewPresenter.setCaja(null);
        } else {
            viewPresenter.setCaja(selectedCaja);
        }
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.NONE);
        alert.getButtons().clear();
        alert.setTitleText("Datos de Caja");
        alert.setContent(getView.getView());
        viewPresenter.setAlert(alert);
        if (viewPresenter.isOk()) {
            tableCajas.getItems().clear();
            fillData();
        }
    }
}

package com.dysconcsa.pancasan.views.rubro;

import com.dysconcsa.pancasan.dao.DaoRubro;
import com.dysconcsa.pancasan.models.Rubro;
import com.dysconcsa.pancasan.models.TipoRubro;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.inject.Inject;

public class ViewPresenter {

    @Inject
    private DaoRubro daoRubro;
    ObservableList<TipoRubro> tipoRubroObservableList = FXCollections.emptyObservableList();
    private Rubro rubro;
    private boolean isNew;
    @FXML
    public View view;
    @FXML
    private TextField txtDescripcion;

    @FXML
    private ChoiceBox<TipoRubro> cmbTipoRubro;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    private boolean ok = false;

    private Alert alert;

    public void initialize() {
        tipoRubroObservableList = FXCollections.observableArrayList(TipoRubro.values());
        cmbTipoRubro.setItems(tipoRubroObservableList);
    }

    public boolean isOk() {
        return ok;
    }

    public void setRubro(Rubro rubro) {
        if (rubro == null) {
            isNew = true;
            txtDescripcion.setText("");
            this.rubro = new Rubro();
        } else {
            isNew = false;
            txtDescripcion.setText(rubro.getDescripcion());
            cmbTipoRubro.setValue(rubro.getTipoRubroProperty());
            this.rubro = rubro;
        }
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        alert.showAndWait();
    }

    @FXML
    void handleAceptar(ActionEvent event) {
        if (txtDescripcion.getText().isBlank()) {
            txtDescripcion.requestFocus();
            return;
        }
        if (cmbTipoRubro.getSelectionModel().isEmpty()) {
            return;
        }
        Notifications notifications = Notifications.create();
        notifications.hideAfter(Duration.seconds(2d));
        try {
            if (isNew) {
                rubro.setDescripcion(txtDescripcion.getText());
                rubro.setTipo(cmbTipoRubro.getValue());
                daoRubro.save(rubro);
            } else {
                var find = daoRubro.findOne(this.rubro.getId());
                find.setDescripcion(txtDescripcion.getText());
                find.setTipo(cmbTipoRubro.getValue());
                daoRubro.update(find);
            }
            notifications.text("Se han guardado los datos de forma correcta.");
            notifications.position(Pos.BOTTOM_RIGHT);
            notifications.showInformation();
            ok = true;
            alert.hide();
        } catch (Exception ex) {
            notifications.text("No se pudo guardar los datos, intente nuevamente");
            notifications.position(Pos.BOTTOM_RIGHT);
            notifications.showError();
            ex.printStackTrace();
            ok = false;
            alert.hide();
        }
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        ok = false;
        alert.hide();
    }

}

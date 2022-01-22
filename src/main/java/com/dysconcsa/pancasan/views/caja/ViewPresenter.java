package com.dysconcsa.pancasan.views.caja;

import com.dysconcsa.pancasan.dao.DaoCajas;
import com.dysconcsa.pancasan.models.Caja;
import com.dysconcsa.pancasan.properties.CajaProperty;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class ViewPresenter {

    @Inject
    private DaoCajas daoCajas;
    private boolean isNew;
    private boolean ok = false;
    @FXML
    public View viewCaja;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtUbicacion;

    private Alert alert;

    private CajaProperty selectedCaja;

    public void setCaja(CajaProperty selectedCaja) {
        if (selectedCaja != null) {
            this.selectedCaja = selectedCaja;
            txtCodigo.setText(selectedCaja.getCodigo().get());
            txtDescripcion.setText(selectedCaja.getDescripcion().get());
            txtUbicacion.setText(selectedCaja.getUbicacion().get());
            isNew = false;
        } else {
            this.selectedCaja = new CajaProperty();
            txtCodigo.setText("");
            txtDescripcion.setText("");
            txtUbicacion.setText("");
            isNew = true;
        }
    }

    public void initialize() {

    }

    @FXML
    void handleAceptar(ActionEvent event) {
        if (!validate()) {
            try {
                if (isNew) {
                    var caja = new Caja();
                    caja.setCodigo(txtCodigo.getText());
                    caja.setDescripcion(txtDescripcion.getText());
                    caja.setUbicacion(txtUbicacion.getText());
                    daoCajas.save(caja);
                    ok = true;
                    alert.hide();
                } else {
                    var caja = daoCajas.findOne(selectedCaja.getId().get());
                    caja.setCodigo(txtCodigo.getText());
                    caja.setUbicacion(txtUbicacion.getText());
                    caja.setDescripcion(txtDescripcion.getText());
                    daoCajas.update(caja);
                    ok = true;
                    alert.hide();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        ok = false;
        alert.hide();
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        this.alert.showAndWait();
    }

    public boolean isOk() {
        return ok;
    }

    private boolean validate() {
        if (txtUbicacion.getText().isEmpty()) txtUbicacion.setText("");
        if (txtCodigo.getText().isEmpty()) {
            txtCodigo.requestFocus();
            return true;
        } else {
            txtDescripcion.requestFocus();
            return txtDescripcion.getText().isEmpty();
        }
    }

}

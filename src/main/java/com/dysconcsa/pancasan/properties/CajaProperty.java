package com.dysconcsa.pancasan.properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CajaProperty {

    private IntegerProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private StringProperty ubicacion;

    public CajaProperty() {
        this.id = new SimpleIntegerProperty();
        this.codigo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.ubicacion = new SimpleStringProperty();
    }

    public CajaProperty(Integer id, String codigo, String descripcion, String ubicacion) {
        this.id = new SimpleIntegerProperty(id);
        this.codigo = new SimpleStringProperty(codigo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.ubicacion = new SimpleStringProperty(ubicacion);
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty getCodigo() {
        return codigo;
    }

    public void setCodigo(StringProperty codigo) {
        this.codigo = codigo;
    }

    public StringProperty getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringProperty descripcion) {
        this.descripcion = descripcion;
    }

    public StringProperty getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(StringProperty ubicacion) {
        this.ubicacion = ubicacion;
    }
}

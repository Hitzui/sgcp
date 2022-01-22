package com.dysconcsa.pancasan.models;

import javafx.beans.property.*;

import javax.persistence.*;

@Entity
@Table(name = "rubro")
public class Rubro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrubro", nullable = false)
    private Integer id;
    @Transient
    private IntegerProperty idProperty;
    @Column(name = "descripcion", nullable = false, length = 250)
    private String descripcion;
    @Transient
    private StringProperty descripcionProperty;

    @Enumerated
    @Column(name = "tipo", nullable = false)
    private TipoRubro tipo;
    @Transient
    private ObjectProperty<TipoRubro> tipoRubroProperty;

    public Rubro() {
        if (this.id != null) {
            this.idProperty = new SimpleIntegerProperty(this.id);
        } else {
            this.idProperty = new SimpleIntegerProperty();
        }
        if (this.descripcion != null) {
            this.descripcionProperty = new SimpleStringProperty(this.descripcion);
        } else {
            this.descripcionProperty = new SimpleStringProperty();
        }
        if (this.tipo != null) {
            this.tipoRubroProperty = new SimpleObjectProperty<>(this.tipo);
        } else {
            this.tipoRubroProperty = new SimpleObjectProperty<>();
        }
    }

    public TipoRubro getTipo() {
        return tipo;
    }

    public void setTipo(TipoRubro tipo) {
        setTipoRubroProperty(tipo);
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        setDescripcionProperty(descripcion);
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        setIdProperty(id);
        this.id = id;
    }

    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idProperty() {
        if (this.id != null) {
            this.idProperty = new SimpleIntegerProperty(this.id);
        }
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        if (this.id != null) {
            this.idProperty = new SimpleIntegerProperty(idProperty);
        }
        this.idProperty.set(idProperty);
    }

    public String getDescripcionProperty() {
        return descripcionProperty.get();
    }

    public StringProperty descripcionProperty() {
        if (this.descripcion != null) {
            this.descripcionProperty = new SimpleStringProperty(descripcion);
        }
        return descripcionProperty;
    }

    public void setDescripcionProperty(String descripcionProperty) {
        this.descripcionProperty.set(descripcionProperty);
    }

    public TipoRubro getTipoRubroProperty() {
        return tipoRubroProperty.get();
    }

    public ObjectProperty<TipoRubro> tipoRubroProperty() {
        if (this.tipo != null) {
            this.tipoRubroProperty = new SimpleObjectProperty<>(this.tipo);
        }
        return tipoRubroProperty;
    }

    public void setTipoRubroProperty(TipoRubro tipoRubroProperty) {
        this.tipoRubroProperty.set(tipoRubroProperty);
    }
}
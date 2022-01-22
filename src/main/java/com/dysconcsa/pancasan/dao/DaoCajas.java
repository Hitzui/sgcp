package com.dysconcsa.pancasan.dao;

import com.dysconcsa.pancasan.models.Caja;
import com.dysconcsa.pancasan.properties.CajaProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DaoCajas extends DataEntity<Caja> {

    public List<Caja> findAll() {
        return super.findAll("select c from Caja c");
    }

    public ObservableList<CajaProperty> observableListCaja() {
        ObservableList<CajaProperty> observableList = FXCollections.observableArrayList();
        for (Caja caja : this.findAll()) {
            var property = new CajaProperty(caja.getId(), caja.getCodigo(), caja.getDescripcion(), caja.getUbicacion());
            observableList.add(property);
        }
        return observableList;
    }

    public Caja findOne(int id){
        return getEntityManager().find(Caja.class, id);
    }
}

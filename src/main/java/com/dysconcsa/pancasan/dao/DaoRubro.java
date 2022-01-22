package com.dysconcsa.pancasan.dao;

import com.dysconcsa.pancasan.models.Rubro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DaoRubro extends DataEntity<Rubro> {
    public List<Rubro> findAll() {
        var sql = "select r from Rubro r";
        return findAll(sql);
    }

    public ObservableList<Rubro> rubroObservableList() {
        return FXCollections.observableArrayList(findAll());
    }

    public Rubro findOne(int id) {
        return getEntityManager().find(Rubro.class, id);
    }
}

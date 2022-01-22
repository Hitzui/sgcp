package com.dysconcsa.pancasan.views.rubro;

import com.dysconcsa.pancasan.PancasanApplication;
import com.dysconcsa.pancasan.models.Rubro;
import com.dysconcsa.pancasan.properties.CajaProperty;
import com.dysconcsa.pancasan.util.Filter;

import java.util.function.Predicate;

public class FilterPresenter extends Filter {

    public FilterPresenter() {
        this.setFilter(PancasanApplication.POPUP_FILTER_RUBRO);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    Predicate<? super Rubro> getPredicate() {
        if (searchField.getText().isEmpty()) return n -> true;
        else return n -> n.descripcionProperty().get().toLowerCase().contains(searchField.getText().toLowerCase());
    }
}

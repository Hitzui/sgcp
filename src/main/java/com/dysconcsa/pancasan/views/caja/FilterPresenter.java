package com.dysconcsa.pancasan.views.caja;

import com.dysconcsa.pancasan.PancasanApplication;
import com.dysconcsa.pancasan.properties.CajaProperty;
import com.dysconcsa.pancasan.util.Filter;

import java.util.function.Predicate;

public class FilterPresenter extends Filter {

    @Override
    public void initialize() {
        super.initialize();
        this.setFilter(PancasanApplication.POPUP_FILTER_CAJA);
    }

    Predicate<? super CajaProperty> getPredicate() {
        if (searchField.getText().isEmpty()) return n -> true;
        else return n -> n.getCodigo().get().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                n.getDescripcion().get().toLowerCase().contains(searchField.getText().toLowerCase());
    }

}

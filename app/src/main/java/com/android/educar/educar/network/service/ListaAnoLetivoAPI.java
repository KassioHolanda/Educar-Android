package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.AnoLetivo;

import java.util.ArrayList;
import java.util.List;

public class ListaAnoLetivoAPI {

    private int count;
    private String next;
    private String previous;
    private List<AnoLetivo> results;

    public ListaAnoLetivoAPI() {
        this.results = new ArrayList<>();
    }

    public List<AnoLetivo> getResults() {
        return results;
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

}

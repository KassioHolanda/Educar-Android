package com.android.educar.educar.service;

import com.android.educar.educar.model.Unidade;

import java.util.ArrayList;
import java.util.List;

public class ListaUnidadesAPI {

    private int count;
    private String next;
    private String previous;
    private List<Unidade> results;

    public ListaUnidadesAPI() {
        this.results = new ArrayList<>();
    }

    public List<Unidade> getResults() {
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

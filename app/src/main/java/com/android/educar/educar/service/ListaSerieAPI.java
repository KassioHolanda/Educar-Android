package com.android.educar.educar.service;

import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.SerieDisciplina;

import java.util.ArrayList;
import java.util.List;

public class ListaSerieAPI {
    private int count;
    private String next;
    private String previous;
    private List<Serie> results;

    public ListaSerieAPI() {
        this.results = new ArrayList<>();
    }

    public List<Serie> getResults() {
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

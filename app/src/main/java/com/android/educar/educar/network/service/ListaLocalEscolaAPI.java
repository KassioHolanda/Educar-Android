package com.android.educar.educar.network.service;

import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;

import java.util.ArrayList;
import java.util.List;

public class ListaLocalEscolaAPI {
    private int count;
    private String next;
    private String previous;
    private List<LocalEscola> results;

    public ListaLocalEscolaAPI() {
        this.results = new ArrayList<>();
    }

    public List<LocalEscola> getResults() {
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

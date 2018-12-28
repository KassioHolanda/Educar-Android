package com.android.educar.educar.network.service;

import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;

import java.util.ArrayList;
import java.util.List;

public class ListaMatriculaAPI {
    private int count;
    private String next;
    private String previous;
    private List<Matricula> results;

    public ListaMatriculaAPI() {
        this.results = new ArrayList<>();
    }

    public List<Matricula> getResults() {
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

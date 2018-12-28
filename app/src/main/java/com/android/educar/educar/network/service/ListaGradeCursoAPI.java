package com.android.educar.educar.network.service;

import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.PessoaFisica;

import java.util.ArrayList;
import java.util.List;

public class ListaGradeCursoAPI {
    private int count;
    private String next;
    private String previous;
    private List<GradeCurso> results;

    public ListaGradeCursoAPI() {
        this.results = new ArrayList<>();
    }

    public List<GradeCurso> getResults() {
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

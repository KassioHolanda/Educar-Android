package com.android.educar.educar.network.service;

import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.UnidadeProfessor;

import java.util.ArrayList;
import java.util.List;

public class ListaUnidadeProfessorAPI {

    private int count;
    private String next;
    private String previous;
    private List<UnidadeProfessor> results;

    public ListaUnidadeProfessorAPI() {
        this.results = new ArrayList<>();
    }

    public List<UnidadeProfessor> getResults() {
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

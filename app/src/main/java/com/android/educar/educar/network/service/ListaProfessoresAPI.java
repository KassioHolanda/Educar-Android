package com.android.educar.educar.network.service;

import com.android.educar.educar.model.Professor;
import com.android.educar.educar.model.UnidadeProfessor;

import java.util.ArrayList;
import java.util.List;

public class ListaProfessoresAPI {

    private int count;
    private String next;
    private String previous;
    private List<Professor> results;

    public ListaProfessoresAPI() {
        this.results = new ArrayList<>();
    }

    public List<Professor> getResults() {
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

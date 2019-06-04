package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.Turma;

import java.util.ArrayList;
import java.util.List;

public class ListaTurmaAPI {
    private int count;
    private String next;
    private String previous;
    private List<Turma> results;

    public ListaTurmaAPI() {
        this.results = new ArrayList<>();
    }

    public List<Turma> getResults() {
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

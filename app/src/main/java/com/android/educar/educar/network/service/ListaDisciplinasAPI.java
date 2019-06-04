package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class ListaDisciplinasAPI {
    private int count;
    private String next;
    private String previous;
    private List<Disciplina> results;

    public ListaDisciplinasAPI() {
        this.results = new ArrayList<>();
    }

    public List<Disciplina> getResults() {
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

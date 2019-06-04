package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.SerieTurma;

import java.util.ArrayList;
import java.util.List;

public class ListaSerieTurmaAPI {
    private int count;
    private String next;
    private String previous;
    private List<SerieTurma> results;

    public ListaSerieTurmaAPI() {
        this.results = new ArrayList<>();
    }

    public List<SerieTurma> getResults() {
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

package com.android.educar.educar.service;

import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.Turma;

import java.util.ArrayList;
import java.util.List;

public class ListaSerieDisciplinaAPI {
    private int count;
    private String next;
    private String previous;
    private List<SerieDisciplina> results;

    public ListaSerieDisciplinaAPI() {
        this.results = new ArrayList<>();
    }

    public List<SerieDisciplina> getResults() {
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

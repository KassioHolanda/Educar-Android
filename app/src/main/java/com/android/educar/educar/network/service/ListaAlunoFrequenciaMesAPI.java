package com.android.educar.educar.network.service;

import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunoFrequenciaMesAPI {
    private int count;
    private String next;
    private String previous;
    private List<AlunoFrequenciaMes> results;

    public ListaAlunoFrequenciaMesAPI() {
        this.results = new ArrayList<>();
    }

    public List<AlunoFrequenciaMes> getResults() {
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

package com.android.educar.educar.service;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosAPI {

    private int count;
    private String next;
    private String previous;
    private List<Aluno> results;

    public ListaAlunosAPI() {
        this.results = new ArrayList<>();
    }

    public List<Aluno> getResults() {
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

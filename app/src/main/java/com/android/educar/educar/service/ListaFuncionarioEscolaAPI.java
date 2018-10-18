package com.android.educar.educar.service;

import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.FuncionarioEscola;

import java.util.ArrayList;
import java.util.List;

public class ListaFuncionarioEscolaAPI {
    private int count;
    private String next;
    private String previous;
    private List<FuncionarioEscola> results;

    public ListaFuncionarioEscolaAPI() {
        this.results = new ArrayList<>();
    }

    public List<FuncionarioEscola> getResults() {
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

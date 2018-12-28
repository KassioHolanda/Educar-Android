package com.android.educar.educar.network.service;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.PessoaFisica;

import java.util.ArrayList;
import java.util.List;

public class ListaPessoaFisicaAPI {

    private int count;
    private String next;
    private String previous;
    private List<PessoaFisica> results;

    public ListaPessoaFisicaAPI() {
        this.results = new ArrayList<>();
    }

    public List<PessoaFisica> getResults() {
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

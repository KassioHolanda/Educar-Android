package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.Ocorrencia;

import java.util.ArrayList;
import java.util.List;

public class ListaOcorrenciaAPI {
    private int count;
    private String next;
    private String previous;
    private List<Ocorrencia> results;

    public ListaOcorrenciaAPI() {
        this.results = new ArrayList<>();
    }

    public List<Ocorrencia> getResults() {
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

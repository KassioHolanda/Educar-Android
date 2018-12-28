package com.android.educar.educar.network.service;

import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.TipoOcorrencia;

import java.util.ArrayList;
import java.util.List;

public class ListaTipoOcorrenciaAPI {
    private int count;
    private String next;
    private String previous;
    private List<TipoOcorrencia> results;

    public ListaTipoOcorrenciaAPI() {
        this.results = new ArrayList<>();
    }

    public List<TipoOcorrencia> getResults() {
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

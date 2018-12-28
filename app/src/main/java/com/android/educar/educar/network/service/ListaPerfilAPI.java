package com.android.educar.educar.network.service;

import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Perfil;

import java.util.ArrayList;
import java.util.List;

public class ListaPerfilAPI {
    private int count;
    private String next;
    private String previous;
    private List<Perfil> results;

    public ListaPerfilAPI() {
        this.results = new ArrayList<>();
    }

    public List<Perfil> getResults() {
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

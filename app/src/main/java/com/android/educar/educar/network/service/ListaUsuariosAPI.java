package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuariosAPI {
    private int count;
    private String next;
    private String previous;
    private List<Usuario> results;

    public ListaUsuariosAPI() {
        this.results = new ArrayList<>();
    }

    public List<Usuario> getResults() {
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

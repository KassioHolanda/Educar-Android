package com.android.educar.educar.network.service;

import com.android.educar.educar.model.modelalterado.Bimestre;

import java.util.ArrayList;
import java.util.List;

public class ListaBimestreAPI {
    private int count;
    private String next;
    private String previous;
    private List<Bimestre> results;

    public ListaBimestreAPI() {
        this.results = new ArrayList<>();
    }

    public List<Bimestre> getResults() {
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

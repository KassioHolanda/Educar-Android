package com.android.educar.educar.network.service;

import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.SituacaoTurmaMes;

import java.util.ArrayList;
import java.util.List;

public class ListaSituacaoTurmaMesAPI {
    private int count;
    private String next;
    private String previous;
    private List<SituacaoTurmaMes> results;

    public ListaSituacaoTurmaMesAPI() {
        this.results = new ArrayList<>();
    }

    public List<SituacaoTurmaMes> getResults() {
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

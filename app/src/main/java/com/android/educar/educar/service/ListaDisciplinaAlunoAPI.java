package com.android.educar.educar.service;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.DisciplinaAluno;

import java.util.ArrayList;
import java.util.List;

public class ListaDisciplinaAlunoAPI {

    private int count;
    private String next;
    private String previous;
    private List<DisciplinaAluno> results;

    public ListaDisciplinaAlunoAPI() {
        this.results = new ArrayList<>();
    }

    public List<DisciplinaAluno> getResults() {
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

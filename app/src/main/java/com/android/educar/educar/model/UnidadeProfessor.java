package com.android.educar.educar.model;

import com.orm.SugarRecord;

public class UnidadeProfessor {
    private long pk;
    private long unidade;
    private long professor;

    public UnidadeProfessor() {
    }

    public UnidadeProfessor(long unidade, long professor) {
        this.unidade = unidade;
        this.professor = professor;
    }

    public long getPk() {
        return pk;
    }

    public long getUnidade() {
        return unidade;
    }

    public long getProfessor() {
        return professor;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }

    public void setProfessor(long professor) {
        this.professor = professor;
    }
}

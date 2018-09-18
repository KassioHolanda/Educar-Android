package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Frequencia {
    @SerializedName("pk")
    private long pk;
    @SerializedName("aluno")
    private long aluno;
    @SerializedName("aula")
    private long aula;
    @SerializedName("presente")
    private boolean presente;


    public Frequencia() {
    }

    public Frequencia(long aluno, long aula, boolean presente) {
        this.pk = pk;
        this.presente = presente;
        this.aluno = aluno;
        this.aula = aula;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setAluno(long aluno) {
        this.aluno = aluno;
    }

    public void setAula(long aula) {
        this.aula = aula;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public long getPk() {
        return pk;
    }

    public long getAluno() {
        return aluno;
    }

    public long getAula() {
        return aula;
    }
}

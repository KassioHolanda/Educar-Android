package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;


public class Aluno {
    @SerializedName("pk")
    private long pk;
    @SerializedName("nome_aluno")
    private String nomeAluno;
    @SerializedName("turma")
    private long turma;
    @SerializedName("quantidade_faltas")
    private int quantidadeFalta;

    public Aluno() {
    }

    public Aluno(long pk, String nomeAluno, long turma, int quantidadeFalta) {
        this.pk = pk;
        this.nomeAluno = nomeAluno;
        this.turma = turma;
        this.quantidadeFalta = quantidadeFalta;
    }

    public long getPk() {
        return pk;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public long getTurma() {
        return turma;
    }

    public int getQuantidadeFalta() {
        return quantidadeFalta;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public void setTurma(long turma) {
        this.turma = turma;
    }

    public void setQuantidadeFalta(int quantidadeFalta) {
        this.quantidadeFalta = quantidadeFalta;
    }

    @Override
    public String toString() {
        return this.nomeAluno;
    }
}

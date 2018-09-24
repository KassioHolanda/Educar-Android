package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;


public class Aluno {
    @SerializedName("pk")
    private long pk;
    @SerializedName("nome_aluno")
    private String nomeAluno;
    @SerializedName("quantidade_faltas")
    private int quantidadeFalta;

    public Aluno() {
    }

    public Aluno(String nomeAluno, int quantidadeFalta) {
        this.nomeAluno = nomeAluno;
        this.quantidadeFalta = quantidadeFalta;
    }

    public long getPk() {
        return pk;
    }

    public String getNomeAluno() {
        return nomeAluno;
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


    public void setQuantidadeFalta(int quantidadeFalta) {
        this.quantidadeFalta = quantidadeFalta;
    }

    @Override
    public String toString() {
        return this.nomeAluno;
    }
}

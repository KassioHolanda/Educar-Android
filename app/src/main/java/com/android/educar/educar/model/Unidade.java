package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Unidade {

    @SerializedName("pk")
    private long pk;
    @SerializedName("nome_unidade")
    private String nomeUnidade;
    @SerializedName("minhas_turmas")
    private List<Turma> minhasTurmas;

    public Unidade() {
    }

    public Unidade(String nomeUnidade) {
        this.pk = pk;
        this.nomeUnidade = nomeUnidade;
        this.minhasTurmas = new ArrayList<>();
    }

    public long getPk() {
        return pk;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public List<Turma> getMinhasTurmas() {
        return minhasTurmas;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public void setMinhasTurmas(List<Turma> minhasTurmas) {
        this.minhasTurmas = minhasTurmas;
    }

    @Override
    public String toString() {
        return this.nomeUnidade;
    }
}

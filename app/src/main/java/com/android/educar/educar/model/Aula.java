package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class Aula {
    @SerializedName("pk")
    private long pk;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("turma")
    private long turma;
    @SerializedName("frequencia_aula")
    private List<Frequencia> frequencias;
    @SerializedName("data")
    private Date data;

    public Aula() {
    }

    public Aula(String descricao, long turma) {
        this.pk = pk;
        this.descricao = descricao;
        this.turma = turma;
        data = new Date();
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getTurma() {
        return turma;
    }

    public void setTurma(long turma) {
        this.turma = turma;
    }

    public List<Frequencia> getFrequencias() {
        return frequencias;
    }

    public void setFrequencias(List<Frequencia> frequencias) {
        this.frequencias = frequencias;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

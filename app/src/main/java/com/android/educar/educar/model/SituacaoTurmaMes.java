package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SituacaoTurmaMes extends RealmObject {
    @PrimaryKey
    private long id;
    @SerializedName("datahora")
    private String dataHora;
    private String status;
    private long turma;
    @SerializedName("quantidadeaproados")
    private int quantidadeAprovados;
    @SerializedName("quantidadereprovados")
    private int quantidadeReprovados;
    private long bimestre;
    private boolean novo;

    public SituacaoTurmaMes() {
    }

    public SituacaoTurmaMes(String dataHora, String status, long tuma, int quantidadeAprovados, int quantidadeReprovados, long bimestre) {
        this.dataHora = dataHora;
        this.status = status;
        this.turma = tuma;
        this.quantidadeAprovados = quantidadeAprovados;
        this.quantidadeReprovados = quantidadeReprovados;
        this.bimestre = bimestre;
        this.novo = false;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getQuantidadeAprovados() {
        return quantidadeAprovados;
    }

    public void setQuantidadeAprovados(int quantidadeAprovados) {
        this.quantidadeAprovados = quantidadeAprovados;
    }

    public int getQuantidadeReprovados() {
        return quantidadeReprovados;
    }

    public void setQuantidadeReprovados(int quantidadeReprovados) {
        this.quantidadeReprovados = quantidadeReprovados;
    }

    public long getBimestre() {
        return bimestre;
    }

    public void setBimestre(long bimestre) {
        this.bimestre = bimestre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTurma() {
        return turma;
    }

    public void setTurma(long turma) {
        this.turma = turma;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }
}

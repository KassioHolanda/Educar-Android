package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SituacaoTurmaMes extends RealmObject {
    @PrimaryKey
    private long id;
    @SerializedName("datahora")
    private Date dataHora;
    private String status;
    private long tuma;
    @SerializedName("quantidadeaproados")
    private int quantidadeAprovados;
    @SerializedName("quantidadereprovados")
    private int quantidadeReprovados;
    private long bimestre;

    public SituacaoTurmaMes() {
    }

    public SituacaoTurmaMes(Date dataHora, String status, long tuma, int quantidadeAprovados, int quantidadeReprovados, long bimestre) {
        this.dataHora = dataHora;
        this.status = status;
        this.tuma = tuma;
        this.quantidadeAprovados = quantidadeAprovados;
        this.quantidadeReprovados = quantidadeReprovados;
        this.bimestre = bimestre;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTuma() {
        return tuma;
    }

    public void setTuma(long tuma) {
        this.tuma = tuma;
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
}

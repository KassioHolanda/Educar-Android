package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SituacaoTurmaMes extends RealmObject{
    @PrimaryKey
    private Long id;
    @SerializedName("datahora")
    private String dataHora;
    private String status;
//    private Turma turma;
    @SerializedName("quantidadeaproados")
    private int quantidadeAprovados;
    @SerializedName("quantidadereprovados")
    private int quantidadeReprovados;
    private Bimestre bimestre;
    private boolean novo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public Turma getTurma() {
//        return turma;
//    }
//
//    public void setTurma(Turma turma) {
//        this.turma = turma;
//    }

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

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }
}

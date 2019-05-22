package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SituacaoTurmaMes extends RealmObject{
    @PrimaryKey
    private Long id;
    @SerializedName("datahora")
    private String dataHora;
    private String status;
    private Long turma;
    @SerializedName("quantidadeaproados")
    private int quantidadeAprovados;
    @SerializedName("quantidadereprovados")
    private int quantidadeReprovados;
    private Bimestre bimestre;
    private boolean novo;

    public SituacaoTurmaMes() {

    }

    public SituacaoTurmaMes(String dataHora, String status, int quantidadeAprovados, int quantidadeReprovados, Bimestre bimestre, boolean novo, Long turma) {
        this.dataHora = dataHora;
        this.status = status;
        this.quantidadeAprovados = quantidadeAprovados;
        this.quantidadeReprovados = quantidadeReprovados;
        this.bimestre = bimestre;
        this.novo = novo;
        this.turma = turma;
    }

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

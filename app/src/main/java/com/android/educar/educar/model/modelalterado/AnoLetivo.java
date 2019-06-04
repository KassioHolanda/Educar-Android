package com.android.educar.educar.model.modelalterado;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AnoLetivo extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    @SerializedName("datainicio")
    private Date dataInicio;
    @SerializedName("datafinal")
    private Date dataFinal;
    @SerializedName("fechadonota")
    private boolean fechadoNota;

    public AnoLetivo() {
    }

    public AnoLetivo(String descricao, Date dataInicio, Date dataFinal, boolean fechadoNota) {
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.fechadoNota = fechadoNota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public boolean isFechadoNota() {
        return fechadoNota;
    }

    public void setFechadoNota(boolean fechadoNota) {
        this.fechadoNota = fechadoNota;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}

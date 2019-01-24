package com.android.educar.educar.model;

import android.renderscript.Short3;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Turma extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    private String turno;
    private Long sala;
    @SerializedName("anoletivo")
    private Long anoLetivo;
    private Long serie;
    private String nivel;
    @SerializedName("statusturma")
    private String statusTurma;

    public Turma() {

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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Long getSala() {
        return sala;
    }

    public void setSala(Long sala) {
        this.sala = sala;
    }

    public Long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Long getSerie() {
        return serie;
    }

    public void setSerie(Long serie) {
        this.serie = serie;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

    public String getStatusTurma() {
        return statusTurma;
    }

    public void setStatusTurma(String statusTurma) {
        this.statusTurma = statusTurma;
    }
}

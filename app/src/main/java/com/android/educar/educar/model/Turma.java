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
    private long id;
    private String descricao;
    private String turno;
    private long sala;
    @SerializedName("anoletivo")
    private long anoLetivo;
    private long serie;
    private String nivel;
    @SerializedName("statusturma")
    private String statusTurma;

    public Turma() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getSala() {
        return sala;
    }

    public void setSala(long sala) {
        this.sala = sala;
    }

    public long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public long getSerie() {
        return serie;
    }

    public void setSerie(long serie) {
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

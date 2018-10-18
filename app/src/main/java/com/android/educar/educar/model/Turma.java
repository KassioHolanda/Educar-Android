package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Turma extends RealmObject{

    @PrimaryKey
    private long id;
    private String descricao;
    private String turno;
    private long sala;
    private long anoletivo;
    private long serie;
    private String nivel;

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

    public long getAnoletivo() {
        return anoletivo;
    }

    public void setAnoletivo(long anoletivo) {
        this.anoletivo = anoletivo;
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
}

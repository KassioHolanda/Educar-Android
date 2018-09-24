package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Turma {

    private long id;
    private String descricao;
    private String turno;
    private long sala;
    private long anoletivo;
    private long serie;
    private String nivel;

    public Turma() {
    }

    public Turma(String descricao, String turno, long sala, long anoletivo, long serie, String nivel) {
        this.descricao = descricao;
        this.turno = turno;
        this.sala = sala;
        this.anoletivo = anoletivo;
        this.serie = serie;
        this.nivel = nivel;
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
}

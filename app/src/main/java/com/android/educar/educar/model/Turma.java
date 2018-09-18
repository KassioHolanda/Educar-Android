package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    @SerializedName("pk")
    private long pk;
    @SerializedName("unidade")
    private long unidade;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("turno")
    private String turno;
    @SerializedName("meus_alunos")
    private List<Aluno> meusAlunos;

    public Turma() {
    }

    public Turma(long unidade, String descricao, String turno) {
        this.pk = pk;
        this.unidade = unidade;
        this.descricao = descricao;
        this.turno = turno;
        this.meusAlunos = new ArrayList<>();
    }

    public long getPk() {
        return pk;
    }

    public long getUnidade() {
        return unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTurno() {
        return turno;
    }

    public List<Aluno> getMeusAlunos() {
        return meusAlunos;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setMeusAlunos(List<Aluno> meusAlunos) {
        this.meusAlunos = meusAlunos;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}

package com.android.educar.educar.model;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Frequencia extends RealmObject {
    @PrimaryKey
    private Long id;
    private Long matricula;
    private long pessoaFisica;
    private long turma;
    private long disciplina;
    private long unidade;
    private boolean presenca;
    private boolean novo;
    private String date;
    private boolean alterado;

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public boolean isAlterado() {
        return alterado;
    }

    public long getPessoafisica() {
        return pessoaFisica;
    }

    public void setPessoafisica(long pessoafisica) {
        this.pessoaFisica = pessoafisica;
    }

    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
    }

    public boolean isPresenca() {
        return presenca;
    }

    public void setPresenca(boolean presenca) {
        this.presenca = presenca;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTurma() {
        return turma;
    }

    public void setTurma(long turma) {
        this.turma = turma;
    }

    public long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(long disciplina) {
        this.disciplina = disciplina;
    }

    public long getUnidade() {
        return unidade;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }
}

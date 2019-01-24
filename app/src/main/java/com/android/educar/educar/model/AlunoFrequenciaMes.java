package com.android.educar.educar.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import javax.annotation.ParametersAreNullableByDefault;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlunoFrequenciaMes extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("totalfaltas")
    private int totalFaltas;
    private Long matricula;
    private Long bimestre;
    private boolean novo;
    private Long disciplina;
    @SerializedName("tipolancamentofrequencia")
    private String tipoLancamentoFrequencia;
    @SerializedName("disciplinaaluno")
    @ParametersAreNullableByDefault
    private Long disciplinaAluno;

    public AlunoFrequenciaMes() {
    }

    public AlunoFrequenciaMes(Long id, int totalFaltas, Long matricula, Long bimestre,  Long disciplina, String tipoLancamentoFrequencia, Long disciplinaAluno) {
        this.id = id;
        this.totalFaltas = totalFaltas;
        this.matricula = matricula;
        this.bimestre = bimestre;
        this.novo = novo;
        this.disciplina = disciplina;
        this.tipoLancamentoFrequencia = tipoLancamentoFrequencia;
        this.disciplinaAluno = disciplinaAluno;
    }

    public String getTipoLancamentoFrequencia() {
        return tipoLancamentoFrequencia;
    }

    public void setTipoLancamentoFrequencia(String tipoLancamentoFrequencia) {
        this.tipoLancamentoFrequencia = tipoLancamentoFrequencia;
    }

    public Long getDisciplinaAluno() {
        return disciplinaAluno;
    }

    public void setDisciplinaAluno(Long disciplinaAluno) {
        this.disciplinaAluno = disciplinaAluno;
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    public Long getBimestre() {
        return bimestre;
    }

    public void setBimestre(Long bimestre) {
        this.bimestre = bimestre;
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

    public int getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(int totalFaltas) {
        this.totalFaltas = totalFaltas;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

}

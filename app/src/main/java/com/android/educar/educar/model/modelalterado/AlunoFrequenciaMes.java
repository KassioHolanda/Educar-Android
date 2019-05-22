package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlunoFrequenciaMes extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("totalfaltas")
    private int totalFaltas;
    private Bimestre bimestre;
    private boolean novo;
    private Disciplina disciplina;
    @SerializedName("tipolancamentofrequencia")
    private String tipoLancamentoFrequencia;
    private boolean alterado;
    private Long matricula;
//    @SerializedName("disciplinaaluno")
//    private Long disciplinaAluno;

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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getTipoLancamentoFrequencia() {
        return tipoLancamentoFrequencia;
    }

    public void setTipoLancamentoFrequencia(String tipoLancamentoFrequencia) {
        this.tipoLancamentoFrequencia = tipoLancamentoFrequencia;
    }

    public boolean isAlterado() {
        return alterado;
    }

    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
}

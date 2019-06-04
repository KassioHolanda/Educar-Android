package com.android.educar.educar.model.modeloriginal;

import com.google.gson.annotations.SerializedName;

public class AlunoFrequenciaMes {
    private Long id;
    @SerializedName("totalfaltas")
    private int totalFaltas;
    @SerializedName("disciplinaaluno")
    private Long disciplinaAluno;
    private Long mes;
    private Long matricula;
    private Long bimestre;
    @SerializedName("criterioaluno")
    private Long criterioAluno;
    @SerializedName("tipolancamentofrequencia")
    private String tipoLancamentoFrequencia;
    private Long disciplina;
    private Long eixo;

    public AlunoFrequenciaMes(Long id, int totalFaltas, Long disciplinaAluno, Long mes, Long matricula, Long bimestre, Long criterioAluno, String tipoLancamentoFrequencia, Long disciplina, Long eixo) {
        this.id = id;
        this.totalFaltas = totalFaltas;
        this.disciplinaAluno = disciplinaAluno;
        this.mes = mes;
        this.matricula = matricula;
        this.bimestre = bimestre;
        this.criterioAluno = criterioAluno;
        this.tipoLancamentoFrequencia = tipoLancamentoFrequencia;
        this.disciplina = disciplina;
        this.eixo = eixo;
    }

    public AlunoFrequenciaMes(int totalFaltas, Long disciplinaAluno, Long mes, Long matricula, Long bimestre, Long criterioAluno, String tipoLancamentoFrequencia, Long disciplina, Long eixo) {
        this.totalFaltas = totalFaltas;
        this.disciplinaAluno = disciplinaAluno;
        this.mes = mes;
        this.matricula = matricula;
        this.bimestre = bimestre;
        this.criterioAluno = criterioAluno;
        this.tipoLancamentoFrequencia = tipoLancamentoFrequencia;
        this.disciplina = disciplina;
        this.eixo = eixo;
    }
}

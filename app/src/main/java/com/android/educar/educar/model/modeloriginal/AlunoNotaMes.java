package com.android.educar.educar.model.modeloriginal;

import com.google.gson.annotations.SerializedName;

public class AlunoNotaMes {
    private Long id;
    private Float nota;
    private Long sequencia;
    private Long bimestre;
    @SerializedName("disciplinaaluno")
    private Long disciplinaAluno;
    private Long mes;
    private String desempenho;
    @SerializedName("criterioaluno")
    private Long criterioAluno;
    @SerializedName("inseridofechamento")
    private boolean inseridoFechamento;
    @SerializedName("criterioatual")
    private Long criterioAtual;
    @SerializedName("tipolancamentonota")
    private String tipoLancamentoNota;
    @SerializedName("anoletivo")
    private Long anoletivo;
    private Long matricula;
    private Long unidade;
    private Long disciplina;
    private Long eixo;
    @SerializedName("datahora")
    private String dataHora;
    private Long usuario;

    public AlunoNotaMes(Long id, Float nota, Long sequencia, Long bimestre, Long disciplinaAluno, Long mes, String desempenho, Long criterioAluno, boolean inseridoFechamento, Long criterioAtual, String tipoLancamentoNota, Long anoletivo, Long matricula, Long unidade, Long disciplina, Long eixo, String dataHora, Long usuario) {
        this.id = id;
        this.nota = nota;
        this.sequencia = sequencia;
        this.bimestre = bimestre;
        this.disciplinaAluno = disciplinaAluno;
        this.mes = mes;
        this.desempenho = desempenho;
        this.criterioAluno = criterioAluno;
        this.inseridoFechamento = inseridoFechamento;
        this.criterioAtual = criterioAtual;
        this.tipoLancamentoNota = tipoLancamentoNota;
        this.anoletivo = anoletivo;
        this.matricula = matricula;
        this.unidade = unidade;
        this.disciplina = disciplina;
        this.eixo = eixo;
        this.dataHora = dataHora;
        this.usuario = usuario;
    }

    public AlunoNotaMes(Float nota, Long sequencia, Long bimestre, Long disciplinaAluno, Long mes, String desempenho, Long criterioAluno, boolean inseridoFechamento, Long criterioAtual, String tipoLancamentoNota, Long anoletivo, Long matricula, Long unidade, Long disciplina, Long eixo, String dataHora, Long usuario) {
        this.nota = nota;
        this.sequencia = sequencia;
        this.bimestre = bimestre;
        this.disciplinaAluno = disciplinaAluno;
        this.mes = mes;
        this.desempenho = desempenho;
        this.criterioAluno = criterioAluno;
        this.inseridoFechamento = inseridoFechamento;
        this.criterioAtual = criterioAtual;
        this.tipoLancamentoNota = tipoLancamentoNota;
        this.anoletivo = anoletivo;
        this.matricula = matricula;
        this.unidade = unidade;
        this.disciplina = disciplina;
        this.eixo = eixo;
        this.dataHora = dataHora;
        this.usuario = usuario;
    }
}

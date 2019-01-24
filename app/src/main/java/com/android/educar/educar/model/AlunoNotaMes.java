package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlunoNotaMes extends RealmObject {

    @PrimaryKey
    private Long id;
    private float nota;
    @SerializedName("disciplinaaluno")
    private Long disciplinaAluno;
    @SerializedName("inseridoFechamento")
    private boolean inseridoFechamento;
    @SerializedName("tipolancamentonota")
    private String tipoLancamentoNota;
    @SerializedName("anoletivo")
    private Long anoLetivo;
    private Long matricula;
    private Long bimestre;
    private Long unidade;
    private Long disciplina;
    private String datahora;
    private Long usuario;
    private boolean novo;
    private int sequencia;

    public AlunoNotaMes() {
    }

    public AlunoNotaMes(Long id, float nota, Long disciplinaAluno, boolean inseridoFechamento, String tipoLancamentoNota, Long anoLetivo, Long matricula, Long unidade, Long disciplina, String datahora, Long usuario, Long bimestre) {
        this.id = id;
        this.bimestre = bimestre;
        this.nota = nota;
        this.disciplinaAluno = disciplinaAluno;
        this.inseridoFechamento = inseridoFechamento;
        this.tipoLancamentoNota = tipoLancamentoNota;
        this.anoLetivo = anoLetivo;
        this.matricula = matricula;
        this.unidade = unidade;
        this.disciplina = disciplina;
        this.datahora = datahora;
        this.usuario = usuario;
        this.novo = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public Long getDisciplinaAluno() {
        return disciplinaAluno;
    }

    public void setDisciplinaAluno(Long disciplinaAluno) {
        this.disciplinaAluno = disciplinaAluno;
    }

    public boolean isInseridoFechamento() {
        return inseridoFechamento;
    }

    public void setInseridoFechamento(boolean inseridoFechamento) {
        this.inseridoFechamento = inseridoFechamento;
    }

    public String getTipoLancamentoNota() {
        return tipoLancamentoNota;
    }

    public void setTipoLancamentoNota(String tipoLancamentoNota) {
        this.tipoLancamentoNota = tipoLancamentoNota;
    }

    public Long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public Long getUnidade() {
        return unidade;
    }

    public void setUnidade(Long unidade) {
        this.unidade = unidade;
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public Long getBimestre() {
        return bimestre;
    }

    public void setBimestre(Long bimestre) {
        this.bimestre = bimestre;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
}


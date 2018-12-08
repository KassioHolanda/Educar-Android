package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlunoNotaMes extends RealmObject {

    @PrimaryKey
    private long id;
    private float nota;
    @SerializedName("disciplinaaluno")
    private long disciplinaAluno;
    @SerializedName("inseridoFechamento")
    private boolean inseridoFechamento;
    @SerializedName("tipolancamentonota")
    private String tipoLancamentoNota;
    @SerializedName("anoletivo")
    private long anoLetivo;
    private long matricula;
    private long bimestre;
    private long unidade;
    private long disciplina;
    private String datahora;
    private long usuario;
    private boolean novo;
    private int sequencia;

    public AlunoNotaMes() {
    }

    public AlunoNotaMes(long id, float nota, long disciplinaAluno, boolean inseridoFechamento, String tipoLancamentoNota, long anoLetivo, long matricula, long unidade, long disciplina, String datahora, long usuario, long bimestre) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public long getDisciplinaAluno() {
        return disciplinaAluno;
    }

    public void setDisciplinaAluno(long disciplinaAluno) {
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

    public long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public long getUnidade() {
        return unidade;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }

    public long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(long disciplina) {
        this.disciplina = disciplina;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public long getBimestre() {
        return bimestre;
    }

    public void setBimestre(long bimestre) {
        this.bimestre = bimestre;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
}


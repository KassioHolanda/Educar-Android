package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlunoNotaMes extends RealmObject {

    @PrimaryKey
    private long id;
    private Float nota;
    @SerializedName("inseridoFechamento")
    private boolean inseridoFechamento;
    @SerializedName("tipolancamentonota")
    private String tipoLancamentoNota;
    @SerializedName("anoletivo")
    private AnoLetivo anoLetivo;
    @SerializedName("bimestre")
    private Bimestre bimestre;
    private Disciplina disciplina;
    private String datahora;
    private boolean novo;
    private boolean alterado;
    private int sequencia;
    @SerializedName("disciplinaaluno")
    private Long disciplinaAluno;
    private Long matricula;
    private Long usuario;

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public AlunoNotaMes() {
    }

    public Long getDisciplinaAluno() {
        return disciplinaAluno;
    }

    public void setDisciplinaAluno(Long disciplinAluno) {
        this.disciplinaAluno = disciplinAluno;
    }

    public Long getMatricula() {
        return matricula;
    }



    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
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

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public boolean isAlterado() {
        return alterado;
    }

    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
}


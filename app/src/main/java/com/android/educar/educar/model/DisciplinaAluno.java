package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DisciplinaAluno extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("statusdisciplinaaluno")
    private String statusDisciplinaAluno;
    private Long matricula;
    @SerializedName("seriedisciplina")
    private Long serieDisciplina;
    @SerializedName("mesesfechadosnota")
    private int mesesFechadosNota;
    @SerializedName("notaacumulada")
    private Float notaAcumulada;
    @SerializedName("datacadastroprovafinal")
    private Date datacadastroprovafinal;
    @SerializedName("notaprovafinal")
    private Float notaProvaFinal;
    @SerializedName("fechadoprovafinal")
    private Boolean fechadoProvaFInal;
    @SerializedName("datacadastroatualizacaoprovafinal")
    private Date dataCadastroAtualizacaoProvaFinal;
    @SerializedName("notaantigaprovafinal")
    private Float notaAntigaProvaFinal;
    @SerializedName("usuarioatualizacaoprovafinal")
    private Long usuarioAtualizacaoProvaFinal;
    @SerializedName("statusatual")
    private String statusAtual;
    private boolean alterado;

    public DisciplinaAluno() {
    }

    public DisciplinaAluno(String statusDisciplinaAluno, Long matricula, Long serieDisciplina, int mesesFechadosNota, Float notaAcumulada, Date datacadastroprovafinal, Float notaProvaFinal, Boolean fechadoProvaFInal, Date dataCadastroAtualizacaoProvaFinal, Float notaAntigaProvaFinal, Long usuarioAtualizacaoProvaFinal) {
        this.statusDisciplinaAluno = statusDisciplinaAluno;
        this.matricula = matricula;
        this.serieDisciplina = serieDisciplina;
        this.mesesFechadosNota = mesesFechadosNota;
        this.notaAcumulada = notaAcumulada;
        this.datacadastroprovafinal = datacadastroprovafinal;
        this.notaProvaFinal = notaProvaFinal;
        this.fechadoProvaFInal = fechadoProvaFInal;
        this.dataCadastroAtualizacaoProvaFinal = dataCadastroAtualizacaoProvaFinal;
        this.notaAntigaProvaFinal = notaAntigaProvaFinal;
        this.usuarioAtualizacaoProvaFinal = usuarioAtualizacaoProvaFinal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusDisciplinaAluno() {
        return statusDisciplinaAluno;
    }

    public void setStatusDisciplinaAluno(String statusDisciplinaAluno) {
        this.statusDisciplinaAluno = statusDisciplinaAluno;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public Long getSerieDisciplina() {
        return serieDisciplina;
    }

    public void setSerieDisciplina(Long serieDisciplina) {
        this.serieDisciplina = serieDisciplina;
    }

    public int getMesesFechadosNota() {
        return mesesFechadosNota;
    }

    public void setMesesFechadosNota(int mesesFechadosNota) {
        this.mesesFechadosNota = mesesFechadosNota;
    }

    public Float getNotaAcumulada() {
        return notaAcumulada;
    }

    public void setNotaAcumulada(Float notaAcumulada) {
        this.notaAcumulada = notaAcumulada;
    }

    public Date getDatacadastroprovafinal() {
        return datacadastroprovafinal;
    }

    public void setDatacadastroprovafinal(Date datacadastroprovafinal) {
        this.datacadastroprovafinal = datacadastroprovafinal;
    }

    public Float getNotaProvaFinal() {
        return notaProvaFinal;
    }

    public void setNotaProvaFinal(Float notaProvaFinal) {
        this.notaProvaFinal = notaProvaFinal;
    }

    public Boolean getFechadoProvaFInal() {
        return fechadoProvaFInal;
    }

    public void setFechadoProvaFInal(Boolean fechadoProvaFInal) {
        this.fechadoProvaFInal = fechadoProvaFInal;
    }

    public Date getDataCadastroAtualizacaoProvaFinal() {
        return dataCadastroAtualizacaoProvaFinal;
    }

    public void setDataCadastroAtualizacaoProvaFinal(Date dataCadastroAtualizacaoProvaFinal) {
        this.dataCadastroAtualizacaoProvaFinal = dataCadastroAtualizacaoProvaFinal;
    }

    public Float getNotaAntigaProvaFinal() {
        return notaAntigaProvaFinal;
    }

    public void setNotaAntigaProvaFinal(Float notaAntigaProvaFinal) {
        this.notaAntigaProvaFinal = notaAntigaProvaFinal;
    }

    public Long getUsuarioAtualizacaoProvaFinal() {
        return usuarioAtualizacaoProvaFinal;
    }

    public void setUsuarioAtualizacaoProvaFinal(Long usuarioAtualizacaoProvaFinal) {
        this.usuarioAtualizacaoProvaFinal = usuarioAtualizacaoProvaFinal;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public boolean isAlterado() {
        return alterado;
    }

    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
    }
}

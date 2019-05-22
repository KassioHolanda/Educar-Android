package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DisciplinaAluno extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("statusdisciplinaaluno")
    private String statusDisciplinaAluno;
    @SerializedName("seriedisciplina")
    private SerieDisciplina serieDisciplina;
    @SerializedName("mesesfechadosnota")
    private int mesesFechadosNota;
    @SerializedName("notaacumulada")
    private Float notaAcumulada;
    @SerializedName("datacadastroprovafinal")
    private String datacadastroprovafinal;
    @SerializedName("notaprovafinal")
    private Float notaProvaFinal;
    @SerializedName("fechadoprovafinal")
    private Boolean fechadoProvaFInal;
    @SerializedName("datacadastroatualizacaoprovafinal")
    private Date dataCadastroAtualizacaoProvaFinal;
    @SerializedName("notaantigaprovafinal")
    private Float notaAntigaProvaFinal;
    @SerializedName("usuarioatualizacaoprovafinal")
    private Usuario usuarioAtualizacaoProvaFinal;
    @SerializedName("statusatual")
    private String statusAtual;
    private boolean alterado;
    @SerializedName("mediaacumulada")
    private double mediaAculumada;
    @SerializedName("cargahoraria")
    private Long cargaHoraria;
    private Long matricula;
    private Aluno aluno;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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

    public String getDatacadastroprovafinal() {
        return datacadastroprovafinal;
    }

    public void setDatacadastroprovafinal(String datacadastroprovafinal) {
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

    public Usuario getUsuarioAtualizacaoProvaFinal() {
        return usuarioAtualizacaoProvaFinal;
    }

    public void setUsuarioAtualizacaoProvaFinal(Usuario usuarioAtualizacaoProvaFinal) {
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

    public double getMediaAculumada() {
        return mediaAculumada;
    }

    public void setMediaAculumada(double mediaAculumada) {
        this.mediaAculumada = mediaAculumada;
    }

    public Long getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Long cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public SerieDisciplina getSerieDisciplina() {
        return serieDisciplina;
    }

    public void setSerieDisciplina(SerieDisciplina serieDisciplina) {
        this.serieDisciplina = serieDisciplina;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
}

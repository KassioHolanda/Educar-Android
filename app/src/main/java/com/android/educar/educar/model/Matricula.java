package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Matricula extends RealmObject {

    @PrimaryKey
    private Long id;
    private Long aluno;
    @SerializedName("statusmatricula")
    private String statusMatricula;
    @SerializedName("datamatricula")
    private Date dataMatricula;
    private Long turma;
    @SerializedName("statusatual")
    private String statusAtual;
    private Long serie;
    @SerializedName("anoletivo")
    private long anoLetivo;



    public Matricula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTurma() {
        return turma;
    }

    public void setTurma(Long turma) {
        this.turma = turma;
    }

    public Long getAluno() {
        return aluno;
    }

    public void setAluno(Long aluno) {
        this.aluno = aluno;
    }

    public String getStatusMatricula() {
        return statusMatricula;
    }

    public void setStatusMatricula(String statusMatricula) {
        this.statusMatricula = statusMatricula;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Long getSerie() {
        return serie;
    }

    public long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public void setSerie(Long serie) {
        this.serie = serie;
    }
}

package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Matricula extends RealmObject{

    @PrimaryKey
    private Long id;
    private Aluno aluno;
    @SerializedName("statusmatricula")
    private String statusMatricula;
    @SerializedName("datamatricula")
    private Date dataMatricula;
    private Turma turma;
    @SerializedName("statusatual")
    private String statusAtual;
    private Serie serie;
    @SerializedName("anoletivo")
    private AnoLetivo anoLetivo;
    @SerializedName("todas_disciplinas_aluno")
    private RealmList<DisciplinaAluno> disciplinaAlunos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
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

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public RealmList<DisciplinaAluno> getDisciplinaAlunos() {
        return disciplinaAlunos;
    }

    public void setDisciplinaAlunos(RealmList<DisciplinaAluno> disciplinaAlunos) {
        this.disciplinaAlunos = disciplinaAlunos;
    }
}

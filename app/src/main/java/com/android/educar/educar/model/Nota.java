package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

public class Nota {

    @SerializedName("pk")
    private long pk;
    @SerializedName("aluno")
    private long aluno;
    @SerializedName("disciplina")
    private long disciplina;
    @SerializedName("nota")
    private float nota;
    @SerializedName("semestre")
    private long bimestre;

    public Nota() {
    }

    public Nota(long aluno, long disciplina, float nota, long bimestre) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.bimestre = bimestre;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public long getAluno() {
        return aluno;
    }

    public void setAluno(long aluno) {
        this.aluno = aluno;
    }

    public long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(long disciplina) {
        this.disciplina = disciplina;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public long getBimestre() {
        return bimestre;
    }

    public void setBimestre(long bimestre) {
        this.bimestre = bimestre;
    }

    @Override
    public String toString() {
        return "" + this.nota;
    }
}

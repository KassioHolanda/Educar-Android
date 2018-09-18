package com.android.educar.educar.model;

public class TurmaAluno {

    private long pk;
    private long aluno;
    private long turma;

    public TurmaAluno() {}

    public TurmaAluno(long aluno, long turma) {
        this.aluno = aluno;
        this.turma = turma;
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

    public long getTurma() {
        return turma;
    }

    public void setTurma(long turma) {
        this.turma = turma;
    }
}

package com.android.educar.educar.model;

public class Ocorrencia {

    private long pk;
    private long aluno;
    private String descricao;

    public Ocorrencia() {
    }

    public Ocorrencia(long aluno, String descricao) {
        this.aluno = aluno;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Disciplina {

    @SerializedName("pk")
    private long pk;
    @SerializedName("professor")
    private long professor;
    @SerializedName("nome")
    private String nome;

    public Disciplina() {
    }

    public Disciplina(long professor, String nome) {
        this.professor = professor;
        this.nome = nome;
    }

    public long getPk() {
        return pk;
    }

    public long getProfessor() {
        return professor;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProfessor(long professor) {
        this.professor = professor;
    }
}

package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GradeCurso extends RealmObject {

    @PrimaryKey
    private long id;
    private long professor;
    private long turma;
    private long seriedisciplina;

    public GradeCurso() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProfessor() {
        return professor;
    }

    public void setProfessor(long professor) {
        this.professor = professor;
    }

    public long getTurma() {
        return turma;
    }

    public void setTurma(long turma) {
        this.turma = turma;
    }

    public long getSeriedisciplina() {
        return seriedisciplina;
    }

    public void setSeriedisciplina(long seriedisciplina) {
        this.seriedisciplina = seriedisciplina;
    }
}

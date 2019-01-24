package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GradeCurso extends RealmObject {

    @PrimaryKey
    private Long id;
    private Long professor;
    private Long turma;
    private Long seriedisciplina;
    private Long disciplina;

    public GradeCurso() {
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfessor() {
        return professor;
    }

    public void setProfessor(Long professor) {
        this.professor = professor;
    }

    public Long getTurma() {
        return turma;
    }

    public void setTurma(Long turma) {
        this.turma = turma;
    }

    public Long getSeriedisciplina() {
        return seriedisciplina;
    }

    public void setSeriedisciplina(Long seriedisciplina) {
        this.seriedisciplina = seriedisciplina;
    }
}

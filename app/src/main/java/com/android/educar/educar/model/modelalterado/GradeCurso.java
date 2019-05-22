package com.android.educar.educar.model.modelalterado;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GradeCurso extends RealmObject {

    @PrimaryKey
    private Long id;
//    private Long professor;
    private Turma turma;
    private SerieDisciplina seriedisciplina;
    private Disciplina disciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public SerieDisciplina getSeriedisciplina() {
        return seriedisciplina;
    }

    public void setSeriedisciplina(SerieDisciplina seriedisciplina) {
        this.seriedisciplina = seriedisciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}

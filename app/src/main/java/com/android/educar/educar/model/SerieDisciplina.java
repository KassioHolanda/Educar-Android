package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SerieDisciplina extends RealmObject{

    @PrimaryKey
    private Long id;
    private Long disciplina;
    private Long serie;

    public SerieDisciplina() {
    }

    public SerieDisciplina(Long disciplina, Long serie) {
        this.disciplina = disciplina;
        this.serie = serie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    public Long getSerie() {
        return serie;
    }

    public void setSerie(Long serie) {
        this.serie = serie;
    }
}

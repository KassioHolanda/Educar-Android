package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SerieDisciplina extends RealmObject{

    @PrimaryKey
    private long id;
    private long disciplina;
    private long serie;

    public SerieDisciplina() {
    }

    public SerieDisciplina(long disciplina, long serie) {
        this.disciplina = disciplina;
        this.serie = serie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(long disciplina) {
        this.disciplina = disciplina;
    }

    public long getSerie() {
        return serie;
    }

    public void setSerie(long serie) {
        this.serie = serie;
    }
}

package com.android.educar.educar.model;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Frequencia extends RealmObject {
    @PrimaryKey
    private long id;
    private long matricula;
    private boolean presenca;
    private boolean novo;

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public boolean isPresenca() {
        return presenca;
    }

    public void setPresenca(boolean presenca) {
        this.presenca = presenca;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }
}

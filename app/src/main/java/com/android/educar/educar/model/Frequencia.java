package com.android.educar.educar.model;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Frequencia extends RealmObject {
    @PrimaryKey
    private Long id;
    private Long matricula;
    private boolean presenca;
    private boolean novo;
    private String date;

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

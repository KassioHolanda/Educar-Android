package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlunoFrequenciaMes extends RealmObject {

    @PrimaryKey
    private long id;
    @SerializedName("totalfaltas")
    private int totalFaltas;
    private long matricula;
    private long bimetre;

    public AlunoFrequenciaMes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(int totalFaltas) {
        this.totalFaltas = totalFaltas;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public long getBimetre() {
        return bimetre;
    }

    public void setBimetre(long bimetre) {
        this.bimetre = bimetre;
    }
}

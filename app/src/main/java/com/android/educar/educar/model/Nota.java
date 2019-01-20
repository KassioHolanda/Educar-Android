package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

public class Nota {

    private long matricula;
    private float nota;

    public Nota() {
    }


    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}

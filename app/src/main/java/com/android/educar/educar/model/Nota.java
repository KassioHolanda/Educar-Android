package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

public class Nota {

    private Long matricula;
    private float nota;

    public Nota() {
    }


    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}

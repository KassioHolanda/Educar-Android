package com.android.educar.educar.model;

public class Serie {
    private long id;
    private String descricao;
    private String nivel;


    public Serie() {
    }

    public Serie(String descricao, String nivel) {
        this.descricao = descricao;
        this.nivel = nivel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}

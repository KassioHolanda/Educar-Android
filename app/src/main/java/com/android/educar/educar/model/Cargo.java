package com.android.educar.educar.model;

import io.realm.RealmObject;

public class Cargo extends RealmObject {
    private Long id;
    private String abreviacao;
    private String descricao;

    public Cargo() {
    }

    public Cargo(String abreviacao, String descricao) {
        this.abreviacao = abreviacao;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}

package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LocalEscola extends RealmObject {

    @PrimaryKey
    private long id;
    private String descricao;
    private long unidade;

    public LocalEscola(String descricao, long unidade) {
        this.descricao = descricao;
        this.unidade = unidade;
    }

    public LocalEscola() {
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

    public long getUnidade() {
        return unidade;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }
}

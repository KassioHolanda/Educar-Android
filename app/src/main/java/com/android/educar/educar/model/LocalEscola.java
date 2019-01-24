package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LocalEscola extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    private Long unidade;

    public LocalEscola(String descricao, Long unidade) {
        this.descricao = descricao;
        this.unidade = unidade;
    }

    public LocalEscola() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getUnidade() {
        return unidade;
    }

    public void setUnidade(Long unidade) {
        this.unidade = unidade;
    }
}

package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipoOcorrencia extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    private int codigo;

    public TipoOcorrencia() {
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

//    public int getCodigo() {
//        return codigo;
//    }
//
//    public void setCodigo(int codigo) {
//        this.codigo = codigo;
//    }

    @Override
    public String toString() {
        return this.descricao;
    }
}

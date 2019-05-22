package com.android.educar.educar.model.modelalterado;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Disciplina extends RealmObject implements Comparable<Disciplina> {

    @PrimaryKey
    private Long id;
    private String descricao;
    private Long codigo;

    public Disciplina() {
    }

    public Disciplina(String descricao, Long codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return this.descricao;
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

    @Override
    public int compareTo(@NonNull Disciplina disciplina) {
        return getDescricao().compareToIgnoreCase(disciplina.getDescricao());
    }
}

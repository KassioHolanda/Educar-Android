package com.android.educar.educar.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Disciplina extends RealmObject implements Comparable{

    @PrimaryKey
    private long id;
    private String descricao;
    private long codigo;

    public Disciplina() {
    }

    public Disciplina(String descricao, long codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return this.descricao;
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

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}

package com.android.educar.educar.model.modelalterado;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bimestre extends RealmObject{
    @PrimaryKey
    private Long id;
    private String descricao;
    private int sequencia;
    private boolean temnota;

    public Bimestre() {
    }

    public Bimestre(Long id, String descricao, int sequencia, boolean temnota) {
        this.id = id;
        this.descricao = descricao;
        this.sequencia = sequencia;
        this.temnota = temnota;
    }

    public Long getId() {
        return id;
    }

    public boolean isTemnota() {
        return temnota;
    }

    public void setTemnota(boolean temnota) {
        this.temnota = temnota;
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

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
}

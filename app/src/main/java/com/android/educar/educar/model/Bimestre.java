package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bimestre extends RealmObject {
    @PrimaryKey
    private long id;
    private String descricao;
    private int sequencia;
    private boolean temnota;

    public Bimestre() {
    }

    public Bimestre(long id, String descricao, int sequencia, boolean temnota) {
        this.id = id;
        this.descricao = descricao;
        this.sequencia = sequencia;
        this.temnota = temnota;
    }

    public long getId() {
        return id;
    }

    public boolean isTemnota() {
        return temnota;
    }

    public void setTemnota(boolean temnota) {
        this.temnota = temnota;
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

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
}

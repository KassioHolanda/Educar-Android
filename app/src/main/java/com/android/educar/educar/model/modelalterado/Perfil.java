package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Perfil extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    @SerializedName("perfilexterno")
    private boolean perfilexterno;
    @SerializedName("vertodasasescolas")
    private boolean verTodasAsEscolas;

    public Perfil() {
    }

    public Perfil(String descricao, boolean perfilexterno, boolean verTodasAsEscolas) {
        this.descricao = descricao;
        this.perfilexterno = perfilexterno;
        this.verTodasAsEscolas = verTodasAsEscolas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPerfilexterno() {
        return perfilexterno;
    }

    public void setPerfilexterno(boolean perfilexterno) {
        this.perfilexterno = perfilexterno;
    }

    public boolean isVerTodasAsEscolas() {
        return verTodasAsEscolas;
    }

    public void setVerTodasAsEscolas(boolean verTodasAsEscolas) {
        this.verTodasAsEscolas = verTodasAsEscolas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

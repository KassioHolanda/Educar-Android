package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Unidade extends RealmObject {

    @PrimaryKey
    private Long id;
    private String abreviacao;
    private String cnpj;
    private String nome;
    @SerializedName("locais_escola")
    private RealmList<LocalEscola> localEscolas;

    public Unidade() {
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public List<LocalEscola> getLocalEscolas() {
        return localEscolas;
    }

    public void setLocalEscolas(RealmList<LocalEscola> localEscolas) {
        this.localEscolas = localEscolas;
    }
}

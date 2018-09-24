package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Unidade {

    @SerializedName("id")
    private long id;
    @SerializedName("abreviacao")
    private String abreviacao;
    @SerializedName("cnpj")
    private String cnpj;
    @SerializedName("nome")
    private String nome;

    public Unidade() {
    }

    public Unidade(String abreviacao, String cnpj, String nome) {
        this.abreviacao = abreviacao;
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}

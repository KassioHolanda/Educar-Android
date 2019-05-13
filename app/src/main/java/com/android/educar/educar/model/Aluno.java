package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Aluno extends RealmObject {
    @PrimaryKey
    private Long id;
    @SerializedName("pessoafisica")
    private Long pessoaFisica;
    @SerializedName("datacadastro")
    private Date dataCadastro;

    public Aluno() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(Long pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}


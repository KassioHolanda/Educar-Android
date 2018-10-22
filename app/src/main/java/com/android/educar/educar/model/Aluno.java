package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Aluno extends RealmObject {
    @PrimaryKey
    private long id;
    @SerializedName("pessoafisica")
    private long pessoaFisica;
    @SerializedName("datacadastro")
    private Date dataCadastro;

    public Aluno() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(long pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}


package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Funcionario extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("escolaridade")
    private String escolaridade;
    @SerializedName("pessoafisica")
    private Long pessoaFisicaId;
    @SerializedName("cargo")
    private Long cargo;
    @SerializedName("cargahoraria")
    private String cargaHoraria;
    @SerializedName("dataadmissao")
    private Date dataAdmissao;
    @SerializedName("statusfuncionario")
    private String statusFuncionario;
    @SerializedName("situacaofuncional")
    private String situacaoFuncional;


    public Funcionario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public Long getPessoaFisicaId() {
        return pessoaFisicaId;
    }

    public void setPessoaFisicaId(Long pessoaFisicaId) {
        this.pessoaFisicaId = pessoaFisicaId;
    }

    public Long getCargo() {
        return cargo;
    }

    public void setCargo(Long cargo) {
        this.cargo = cargo;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getStatusFuncionario() {
        return statusFuncionario;
    }

    public void setStatusFuncionario(String statusFuncionario) {
        this.statusFuncionario = statusFuncionario;
    }

    public String getSituacaoFuncional() {
        return situacaoFuncional;
    }

    public void setSituacaoFuncional(String situacaoFuncional) {
        this.situacaoFuncional = situacaoFuncional;
    }
}

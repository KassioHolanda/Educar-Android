package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Funcionario {
    private long id;
    private String escolaridade;
    @SerializedName("pessoafisica")
    private long pessoaFisica;
    private long cargo;
    @SerializedName("cargahoraria")
    private String cargaHoraria;
    @SerializedName("dataadmissao")
    private Date dataAdmissao;
    @SerializedName("statusfuncionario")
    private String statusFuncionario;

    public Funcionario() {
    }

    public Funcionario(String escolaridade, long pessoaFisica, long cargo, String cargaHoraria, Date dataAdmissao, String statusFuncionario) {
        this.escolaridade = escolaridade;
        this.pessoaFisica = pessoaFisica;
        this.cargo = cargo;
        this.cargaHoraria = cargaHoraria;
        this.dataAdmissao = dataAdmissao;
        this.statusFuncionario = statusFuncionario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public long getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(long pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public long getCargo() {
        return cargo;
    }

    public void setCargo(long cargo) {
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
}

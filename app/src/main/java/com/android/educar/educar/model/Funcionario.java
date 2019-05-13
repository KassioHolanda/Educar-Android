package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Funcionario extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("escolaridade")
    private String escolaridade;
    @SerializedName("pessoafisica")
    private PessoaFisica pessoaFisica;
    @SerializedName("cargo")
    private Cargo cargo;
    @SerializedName("cargahoraria")
    private String cargaHoraria;
    @SerializedName("dataadmissao")
    private String dataAdmissao;
    @SerializedName("statusfuncionario")
    private String statusFuncionario;
    @SerializedName("situacaofuncional")
    private String situacaoFuncional;
    @SerializedName("funcionario_escolas")
    private RealmList<FuncionarioEscola> funcionarioEscolas;
    @SerializedName("grade_curso")
    private RealmList<GradeCurso> gradeCursos;

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

    public PessoaFisica getPessoaFisicaId() {
        return pessoaFisica;
    }

    public void setPessoaFisicaId(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(String dataAdmissao) {
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

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public RealmList<FuncionarioEscola> getFuncionarioEscolas() {
        return funcionarioEscolas;
    }

    public void setFuncionarioEscolas(RealmList<FuncionarioEscola> funcionarioEscolas) {
        this.funcionarioEscolas = funcionarioEscolas;
    }

    public RealmList<GradeCurso> getGradeCursos() {
        return gradeCursos;
    }

    public void setGradeCursos(RealmList<GradeCurso> gradeCursos) {
        this.gradeCursos = gradeCursos;
    }
}

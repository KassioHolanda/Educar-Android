package com.android.educar.educar.model;

public class FuncionarioEscola {
    private long id;
    private Boolean ativo;
    private long unidade;
    private long funcionario;

    public FuncionarioEscola() {

    }

    public FuncionarioEscola(Boolean ativo, long unidade, long funcionario) {
        this.ativo = ativo;
        this.unidade = unidade;
        this.funcionario = funcionario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public long getUnidade() {
        return unidade;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }

    public long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(long funcionario) {
        this.funcionario = funcionario;
    }
}

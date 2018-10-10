package com.android.educar.educar.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FuncionarioEscola extends RealmObject {

    @PrimaryKey
    private long id;
    private Boolean ativo;
    private long unidade;
    private long funcionario;

    public FuncionarioEscola() {

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

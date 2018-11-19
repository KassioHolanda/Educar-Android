package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Usuario extends RealmObject {
    @PrimaryKey
    private long id;
    private boolean ativo;
    private String login;
    private String matricula;
    private String senha;
    @SerializedName("pessoafisica")
    private long pessoaFisica;
    private long perfil;

    public Usuario() {
    }

    public Usuario(boolean ativo, String login, String matricula, String senha, long pessoaFisica, long perfil) {
        this.ativo = ativo;
        this.login = login;
        this.matricula = matricula;
        this.senha = senha;
        this.pessoaFisica = pessoaFisica;
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(long pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public long getPerfil() {
        return perfil;
    }

    public void setPerfil(long perfil) {
        this.perfil = perfil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

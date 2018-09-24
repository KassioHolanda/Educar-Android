package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PessoaFisica {
    private long id;
    private String cpf;
    private String nacionalidade;
    private String sexo;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String rg;

    public PessoaFisica() {
    }

    public PessoaFisica(String cpf, String nacionalidade, String sexo, String email, String senha, Date dataNascimento, String rg) {
        this.cpf = cpf;
        this.nacionalidade = nacionalidade;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.rg = rg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
}

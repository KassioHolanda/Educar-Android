package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Professor {

    @SerializedName("pk")
    private long pk;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("nome")
    private String nome;
    @SerializedName("unidades")
    private List<UnidadeProfessor> unidades;
    @SerializedName("disciplinas")
    private List<Disciplina> disciplinas;
    private String senha;

    public Professor() {
    }

    public Professor(String nome, String cpf, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        unidades = new ArrayList<>();
        disciplinas = new ArrayList<>();
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getPk() {
        return pk;
    }

    public List<UnidadeProfessor> getUnidades() {
        return unidades;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUnidades(List<UnidadeProfessor> unidades) {
        this.unidades = unidades;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}

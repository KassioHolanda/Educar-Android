package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ocorrencia extends RealmObject {

    @PrimaryKey
    private long id;
    @SerializedName("datahoracadastro")
    private Date dataHoraCadastro;
    @SerializedName("funcioanrioescola")
    private long funcionarioEscola;
    private String descricao;
    @SerializedName("matriculaaluno")
    private long matriculaAluno;
    @SerializedName("tipoocorrencia")
    private long tipoOcorrencia;
    private long aluno;
    @SerializedName("anoletivo")
    private long anoLetivo;
    private long funcionario;
    private long unidade;

    public Ocorrencia() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Date dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public long getFuncionarioEscola() {
        return funcionarioEscola;
    }

    public void setFuncionarioEscola(long funcionarioEscola) {
        this.funcionarioEscola = funcionarioEscola;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(long matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public long getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(long tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public long getAluno() {
        return aluno;
    }

    public void setAluno(long aluno) {
        this.aluno = aluno;
    }

    public long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(long funcionario) {
        this.funcionario = funcionario;
    }

    public long getUnidade() {
        return unidade;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }
}

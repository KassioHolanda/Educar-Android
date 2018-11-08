package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Unique;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ocorrencia extends RealmObject {

    @PrimaryKey
    private long id;
    private String datahora;
    private String datahoracadastro;
    @SerializedName("funcionarioescola")
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
    @SerializedName("enviadosms")
    private boolean enviadoSms;
    @SerializedName("dataenviosms")
    private Date dataEnvioSms;
    @SerializedName("resumosms")
    private String resumoSms;
    private String observacao;
    @SerializedName("numerotelefone")
    private int numeroTelefone;
    private boolean novo;

    public Ocorrencia() {
        novo = false;
    }

    public Ocorrencia(long id, String datahora, String dataHoraCadastro, long funcionarioEscola, String descricao, long matriculaAluno, long tipoOcorrencia, long aluno, long anoLetivo, long funcionario, long unidade, boolean enviadoSms, Date dataEnvioSms, String resumoSms, String observacao, int numeroTelefone, boolean novo) {
        this.id = id;
        this.datahora = datahora;
        this.datahoracadastro = dataHoraCadastro;
        this.funcionarioEscola = funcionarioEscola;
        this.descricao = descricao;
        this.matriculaAluno = matriculaAluno;
        this.tipoOcorrencia = tipoOcorrencia;
        this.aluno = aluno;
        this.anoLetivo = anoLetivo;
        this.funcionario = funcionario;
        this.unidade = unidade;
        this.enviadoSms = enviadoSms;
        this.dataEnvioSms = dataEnvioSms;
        this.resumoSms = resumoSms;
        this.observacao = observacao;
        this.numeroTelefone = numeroTelefone;
        this.novo = novo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public String getDatahoracadastro() {
        return datahoracadastro;
    }

    public void setDatahoracadastro(String datahoracadastro) {
        this.datahoracadastro = datahoracadastro;
    }

    public boolean isEnviadoSms() {
        return enviadoSms;
    }

    public void setEnviadoSms(boolean enviadoSms) {
        this.enviadoSms = enviadoSms;
    }

    public Date getDataEnvioSms() {
        return dataEnvioSms;
    }

    public void setDataEnvioSms(Date dataEnvioSms) {
        this.dataEnvioSms = dataEnvioSms;
    }

    public String getResumoSms() {
        return resumoSms;
    }

    public void setResumoSms(String resumoSms) {
        this.resumoSms = resumoSms;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(int numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }
}

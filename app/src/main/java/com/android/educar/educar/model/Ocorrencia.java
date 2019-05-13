package com.android.educar.educar.model;

import com.android.educar.educar.utils.UtilsFunctions;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ocorrencia extends RealmObject {

    @PrimaryKey
    private Long id;
    private String datahora;
    private String datahoracadastro;
    @SerializedName("funcionarioescola")
    private Long funcionarioEscola;
    private String descricao;
    @SerializedName("matriculaaluno")
    private Long matriculaAluno;
    @SerializedName("tipoocorrencia")
    private Long tipoOcorrencia;
    private Long aluno;
    @SerializedName("anoletivo")
    private Long anoLetivo;
    private Long funcionario;
    private Long unidade;
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

    public Ocorrencia(Long id, String datahora, String dataHoraCadastro, Long funcionarioEscola, String descricao, Long matriculaAluno, Long tipoOcorrencia, Long aluno, Long anoLetivo, Long funcionario, Long unidade, boolean enviadoSms, Date dataEnvioSms, String resumoSms, String observacao, int numeroTelefone, boolean novo) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuncionarioEscola() {
        return funcionarioEscola;
    }

    public void setFuncionarioEscola(Long funcionarioEscola) {
        this.funcionarioEscola = funcionarioEscola;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(Long matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public Long getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(Long tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public Long getAluno() {
        return aluno;
    }

    public void setAluno(Long aluno) {
        this.aluno = aluno;
    }

    public Long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Long funcionario) {
        this.funcionario = funcionario;
    }

    public Long getUnidade() {
        return unidade;
    }

    public void setUnidade(Long unidade) {
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

    @Override
    public String toString() {
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return this.descricao + " - " + this.datahora;
    }
}

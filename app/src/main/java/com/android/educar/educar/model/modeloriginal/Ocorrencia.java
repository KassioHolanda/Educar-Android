package com.android.educar.educar.model.modeloriginal;

import com.google.gson.annotations.SerializedName;

public class Ocorrencia {
    private Long id;
    @SerializedName("datahora")
    private String dataHora;
    @SerializedName("datahoracadastro")
    private String datahoraCadastro;
    private String descricao;
    @SerializedName("funcionarioescola")
    private Long funcionarioEscola;
    @SerializedName("matriculaaluno")
    private Long matriculaAluno;
    @SerializedName("tipoocorrencia")
    private Long tipoOcorrencia;
    private Long usuario;
    private Long aluno;
    @SerializedName("anoletivo")
    private Long anoLetivo;
    private Long funcionario;
    private Long unidade;
    @SerializedName("enviadosms")
    private boolean enviadoSms;
    @SerializedName("ano_letivo")
    private Long anoLetivo2;
    @SerializedName("dataenviosms")
    private String dataEnvioSms;
    @SerializedName("telefonepessoa")
    private Long telefonePessoa;
    @SerializedName("resumosms")
    private String resumoSms;
    private String observacao;
    @SerializedName("numerotelefone")
    private String numeroTelefone;

    public Ocorrencia(Long id, String dataHora, String datahoraCadastro, String descricao, Long funcionarioEscola, Long matriculaAluno, Long tipoOcorrencia, Long usuario, Long aluno, Long anoLetivo, Long funcionario, Long unidade, boolean enviadoSms, Long anoLetivo2, String dataEnvioSms, Long telefonePessoa, String resumoSms, String observacao, String numeroTelefone) {
        this.id = id;
        this.dataHora = dataHora;
        this.datahoraCadastro = datahoraCadastro;
        this.descricao = descricao;
        this.funcionarioEscola = funcionarioEscola;
        this.matriculaAluno = matriculaAluno;
        this.tipoOcorrencia = tipoOcorrencia;
        this.usuario = usuario;
        this.aluno = aluno;
        this.anoLetivo = anoLetivo;
        this.funcionario = funcionario;
        this.unidade = unidade;
        this.enviadoSms = enviadoSms;
        this.anoLetivo2 = anoLetivo2;
        this.dataEnvioSms = dataEnvioSms;
        this.telefonePessoa = telefonePessoa;
        this.resumoSms = resumoSms;
        this.observacao = observacao;
        this.numeroTelefone = numeroTelefone;
    }

    public Ocorrencia(String dataHora, String datahoraCadastro, String descricao, Long funcionarioEscola, Long matriculaAluno, Long tipoOcorrencia, Long usuario, Long aluno, Long anoLetivo, Long funcionario, Long unidade, boolean enviadoSms, Long anoLetivo2, String dataEnvioSms, Long telefonePessoa, String resumoSms, String observacao, String numeroTelefone) {
        this.dataHora = dataHora;
        this.datahoraCadastro = datahoraCadastro;
        this.descricao = descricao;
        this.funcionarioEscola = funcionarioEscola;
        this.matriculaAluno = matriculaAluno;
        this.tipoOcorrencia = tipoOcorrencia;
        this.usuario = usuario;
        this.aluno = aluno;
        this.anoLetivo = anoLetivo;
        this.funcionario = funcionario;
        this.unidade = unidade;
        this.enviadoSms = enviadoSms;
        this.anoLetivo2 = anoLetivo2;
        this.dataEnvioSms = dataEnvioSms;
        this.telefonePessoa = telefonePessoa;
        this.resumoSms = resumoSms;
        this.observacao = observacao;
        this.numeroTelefone = numeroTelefone;
    }
}

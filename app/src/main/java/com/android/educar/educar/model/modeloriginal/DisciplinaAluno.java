package com.android.educar.educar.model.modeloriginal;

import com.google.gson.annotations.SerializedName;

public class DisciplinaAluno {
    private Long id;
    @SerializedName("cargahoraria")
    private int cargaHoraria;
    @SerializedName("cargahorariaassistida")
    private int cargaHorariaAssistida;
    @SerializedName("statusdisciplinaaluno")
    private String statusDisciplinaAluno;
    private Long matricula;
    @SerializedName("seriedisciplina")
    private Long serieDisciplina;
    @SerializedName("mediaacumulada")
    private Float mediaAcumulada;
    @SerializedName("mesesfechadonota")
    private int mesesFechadoNota;
    @SerializedName("notaacumulada")
    private Float notaAcumulada;
    @SerializedName("statusatual")
    private String statusAtual;
    @SerializedName("datacadastroprovafinal")
    private String dataCadastroProvaFinal;
    @SerializedName("notaprovafinal")
    private Float notaProvaFinal;
    @SerializedName("usuarioprovafinal")
    private Long usuarioProvaFinal;
    @SerializedName("fechadoprovafinal")
    private boolean fechadoProvaFinal;
    @SerializedName("datacadastroatualizacaoprovafinal")
    private String dataCadastroAtualizacaoProvaFinal;
    @SerializedName("notaantigaprovafinal")
    private Float notaAntigaProvaFinal;
    @SerializedName("usuarioatualilzacaoprovafinal")
    private Long usuarioAtualizacaoProvaFinal;

    public DisciplinaAluno(Long id, int cargaHoraria, int cargaHorariaAssistida, String statusDisciplinaAluno, Long matricula, Long serieDisciplina, Float mediaAcumulada, int mesesFechadoNota, Float notaAcumulada, String statusAtual, String dataCadastroProvaFinal, Float notaProvaFinal, Long usuarioProvaFinal, boolean fechadoProvaFinal, String dataCadastroAtualizacaoProvaFinal, Float notaAntigaProvaFinal, Long usuarioAtualizacaoProvaFinal) {
        this.id = id;
        this.cargaHoraria = cargaHoraria;
        this.cargaHorariaAssistida = cargaHorariaAssistida;
        this.statusDisciplinaAluno = statusDisciplinaAluno;
        this.matricula = matricula;
        this.serieDisciplina = serieDisciplina;
        this.mediaAcumulada = mediaAcumulada;
        this.mesesFechadoNota = mesesFechadoNota;
        this.notaAcumulada = notaAcumulada;
        this.statusAtual = statusAtual;
        this.dataCadastroProvaFinal = dataCadastroProvaFinal;
        this.notaProvaFinal = notaProvaFinal;
        this.usuarioProvaFinal = usuarioProvaFinal;
        this.fechadoProvaFinal = fechadoProvaFinal;
        this.dataCadastroAtualizacaoProvaFinal = dataCadastroAtualizacaoProvaFinal;
        this.notaAntigaProvaFinal = notaAntigaProvaFinal;
        this.usuarioAtualizacaoProvaFinal = usuarioAtualizacaoProvaFinal;
    }

    public DisciplinaAluno(int cargaHoraria, int cargaHorariaAssistida, String statusDisciplinaAluno, Long matricula, Long serieDisciplina, Float mediaAcumulada, int mesesFechadoNota, Float notaAcumulada, String statusAtual, String dataCadastroProvaFinal, Float notaProvaFinal, Long usuarioProvaFinal, boolean fechadoProvaFinal, String dataCadastroAtualizacaoProvaFinal, Float notaAntigaProvaFinal, Long usuarioAtualizacaoProvaFinal) {
        this.cargaHoraria = cargaHoraria;
        this.cargaHorariaAssistida = cargaHorariaAssistida;
        this.statusDisciplinaAluno = statusDisciplinaAluno;
        this.matricula = matricula;
        this.serieDisciplina = serieDisciplina;
        this.mediaAcumulada = mediaAcumulada;
        this.mesesFechadoNota = mesesFechadoNota;
        this.notaAcumulada = notaAcumulada;
        this.statusAtual = statusAtual;
        this.dataCadastroProvaFinal = dataCadastroProvaFinal;
        this.notaProvaFinal = notaProvaFinal;
        this.usuarioProvaFinal = usuarioProvaFinal;
        this.fechadoProvaFinal = fechadoProvaFinal;
        this.dataCadastroAtualizacaoProvaFinal = dataCadastroAtualizacaoProvaFinal;
        this.notaAntigaProvaFinal = notaAntigaProvaFinal;
        this.usuarioAtualizacaoProvaFinal = usuarioAtualizacaoProvaFinal;
    }
}

package com.android.educar.educar.model.modeloriginal;

import com.google.gson.annotations.SerializedName;

public class SituacaoTurmaMes {
    private Long id;
    @SerializedName("datahora")
    private String dataHora;
    private String status;
    private Long mes;
    private Long turma;
    @SerializedName("quantidadeaproados")
    private int quantidadeAprovados;
    @SerializedName("quantidadereprovados")
    private int quantidadeReprovados;
    private Long bimestre;

    public SituacaoTurmaMes(Long id, String dataHora, String status, Long mes, Long turma, int quantidadeAprovados, int quantidadeReprovados, Long bimestre) {
        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
        this.mes = mes;
        this.turma = turma;
        this.quantidadeAprovados = quantidadeAprovados;
        this.quantidadeReprovados = quantidadeReprovados;
        this.bimestre = bimestre;
    }

    public SituacaoTurmaMes(String dataHora, String status, Long mes, Long turma, int quantidadeAprovados, int quantidadeReprovados, Long bimestre) {
        this.dataHora = dataHora;
        this.status = status;
        this.mes = mes;
        this.turma = turma;
        this.quantidadeAprovados = quantidadeAprovados;
        this.quantidadeReprovados = quantidadeReprovados;
        this.bimestre = bimestre;
    }
}

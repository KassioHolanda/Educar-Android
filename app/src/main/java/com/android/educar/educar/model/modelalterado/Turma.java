package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Turma extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    private String turno;
//    private LocalEscola sala;
    @SerializedName("anoletivo")
    private AnoLetivo anoLetivo;
    private Serie serie;
    private String nivel;
    @SerializedName("statusturma")
    private String statusTurma;
    @SerializedName("grade_curso")
    private RealmList<GradeCurso> gradeCursos;
    private RealmList<Matricula> matriculas;
    @SerializedName("situacao_turma_mes")
    private RealmList<SituacaoTurmaMes> situacoesTurmaMes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getStatusTurma() {
        return statusTurma;
    }

    public void setStatusTurma(String statusTurma) {
        this.statusTurma = statusTurma;
    }

    public RealmList<GradeCurso> getGradeCursos() {
        return gradeCursos;
    }

    public void setGradeCursos(RealmList<GradeCurso> gradeCursos) {
        this.gradeCursos = gradeCursos;
    }

    public RealmList<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(RealmList<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public RealmList<SituacaoTurmaMes> getSituacoesTurmaMes() {
        return situacoesTurmaMes;
    }

    public void setSituacoesTurmaMes(RealmList<SituacaoTurmaMes> situacoesTurmaMes) {
        this.situacoesTurmaMes = situacoesTurmaMes;
    }
}

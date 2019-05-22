package com.android.educar.educar.model.modelalterado;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Matricula extends RealmObject implements Comparable<Matricula> {

    @PrimaryKey
    private Long id;
    private Aluno aluno;
    @SerializedName("statusmatricula")
    private String statusMatricula;
    @SerializedName("datamatricula")
    private String dataMatricula;
    private Turma turma;
    @SerializedName("statusatual")
    private String statusAtual;
    private Serie serie;
    @SerializedName("anoletivo")
    private AnoLetivo anoLetivo;
    @SerializedName("todas_disciplinas_aluno")
    private RealmList<DisciplinaAluno> disciplinaAlunos;
    @SerializedName("aluno_frequencia_mes")
    private RealmList<AlunoFrequenciaMes> alunoFrequenciasMes;
    @SerializedName("aluno_nota_mes")
    private RealmList<AlunoNotaMes> alunosNotaMes;

    public Long getId() {
        return id;
    }

    public RealmList<AlunoFrequenciaMes> getAlunoFrequenciasMes() {
        return alunoFrequenciasMes;
    }

    public void setAlunoFrequenciasMes(RealmList<AlunoFrequenciaMes> alunoFrequenciasMes) {
        this.alunoFrequenciasMes = alunoFrequenciasMes;
    }

    public RealmList<AlunoNotaMes> getAlunosNotaMes() {
        return alunosNotaMes;
    }

    public void setAlunosNotaMes(RealmList<AlunoNotaMes> alunosNotaMes) {
        this.alunosNotaMes = alunosNotaMes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getStatusMatricula() {
        return statusMatricula;
    }

    public void setStatusMatricula(String statusMatricula) {
        this.statusMatricula = statusMatricula;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public RealmList<DisciplinaAluno> getDisciplinaAlunos() {
        return disciplinaAlunos;
    }

    public void setDisciplinaAlunos(RealmList<DisciplinaAluno> disciplinaAlunos) {
        this.disciplinaAlunos = disciplinaAlunos;
    }

    @Override
    public String toString() {
        return this.getAluno().getPessoaFisica().getNome();
    }

    @Override
    public int compareTo(@NonNull Matricula matricula) {
        return getAluno().getPessoaFisica().getNome().compareToIgnoreCase(matricula.getAluno().getPessoaFisica().getNome());
    }
}

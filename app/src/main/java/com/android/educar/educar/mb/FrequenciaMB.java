package com.android.educar.educar.mb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.model.modelalterado.AlunoFrequenciaMes;
import com.android.educar.educar.model.modelalterado.DisciplinaAluno;
import com.android.educar.educar.model.modelalterado.Frequencia;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.model.modelalterado.SerieDisciplina;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.utils.Preferences;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrequenciaMB {

    private Context context;
    private Realm realm;
    private Preferences preferences;
    private BimestreMB bimestreMB;
    private AlunoFrequenciaMes alunoFrequenciaMes;
    private Turma turma;
    private DisciplinaAluno disciplinaAluno;

    public FrequenciaMB(Context context) {
        this.context = context;
        configRealm();
        preferences = new Preferences(context);
        bimestreMB = new BimestreMB(context);
        turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst());
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarFrequencia() {

        Long idBimestreAtual = bimestreMB.getBimestreAtual();
        RealmResults<Frequencia> frequencias = realm.where(Frequencia.class).findAll();

        SerieDisciplina serieDisciplina = realm.copyFromRealm(realm.where(SerieDisciplina.class).equalTo("disciplina.id", preferences.getSavedLong("id_disciplina")).findFirst());

        for (Frequencia frequencia : frequencias) {

            Matricula matricula = realm.copyFromRealm(realm.where(Matricula.class).equalTo("id", frequencia.getMatricula()).findFirst());
            disciplinaAluno = realm.copyFromRealm(realm.where(DisciplinaAluno.class).equalTo("serieDisciplina.id", serieDisciplina.getId()).findFirst());
            disciplinaAluno = null;
            for (DisciplinaAluno discAluno : matricula.getDisciplinaAlunos()) {
                if (discAluno.getSerieDisciplina().getId() == serieDisciplina.getId()) {
                    disciplinaAluno = discAluno;
                    for (AlunoFrequenciaMes alunoFreq : matricula.getAlunoFrequenciasMes()) {
                        if (alunoFreq.getBimestre().getId() == idBimestreAtual && alunoFreq.getDisciplinaAluno().equals(disciplinaAluno.getId())) {
                            alunoFrequenciaMes = alunoFreq;
                        }
                    }
                }
            }

            if (disciplinaAluno == null) {
                for (AlunoFrequenciaMes alunoFreq : matricula.getAlunoFrequenciasMes()) {
                    if (alunoFreq.getBimestre().getId() == idBimestreAtual && alunoFreq.getMatricula() == matricula.getId()) {
                        alunoFrequenciaMes = alunoFreq;
                    }
                }
            }
            if (frequencia.isNovo()) {
                if (alunoFrequenciaMes != null) {
                    realm.beginTransaction();

                    if (!frequencia.isPresenca()) {
                        alunoFrequenciaMes.setTotalFaltas(alunoFrequenciaMes.getTotalFaltas() + 1);
                    }

                    alunoFrequenciaMes.setNovo(true);
                    alunoFrequenciaMes.setTipoLancamentoFrequencia("LANCADO_APP");
                    frequencia.setNovo(false);
                    realm.copyToRealmOrUpdate(frequencia);
                    realm.commitTransaction();
                }

                Toast.makeText(context, "Frequência do dia " + new Date().getDate() + " Registrada!", Toast.LENGTH_LONG).show();

            } else if (frequencia.isAlterado()) {
                if (alunoFrequenciaMes != null) {
                    realm.beginTransaction();

                    if (!frequencia.isPresenca()) {
                        alunoFrequenciaMes.setTotalFaltas(alunoFrequenciaMes.getTotalFaltas() + 1);
                    } else {
                        alunoFrequenciaMes.setTotalFaltas(alunoFrequenciaMes.getTotalFaltas() - 1);
                    }

                    if (!alunoFrequenciaMes.isNovo()) {
                        alunoFrequenciaMes.setAlterado(true);
                    }

                    alunoFrequenciaMes.setTipoLancamentoFrequencia("LANCADO_APP");
                    frequencia.setAlterado(false);
                    realm.copyToRealmOrUpdate(frequencia);
                    realm.copyToRealmOrUpdate(alunoFrequenciaMes);
                    realm.commitTransaction();
                }
                Toast.makeText(context, "Frequência do dia " + new Date().getDate() + " Atualizada!", Toast.LENGTH_LONG).show();
            }
        }
    }
}

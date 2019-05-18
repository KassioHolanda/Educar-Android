package com.android.educar.educar.mb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.SerieDisciplina;
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

    public FrequenciaMB(Context context) {
        this.context = context;
        configRealm();
        preferences = new Preferences(context);
        bimestreMB = new BimestreMB(context);
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarFrequencia() {
        Long idBimestreAtual = bimestreMB.getBimestreAtual();

        RealmResults<Frequencia> frequencias = realm.where(Frequencia.class).findAll();

        for (Frequencia frequencia : frequencias) {

            Long idSerieDisciplina = realm.where(SerieDisciplina.class).equalTo("disciplina", preferences.getSavedLong("id_disciplina")).findFirst().getId();
            Long idMatricula = realm.where(Matricula.class).equalTo("id", frequencia.getMatricula()).findFirst().getId();
            Long idDisciplinaAluno = null;

            try {
                idDisciplinaAluno = (realm.where(DisciplinaAluno.class).equalTo("matricula", idMatricula)
                        .equalTo("serieDisciplina", idSerieDisciplina)
                        .equalTo("matricula", frequencia.getMatricula()).findFirst()).getId();
            } catch (Exception e) {
                Log.i("ERRO", "DISCIPLINA ALUNO NULLL");
            }


            alunoFrequenciaMes = null;
            if (idDisciplinaAluno != null) {
                alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class)
                        .equalTo("bimestre", idBimestreAtual)
                        .equalTo("disciplinaAluno", idDisciplinaAluno).findFirst();

            } else {
                alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class)
                        .equalTo("bimestre", idBimestreAtual)
                        .equalTo("matricula", idMatricula).findFirst();
            }

            if (frequencia.isNovo()) {
                if (alunoFrequenciaMes != null) {
                    realm.beginTransaction();

                    if(!frequencia.isPresenca()){
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

package com.android.educar.educar.mb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.utils.Preferences;
import com.google.gson.annotations.SerializedName;

import java.lang.ref.PhantomReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrequenciaMB {

    private Context context;
    private Realm realm;
    private Preferences preferences;
    private RealmObjectsBO realmObjectsBO;
    private NotaMB notaMB;

    public FrequenciaMB(Context context) {
        this.context = context;
        configRealm();
        preferences = new Preferences(context);
        realmObjectsBO = new RealmObjectsBO(context);
        notaMB = new NotaMB(context);
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarFrequencia() {
        Long idBimestreAtual = notaMB.verificarBimestreAtual();
        if (idBimestreAtual == 0) {
            idBimestreAtual = Long.valueOf(5);
        }
        RealmResults<Frequencia> frequencias = realm.where(Frequencia.class).findAll();
        RealmResults<AlunoFrequenciaMes> alunoFrequenciaMes2 = realm.where(AlunoFrequenciaMes.class).findAll();

        for (int i = 0; i < frequencias.size(); i++) {
            if (frequencias.get(i).isNovo()) {
                Long idSerieDisciplina = realm.where(SerieDisciplina.class).equalTo("disciplina", preferences.getSavedLong("id_disciplina")).findFirst().getId();
                Long idMatricula = realm.where(Matricula.class).equalTo("id", frequencias.get(i).getMatricula()).findFirst().getId();

                Long idDisciplinaAluno = Long.valueOf(0);
                try {
                    idDisciplinaAluno = (realm.where(DisciplinaAluno.class).equalTo("matricula", idMatricula)
                            .equalTo("serieDisciplina", idSerieDisciplina)
                            .equalTo("matricula", frequencias.get(i).getMatricula()).findFirst()).getId();
                } catch (Exception e) {
                    Log.i("ERRO", "DISCIPLINA ALUNO NULLL");
                }


                AlunoFrequenciaMes alunoFrequenciaMes = null;
                if (idDisciplinaAluno != 0) {
                    alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class)
                            .equalTo("bimestre", idBimestreAtual)
                            .equalTo("disciplinaAluno", idDisciplinaAluno).findFirst();

                } else {
                    alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class)
                            .equalTo("bimestre", idBimestreAtual)
                            .equalTo("matricula", idMatricula).findFirst();
                }

                if (alunoFrequenciaMes != null) {
                    realm.beginTransaction();
                    alunoFrequenciaMes.setTotalFaltas(alunoFrequenciaMes.getTotalFaltas() + 1);
                    alunoFrequenciaMes.setNovo(true);
                    alunoFrequenciaMes.setTipoLancamentoFrequencia("LANCADO_APP");
                    frequencias.get(i).setNovo(false);
                    realm.commitTransaction();
                }
            }
        }
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        preferences.saveBoolean("datadia-" + formatador.format(data) + "-turma-" + preferences.getSavedLong("id_turma") + "-" + preferences.getSavedLong("id_disciplina"), true);
    }
}

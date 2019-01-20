package com.android.educar.educar.mb;

import android.content.Context;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.Frequencia;
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
        long idBimestreAtual = notaMB.verificarBimestreAtual();
        if (idBimestreAtual == 0) {
            idBimestreAtual = 5;
        }
        RealmResults<Frequencia> frequencias = realm.where(Frequencia.class).findAll();
        RealmResults<AlunoFrequenciaMes> alunoFrequenciaMes2 = realm.where(AlunoFrequenciaMes.class).findAll();

        for (int i = 0; i < frequencias.size(); i++) {
            if (frequencias.get(i).isNovo()) {
                long id = frequencias.get(i).getMatricula();
                long id2 = preferences.getSavedLong("id_bimestre");
                long id3 = preferences.getSavedLong("id_disciplina");

                AlunoFrequenciaMes alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class)
                        .equalTo("bimestre", idBimestreAtual)
                        .equalTo("matricula", frequencias.get(i).getMatricula()).findFirst();

                if (alunoFrequenciaMes != null) {
                    realm.beginTransaction();
                    alunoFrequenciaMes.setTotalFaltas(alunoFrequenciaMes.getTotalFaltas() + 1);
                    alunoFrequenciaMes.setNovo(true);
                    realm.commitTransaction();
//                    realmObjectsBO.salvarObjetoRealm(alunoFrequenciaMes);
                }
            }
        }
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        preferences.saveBoolean("datadia-" + formatador.format(data) + "-turma-" + preferences.getSavedLong("id_turma") + "-" + preferences.getSavedLong("id_disciplina"), true);
    }
}


//    private long id;
//    @SerializedName("totalfaltas")
//    private int totalFaltas;
//    private long matricula;
//    private long bimetre;

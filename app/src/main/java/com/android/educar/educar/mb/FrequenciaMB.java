package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.utils.Preferences;
import com.google.gson.annotations.SerializedName;

import java.lang.ref.PhantomReference;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrequenciaMB {

    private Context context;
    private Realm realm;
    private Preferences preferences;

    public FrequenciaMB(Context context) {
        this.context = context;
        configRealm();
        preferences = new Preferences(context);
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarFrequencia() {

        RealmResults<Frequencia> frequencias = realm.where(Frequencia.class).findAll();

        for (int i = 0; i < frequencias.size(); i++) {

            if (frequencias.get(i).isNovo()) {
                AlunoFrequenciaMes alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class)
                        .equalTo("bimestre", preferences.getSavedLong("id_bimestre"))
                        .equalTo("matricula", frequencias.get(i).getMatricula()).findFirst();

                if (alunoFrequenciaMes != null) {
                    alunoFrequenciaMes.setTotalFaltas(alunoFrequenciaMes.getTotalFaltas() + 1);
                }
            }

//            AlunoFrequenciaMes alunoFrequenciaMes = new AlunoFrequenciaMes();
//            alunoFrequenciaMes.setBimetre(preferences.getSavedLong("id_bimestre"));
//            if (frequencias.get(i).isPresenca()) {
//                alunoFrequenciaMes.setTotalFaltas(1);
//            }
        }
    }
}



//    private long id;
//    @SerializedName("totalfaltas")
//    private int totalFaltas;
//    private long matricula;
//    private long bimetre;

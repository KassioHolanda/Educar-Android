package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.model.modelalterado.Bimestre;
import com.android.educar.educar.model.modelalterado.SituacaoTurmaMes;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class BimestreMB {

    private Realm realm;
    private Context context;
    private Preferences preferences;
    private Long idBimestreAtual;
    private Long ultimoIdBimestre;
    private Turma turma;

    public BimestreMB(Context context) {
        preferences = new Preferences(context);
        this.context = context;
        this.idBimestreAtual = Long.valueOf(0);
        this.ultimoIdBimestre = Long.valueOf(0);
        configRealm();
        turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst());
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public Long getBimestreAtual() {
        RealmList<SituacaoTurmaMes> situacaoTurmaMes = turma.getSituacoesTurmaMes();

        for (SituacaoTurmaMes sit : situacaoTurmaMes) {
            if (sit.getStatus().equals("ABERTO")) {
                this.idBimestreAtual = sit.getBimestre().getId();
            }
            this.ultimoIdBimestre = situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getBimestre().getId();
        }

        if (idBimestreAtual == 0) {
            return criarNovaSituacaoTurmaMes();
        }

//        preferences.saveLong("id_bimestre", idBimestreAtual);
        return idBimestreAtual;
    }

    public Long criarNovaSituacaoTurmaMes() {

        Long bimestre = realm.copyFromRealm(realm.where(Bimestre.class).equalTo("id", 2).findFirst()).getId();

        if (this.ultimoIdBimestre != 0) {
            bimestre = ultimoIdBimestre + 1;
        }

        Bimestre bi = realm.copyFromRealm(realm.where(Bimestre.class).equalTo("id", bimestre).findFirst());
        SituacaoTurmaMes situacaoTurmaMes = new SituacaoTurmaMes(UtilsFunctions.formatoDataPadrao().format(new Date()), "ABERTO",
                0, 0, bi, true, turma.getId());
        turma.getSituacoesTurmaMes().add(situacaoTurmaMes);

        situacaoTurmaMes.setNovo(true);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(situacaoTurmaMes);
        realm.copyToRealmOrUpdate(turma);
        realm.commitTransaction();
        preferences.saveLong("id_bimestre", situacaoTurmaMes.getBimestre().getId());

        return idBimestreAtual;
    }
}

package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.Frequencia;

import java.util.List;

import io.realm.Realm;

public class FrequenciaMB {

    private Context context;
    private Realm realm;
    private RealmObjectsBO realmObjectsBO;

    public FrequenciaMB(Context context) {
        this.context = context;
        this.realmObjectsBO = new RealmObjectsBO(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarFrequencia(List<Frequencia> frequencias) {
        for (int i = 0; i < frequencias.size(); i++) {

        }
    }
}

package com.android.educar.educar.dao;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

public class RealmObjectsDAO {
    private Context context;
    private Realm realm;

    public RealmObjectsDAO(Context context) {
        this.context = context;
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public void salvarListaRealm(List<?> realmModels) {
        realm.beginTransaction();
        for (int i = 0; i < realmModels.size(); i++) {
            realm.copyToRealmOrUpdate((RealmModel) realmModels.get(i));
        }
        realm.commitTransaction();
    }

    public void salvarRealm(final RealmModel o) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(o);
        realm.commitTransaction();
    }

    public Object recuperarObjetoPorId(Long id, Object o) {
        Object objects = realm.where((Class) o).equalTo("id", id).findFirst();
        return objects;
    }
}

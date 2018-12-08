package com.android.educar.educar.bo;

import android.content.Context;

import com.android.educar.educar.dao.RealmObjectsDAO;

import java.util.List;

public class RealmObjectsBO {

    private RealmObjectsDAO realmObjectsDAO;

    public RealmObjectsBO(Context context) {
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void salvarObjetoRealm(Object o) {
        realmObjectsDAO.salvarRealm(o);
    }
}

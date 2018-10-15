package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.service.ListaSerieDisciplinaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerieDisciplinaMB {
    private Context context;
    private APIService apiService;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public SerieDisciplinaMB(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void serieDisciplina() {
        Call<ListaSerieDisciplinaAPI> listaSerieDisciplinaAPICall = apiService.getSerieDisciplinaEndPoint().serieDisciplinas();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieDisciplinaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieDisciplinaAPI> call, Response<ListaSerieDisciplinaAPI> response) {
                if (response.isSuccessful()) {
                    serieDisciplinaBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieDisciplinaAPI> call, Throwable t) {

            }
        });
    }

    public void serieDisciplinaBD(final List<SerieDisciplina> serieDisciplinas) {
        realm.beginTransaction();
        for (int i = 0; i < serieDisciplinas.size(); i++) {
            realm.copyToRealmOrUpdate(serieDisciplinas.get(i));
            Log.i("seriedisciplina", "" + serieDisciplinas.get(i).getId());
        }
        realm.commitTransaction();
    }
}

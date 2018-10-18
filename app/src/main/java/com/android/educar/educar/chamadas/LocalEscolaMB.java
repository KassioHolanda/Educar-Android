package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaLocalEscolaAPI;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalEscolaMB {
    private Context context;
    private APIService apiService;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public LocalEscolaMB(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void localEscolaAPI() {
        Call<ListaLocalEscolaAPI> listaProfessoresAPICall = apiService.getLocalEscolaEndPoint().locaisEscola();
        listaProfessoresAPICall.enqueue(new Callback<ListaLocalEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaLocalEscolaAPI> call, Response<ListaLocalEscolaAPI> response) {
                if (response.isSuccessful()) {
                    salvarLocalEscola(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaLocalEscolaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarLocalEscola(final List<LocalEscola> localEscolas) {
        realm.beginTransaction();
        for (int i = 0; i < localEscolas.size(); i++) {
            realm.copyToRealmOrUpdate(localEscolas.get(i));
            Log.i("localescola", "" + localEscolas.get(i).getDescricao());
        }
        realm.commitTransaction();
    }
}

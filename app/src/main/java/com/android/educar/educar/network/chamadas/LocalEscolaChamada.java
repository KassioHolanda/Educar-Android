package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaLocalEscolaAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalEscolaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public LocalEscolaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void localEscolaAPI() {
        Call<ListaLocalEscolaAPI> listaProfessoresAPICall = apiService.getLocalEscolaEndPoint().locaisEscola();
        listaProfessoresAPICall.enqueue(new Callback<ListaLocalEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaLocalEscolaAPI> call, Response<ListaLocalEscolaAPI> response) {
                if (response.isSuccessful()) {
                    salvarLocalEscolaRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaLocalEscolaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarLocalEscolaRealm(List<LocalEscola> localEscolas) {
        realm.beginTransaction();
        for (int i = 0; i < localEscolas.size(); i++) {
            realm.copyToRealmOrUpdate(localEscolas.get(i));
        }
        realm.commitTransaction();
    }
}

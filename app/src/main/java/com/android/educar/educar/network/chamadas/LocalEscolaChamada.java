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
    private int paginaAtualLocaisEscola;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public LocalEscolaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualLocaisEscola = 1;
    }

    public void localEscolaAPI() {
        Call<ListaLocalEscolaAPI> listaProfessoresAPICall = apiService.getLocalEscolaEndPoint().locaisEscola(paginaAtualLocaisEscola);
        listaProfessoresAPICall.enqueue(new Callback<ListaLocalEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaLocalEscolaAPI> call, Response<ListaLocalEscolaAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualLocaisEscola = paginaAtualLocaisEscola + 1;
                        localEscolaAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaLocalEscolaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

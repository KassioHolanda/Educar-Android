package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private int paginaAtualUnidade;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public UnidadeChamada(Context context) {
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualUnidade = 1;
    }

    public void unidadesAPI() {
        Call<ListaUnidadesAPI> listaUnidadesAPICall = apiService.getUnidadeEndPoint().unidades(paginaAtualUnidade);
        listaUnidadesAPICall.enqueue(new Callback<ListaUnidadesAPI>() {
            @Override
            public void onResponse(Call<ListaUnidadesAPI> call, Response<ListaUnidadesAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualUnidade = paginaAtualUnidade + 1;
                        unidadesAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaUnidadesAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

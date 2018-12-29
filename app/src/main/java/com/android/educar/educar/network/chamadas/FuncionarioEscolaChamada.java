package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaFuncionarioEscolaAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioEscolaChamada {

    private APIService apiService;
    private Context context;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private int paginaAtualFuncionarioEscola;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public FuncionarioEscolaChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualFuncionarioEscola = 1;
    }

    public void funcionariosEscola() {
        final Call<ListaFuncionarioEscolaAPI> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola(paginaAtualFuncionarioEscola);
        listaFuncionarioEscolaAPICall.enqueue(new Callback<ListaFuncionarioEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionarioEscolaAPI> call, Response<ListaFuncionarioEscolaAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() !=null) {
                        paginaAtualFuncionarioEscola = paginaAtualFuncionarioEscola+1;
                        funcionariosEscola();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaFuncionarioEscolaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

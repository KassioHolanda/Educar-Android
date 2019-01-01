package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioChamada {

    private APIService apiService;
    private Context context;
    private Realm realm;
    private int paginaAtual;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public FuncionarioChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        configRealm();
        paginaAtual = 1;
    }

    public void recuperarFuncionariosAPI() {
        final Call<ListaFuncionariosAPI> listaProfessoresAPICall = apiService.getFuncionarioEndPoint().funcionarios(paginaAtual);
        listaProfessoresAPICall.enqueue(new Callback<ListaFuncionariosAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionariosAPI> call, Response<ListaFuncionariosAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();

                    if (response.body().getNext() != null) {
                        paginaAtual = paginaAtual + 1;
                        recuperarFuncionariosAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaFuncionariosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

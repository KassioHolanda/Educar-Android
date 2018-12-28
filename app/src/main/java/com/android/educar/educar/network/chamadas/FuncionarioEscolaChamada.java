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

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public FuncionarioEscolaChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void funcionariosEscola() {
        final Call<ListaFuncionarioEscolaAPI> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola();
        listaFuncionarioEscolaAPICall.enqueue(new Callback<ListaFuncionarioEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionarioEscolaAPI> call, Response<ListaFuncionarioEscolaAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarFuncionarioEscolaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaFuncionarioEscolaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarFuncionarioEscolaRealm(List<FuncionarioEscola> funcionarioEscolas) {
        realm.beginTransaction();
        for (int i = 0; i < funcionarioEscolas.size(); i++) {
            realm.copyToRealmOrUpdate(funcionarioEscolas.get(i));
        }
        realm.commitTransaction();
    }
}

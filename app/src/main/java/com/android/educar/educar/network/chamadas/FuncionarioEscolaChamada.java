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

    public void funcionariosEscola(final long funcionarioId) {
        final Call<List<FuncionarioEscola>> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola(funcionarioId);
        listaFuncionarioEscolaAPICall.enqueue(new Callback<List<FuncionarioEscola>>() {
            @Override
            public void onResponse(Call<List<FuncionarioEscola>> call, Response<List<FuncionarioEscola>> response) {
                if (response.isSuccessful()) {
                    Realm.getDefaultInstance().beginTransaction();
                    Realm.getDefaultInstance().copyToRealmOrUpdate(response.body());
                    Realm.getDefaultInstance().commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<List<FuncionarioEscola>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

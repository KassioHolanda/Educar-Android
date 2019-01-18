package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
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

    public void recuperarUndidadesProfessor() {
        RealmResults<FuncionarioEscola> funcionarioEscolas = Realm.getDefaultInstance().where(FuncionarioEscola.class).findAll();

        for (int i = 0; i < funcionarioEscolas.size(); i++) {
            unidadesAPI(funcionarioEscolas.get(i).getUnidade());
        }
    }

    public void unidadesAPI(long unidadeId) {
        Call<Unidade> listaUnidadesAPICall = apiService.getUnidadeEndPoint().getUnidade(unidadeId);
        listaUnidadesAPICall.enqueue(new Callback<Unidade>() {
            @Override
            public void onResponse(Call<Unidade> call, Response<Unidade> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarRealm(response.body());
                }
            }

            @Override
            public void onFailure(Call<Unidade> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

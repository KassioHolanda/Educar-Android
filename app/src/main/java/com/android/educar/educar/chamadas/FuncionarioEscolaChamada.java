package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaFuncionarioEscolaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioEscolaChamada {

    private APIService apiService;
    private Context context;
    private RealmObjectsDAO realmObjectsDAO;

    public FuncionarioEscolaChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void funcionariosEscola() {
        final Call<ListaFuncionarioEscolaAPI> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola();
        listaFuncionarioEscolaAPICall.enqueue(new Callback<ListaFuncionarioEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionarioEscolaAPI> call, Response<ListaFuncionarioEscolaAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaFuncionarioEscolaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAnoLetivoAPI;
import com.android.educar.educar.service.ListaBimestreAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnoLetivoChamada {

    private RealmObjectsDAO realmObjectsDAO;
    private Context context;
    private APIService apiService;

    public AnoLetivoChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void anoLetivoAPI() {
        Call<ListaAnoLetivoAPI> listaAnoLetivoAPICall = apiService.getAnoLetivoEndPoint().anosLetivos();
        listaAnoLetivoAPICall.enqueue(new Callback<ListaAnoLetivoAPI>() {
            @Override
            public void onResponse(Call<ListaAnoLetivoAPI> call, Response<ListaAnoLetivoAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAnoLetivoAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recuperarBimestreAPI() {
        Call<ListaBimestreAPI> listaAnoLetivoAPICall = apiService.getBimestreEndPoint().bimestres();
        listaAnoLetivoAPICall.enqueue(new Callback<ListaBimestreAPI>() {
            @Override
            public void onResponse(Call<ListaBimestreAPI> call, Response<ListaBimestreAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaBimestreAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
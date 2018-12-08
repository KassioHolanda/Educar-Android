package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaSerieAPI;
import com.android.educar.educar.service.ListaSerieDisciplinaAPI;
import com.android.educar.educar.service.ListaSerieTurmaAPI;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerieChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;

    public SerieChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
    }


    public void configRealm() {
        Realm.init(context);
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void serieDisciplina() {
        Call<ListaSerieDisciplinaAPI> listaSerieDisciplinaAPICall = apiService.getSerieDisciplinaEndPoint().serieDisciplinas();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieDisciplinaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieDisciplinaAPI> call, Response<ListaSerieDisciplinaAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieDisciplinaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recuperarSerieTurmaAPI() {
        Call<ListaSerieTurmaAPI> listaSerieDisciplinaAPICall = apiService.getSerieTurmaEndPoint().seriesturma();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieTurmaAPI> call, Response<ListaSerieTurmaAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieTurmaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recuperarSerieAPI() {
        Call<ListaSerieAPI> listaSerieDisciplinaAPICall = apiService.getSerieEndPoint().series();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieAPI>() {
            @Override
            public void onResponse(Call<ListaSerieAPI> call, Response<ListaSerieAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaLocalEscolaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalEscolaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;

    public LocalEscolaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void localEscolaAPI() {
        Call<ListaLocalEscolaAPI> listaProfessoresAPICall = apiService.getLocalEscolaEndPoint().locaisEscola();
        listaProfessoresAPICall.enqueue(new Callback<ListaLocalEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaLocalEscolaAPI> call, Response<ListaLocalEscolaAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaLocalEscolaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

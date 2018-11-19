package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaLocalEscolaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalEscolaChamada {
    private Context context;
    private APIService apiService;
    private RealmBO realmBO;

    public LocalEscolaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmBO = new RealmBO(context);
    }

    public void localEscolaAPI() {
        Call<ListaLocalEscolaAPI> listaProfessoresAPICall = apiService.getLocalEscolaEndPoint().locaisEscola();
        listaProfessoresAPICall.enqueue(new Callback<ListaLocalEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaLocalEscolaAPI> call, Response<ListaLocalEscolaAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarLocalEscolaRealm(response.body().getResults());
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

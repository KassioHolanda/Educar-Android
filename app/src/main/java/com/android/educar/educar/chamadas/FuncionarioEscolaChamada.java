package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaFuncionarioEscolaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioEscolaChamada {

    private APIService apiService;
    private Context context;
    private RealmBO realmBO;

    public FuncionarioEscolaChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        realmBO = new RealmBO(context);
    }

    public void funcionariosEscola() {
        final Call<ListaFuncionarioEscolaAPI> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola();
        listaFuncionarioEscolaAPICall.enqueue(new Callback<ListaFuncionarioEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionarioEscolaAPI> call, Response<ListaFuncionarioEscolaAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarFuncionarioEscolaRealm(response.body().getResults());
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

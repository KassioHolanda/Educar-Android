package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaFuncionariosAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioChamada {

    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Context context;

    public FuncionarioChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void funcionariosAPI() {
        final Call<ListaFuncionariosAPI> listaProfessoresAPICall = apiService.getFuncionarioEndPoint().funcionarios();
        listaProfessoresAPICall.enqueue(new Callback<ListaFuncionariosAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionariosAPI> call, Response<ListaFuncionariosAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());

                }
            }

            @Override
            public void onFailure(Call<ListaFuncionariosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

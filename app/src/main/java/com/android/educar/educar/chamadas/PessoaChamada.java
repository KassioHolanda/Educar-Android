package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaPerfilAPI;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.service.ListaUsuariosAPI;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaChamada {

    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;

    public PessoaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void pessoaFisicaAPI() {
        Call<ListaPessoaFisicaAPI> listaProfessoresAPICall = apiService.getPessoaFisicaEndPoint().pessoasFisicas(1);
        listaProfessoresAPICall.enqueue(new Callback<ListaPessoaFisicaAPI>() {
            @Override
            public void onResponse(Call<ListaPessoaFisicaAPI> call, Response<ListaPessoaFisicaAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaPessoaFisicaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recuperarUsuariosAPI() {
        Call<ListaUsuariosAPI> listaProfessoresAPICall = apiService.getUsuarioEndPoint().usuarios();
        listaProfessoresAPICall.enqueue(new Callback<ListaUsuariosAPI>() {
            @Override
            public void onResponse(Call<ListaUsuariosAPI> call, Response<ListaUsuariosAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaUsuariosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recuperarPerfilAPI() {
        Call<ListaPerfilAPI> listaProfessoresAPICall = apiService.getPerfilEndPoint().perfis();
        listaProfessoresAPICall.enqueue(new Callback<ListaPerfilAPI>() {
            @Override
            public void onResponse(Call<ListaPerfilAPI> call, Response<ListaPerfilAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaPerfilAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
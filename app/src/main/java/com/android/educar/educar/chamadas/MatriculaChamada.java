package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaMatriculaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatriculaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;

    public MatriculaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void matriculaAPI() {
        Call<ListaMatriculaAPI> listaProfessoresAPICall = apiService.getMatriculaEndPoint().matriculas();
        listaProfessoresAPICall.enqueue(new Callback<ListaMatriculaAPI>() {
            @Override
            public void onResponse(Call<ListaMatriculaAPI> call, Response<ListaMatriculaAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaMatriculaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

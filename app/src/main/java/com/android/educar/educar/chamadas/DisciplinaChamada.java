package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaDisciplinasAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisciplinaChamada {
    private Context context;
    private APIService apiService;
    private RealmBO realmBO;

    public DisciplinaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmBO = new RealmBO(context);
    }

    public void disciplinasAPI() {
        Call<ListaDisciplinasAPI> listaProfessoresAPICall = apiService.getDisciplinaEndPoint().disciplinas();
        listaProfessoresAPICall.enqueue(new Callback<ListaDisciplinasAPI>() {
            @Override
            public void onResponse(Call<ListaDisciplinasAPI> call, Response<ListaDisciplinasAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarDisciplinasRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaDisciplinasAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

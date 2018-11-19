package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunoNotaMesAPI;
import com.android.educar.educar.service.ListaAlunosAPI;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoChamada {
    private Realm realm;
    private Context context;
    private APIService apiService;
    private RealmBO realmBO;

    public AlunoChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmBO = new RealmBO(context);

    }

    public void alunosAPI() {
        Call<ListaAlunosAPI> listaAlunosAPICall = apiService.getAlunoEndPoint().alunos();
        listaAlunosAPICall.enqueue(new Callback<ListaAlunosAPI>() {
            @Override
            public void onResponse(Call<ListaAlunosAPI> call, Response<ListaAlunosAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarAlunosRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAlunosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void recuperarAlunoNotaMesAPI() {
        Call<ListaAlunoNotaMesAPI> listaAlunosAPICall = apiService.getAlunoNotaMesEndPoint().alunosNotaMes();
        listaAlunosAPICall.enqueue(new Callback<ListaAlunoNotaMesAPI>() {
            @Override
            public void onResponse(Call<ListaAlunoNotaMesAPI> call, Response<ListaAlunoNotaMesAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarAlunosNotaMesRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAlunoNotaMesAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

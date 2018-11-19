package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaGradeCursoAPI;
import com.android.educar.educar.service.ListaTurmaAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurmaChamada {
    private Context context;
    private APIService apiService;
    private RealmBO realmBO;

    public TurmaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmBO = new RealmBO(context);

    }

    public void gradeCursoAPI() {
        Call<ListaGradeCursoAPI> listaGradeCursoAPICall = apiService.getGradeCursoEndPoint().gradeCursos();
        listaGradeCursoAPICall.enqueue(new Callback<ListaGradeCursoAPI>() {
            @Override
            public void onResponse(Call<ListaGradeCursoAPI> call, Response<ListaGradeCursoAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarGradeCursoRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaGradeCursoAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void turmasAPI() {
        Call<ListaTurmaAPI> listaUnidadesAPICall = apiService.getTurmaEndPoint().turmas();
        listaUnidadesAPICall.enqueue(new Callback<ListaTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaTurmaAPI> call, Response<ListaTurmaAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarTurmaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaTurmaAPI> call, Throwable t) {

            }
        });
    }


}

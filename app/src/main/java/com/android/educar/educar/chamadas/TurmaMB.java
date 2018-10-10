package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

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

public class TurmaMB {
    private Realm realm;
    private Context context;
    private APIService apiService;

    public TurmaMB(Context context) {
        apiService = new APIService("");
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void gradeCursoAPI() {
        Call<ListaGradeCursoAPI> listaGradeCursoAPICall = apiService.getGradeCursoEndPoint().gradeCursos();
        listaGradeCursoAPICall.enqueue(new Callback<ListaGradeCursoAPI>() {
            @Override
            public void onResponse(Call<ListaGradeCursoAPI> call, Response<ListaGradeCursoAPI> response) {
                if (response.isSuccessful()) {
                    salvarGradeCurso(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaGradeCursoAPI> call, Throwable t) {

            }
        });
    }

    public void turmasAPI() {
        Call<ListaTurmaAPI> listaUnidadesAPICall = apiService.getTurmaEndPoint().turmas();
        listaUnidadesAPICall.enqueue(new Callback<ListaTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaTurmaAPI> call, Response<ListaTurmaAPI> response) {
                if (response.isSuccessful()) {
                    salvarTurma(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaTurmaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarGradeCurso(List<GradeCurso> gradeCursos) {
        realm.beginTransaction();
        for (int i = 0; i < gradeCursos.size(); i++) {
            realm.copyToRealmOrUpdate(gradeCursos.get(i));
            Log.i("gradecurso", "" + gradeCursos.get(i).getId());
        }
        realm.commitTransaction();
    }

    public void salvarTurma(List<Turma> turmas) {
        realm.beginTransaction();
        for (int i = 0; i < turmas.size(); i++) {
            realm.copyToRealmOrUpdate(turmas.get(i));
            Log.i("turma", "" + turmas.get(i).getDescricao());
        }
        realm.commitTransaction();
    }
}

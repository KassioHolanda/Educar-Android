package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaMatriculaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatriculaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private int paginaAtualMatricula;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public MatriculaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualMatricula = 1;
    }

    public void matriculaAPI() {
        Call<ListaMatriculaAPI> listaProfessoresAPICall = apiService.getMatriculaEndPoint().matriculas(paginaAtualMatricula);
        listaProfessoresAPICall.enqueue(new Callback<ListaMatriculaAPI>() {
            @Override
            public void onResponse(Call<ListaMatriculaAPI> call, Response<ListaMatriculaAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualMatricula = paginaAtualMatricula + 1;
                        matriculaAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaMatriculaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarMatriculasTurmas() {
        RealmResults<Turma> turmas = realm.where(Turma.class).findAll();
        for (int i = 0; i < turmas.size(); i++) {
            recuperarMatriculasTurmas(turmas.get(i).getId());
        }
    }

    public void recuperarMatriculasTurmas(long turmaId) {
        Call<List<Matricula>> listCall = apiService.getMatriculaEndPoint().getmatriculas(turmaId);
        listCall.enqueue(new Callback<List<Matricula>>() {
            @Override
            public void onResponse(Call<List<Matricula>> call, Response<List<Matricula>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<List<Matricula>> call, Throwable t) {

            }
        });
    }
}

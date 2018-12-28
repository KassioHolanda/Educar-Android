package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaMatriculaAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatriculaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public MatriculaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void matriculaAPI() {
        Call<ListaMatriculaAPI> listaProfessoresAPICall = apiService.getMatriculaEndPoint().matriculas();
        listaProfessoresAPICall.enqueue(new Callback<ListaMatriculaAPI>() {
            @Override
            public void onResponse(Call<ListaMatriculaAPI> call, Response<ListaMatriculaAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarMatriculaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaMatriculaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarMatriculaRealm(List<Matricula> matriculas) {
        realm.beginTransaction();
        for (int i = 0; i < matriculas.size(); i++) {
            realm.copyToRealmOrUpdate(matriculas.get(i));
        }
        realm.commitTransaction();
    }
}

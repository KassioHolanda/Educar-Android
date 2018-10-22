package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaLocalEscolaAPI;
import com.android.educar.educar.service.ListaMatriculaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatriculaMB {
    private Context context;
    private APIService apiService;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public MatriculaMB(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void matriculaAPI() {
        Call<ListaMatriculaAPI> listaProfessoresAPICall = apiService.getMatriculaEndPoint().matriculas();
        listaProfessoresAPICall.enqueue(new Callback<ListaMatriculaAPI>() {
            @Override
            public void onResponse(Call<ListaMatriculaAPI> call, Response<ListaMatriculaAPI> response) {
                if (response.isSuccessful()) {
                    salvarMatricula(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaMatriculaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarMatricula(final List<Matricula> matriculas) {
        realm.beginTransaction();
        for (int i = 0; i < matriculas.size(); i++) {
            realm.copyToRealmOrUpdate(matriculas.get(i));
            Log.i("matricula", "" + matriculas.get(i).getStatusMatricula());
        }
        realm.commitTransaction();
    }
}

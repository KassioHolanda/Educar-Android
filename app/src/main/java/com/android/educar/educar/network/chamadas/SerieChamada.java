package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaSerieAPI;
import com.android.educar.educar.network.service.ListaSerieDisciplinaAPI;
import com.android.educar.educar.network.service.ListaSerieTurmaAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerieChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;


    public SerieChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void serieDisciplina() {
        Call<ListaSerieDisciplinaAPI> listaSerieDisciplinaAPICall = apiService.getSerieDisciplinaEndPoint().serieDisciplinas();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieDisciplinaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieDisciplinaAPI> call, Response<ListaSerieDisciplinaAPI> response) {
                if (response.isSuccessful()) {
                    salvarSerieDisciplinaRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieDisciplinaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarSerieDisciplinaRealm(List<SerieDisciplina> SerieDisciplina) {
        realm.beginTransaction();
        for (int i = 0; i < SerieDisciplina.size(); i++) {
            realm.copyToRealmOrUpdate(SerieDisciplina.get(i));
        }
        realm.commitTransaction();
    }

    public void recuperarSerieTurmaAPI() {
        Call<ListaSerieTurmaAPI> listaSerieDisciplinaAPICall = apiService.getSerieTurmaEndPoint().seriesturma();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieTurmaAPI> call, Response<ListaSerieTurmaAPI> response) {
                if (response.isSuccessful()) {
                    salvarSerieTurmaRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieTurmaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarSerieTurmaRealm(List<SerieTurma> serieTurmas) {
        realm.beginTransaction();
        for (int i = 0; i < serieTurmas.size(); i++) {
            realm.copyToRealmOrUpdate(serieTurmas.get(i));
        }
        realm.commitTransaction();
    }

    public void recuperarSerieAPI() {
        Call<ListaSerieAPI> listaSerieDisciplinaAPICall = apiService.getSerieEndPoint().series();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieAPI>() {
            @Override
            public void onResponse(Call<ListaSerieAPI> call, Response<ListaSerieAPI> response) {
                if (response.isSuccessful()) {
                    salvarSerieRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarSerieRealm(List<Serie> serie) {
        realm.beginTransaction();
        for (int i = 0; i < serie.size(); i++) {
            realm.copyToRealmOrUpdate(serie.get(i));
        }
        realm.commitTransaction();
    }


}

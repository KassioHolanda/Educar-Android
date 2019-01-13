package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaSerieAPI;
import com.android.educar.educar.network.service.ListaSerieDisciplinaAPI;
import com.android.educar.educar.network.service.ListaSerieTurmaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerieChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private int paginaAtualSerieDisciplina;
    private int paginaAtualSerieTurma;
    private int paginaAtualSerie;


    public SerieChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualSerie = 1;
        paginaAtualSerieDisciplina = 1;
        paginaAtualSerieTurma = 1;
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void serieDisciplina() {
        Call<ListaSerieDisciplinaAPI> listaSerieDisciplinaAPICall = apiService.getSerieDisciplinaEndPoint().serieDisciplinas(paginaAtualSerieDisciplina);

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieDisciplinaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieDisciplinaAPI> call, Response<ListaSerieDisciplinaAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualSerieDisciplina = paginaAtualSerieDisciplina + 1;
                        serieDisciplina();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaSerieDisciplinaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarSerieTurmaAPI() {
        Call<ListaSerieTurmaAPI> listaSerieDisciplinaAPICall = apiService.getSerieTurmaEndPoint().seriesturma(paginaAtualSerieTurma);

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieTurmaAPI> call, Response<ListaSerieTurmaAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualSerieTurma = paginaAtualSerieTurma + 1;
                        recuperarSerieTurmaAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaSerieTurmaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarSerieAPI() {
        Call<ListaSerieAPI> listaSerieDisciplinaAPICall = apiService.getSerieEndPoint().series(paginaAtualSerie);

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieAPI>() {
            @Override
            public void onResponse(Call<ListaSerieAPI> call, Response<ListaSerieAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualSerie = paginaAtualSerie + 1;
                        recuperarSerieAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaSerieAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarSerieDisciplina() {
        RealmResults<GradeCurso> gradeCursos = realm.where(GradeCurso.class).findAll();

        for (int i = 0; i < gradeCursos.size(); i++) {
            recuperarSerieDisciplina(gradeCursos.get(i).getSeriedisciplina());
        }
    }

    public void recuperarSerieDisciplina(long serieDisciplina) {
        Call<SerieDisciplina> serieDisciplinaCall = apiService.getSerieDisciplinaEndPoint().getSerieDisciplinas(serieDisciplina);
        serieDisciplinaCall.enqueue(new Callback<SerieDisciplina>() {
            @Override
            public void onResponse(Call<SerieDisciplina> call, Response<SerieDisciplina> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<SerieDisciplina> call, Throwable t) {

            }
        });
    }
}

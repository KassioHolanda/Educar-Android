package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.model.Turma;
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
    private Realm realm;
    private int paginaAtualSerieDisciplina;
    private int paginaAtualSerieTurma;
    private int paginaAtualSerie;
    private DisciplinaChamada disciplinaChamada;


    public SerieChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
        paginaAtualSerie = 1;
        paginaAtualSerieDisciplina = 1;
        paginaAtualSerieTurma = 1;
        disciplinaChamada = new DisciplinaChamada(context);
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
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


    public void recuperarSerieAPI(Long serieID) {
        Call<Serie> listaSerieDisciplinaAPICall = apiService.getSerieEndPoint().getSerie(serieID);
        listaSerieDisciplinaAPICall.enqueue(new Callback<Serie>() {
            @Override
            public void onResponse(Call<Serie> call, Response<Serie> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    Log.i("RESPONSE", "SERIES RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<Serie> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarSerieDisciplinaPelaDisciplina(final Long disciplinaId) {
        Call<List<SerieDisciplina>> listCall = apiService.getSerieDisciplinaEndPoint().serieDisciplinas(disciplinaId);
        listCall.enqueue(new Callback<List<SerieDisciplina>>() {
            @Override
            public void onResponse(Call<List<SerieDisciplina>> call, Response<List<SerieDisciplina>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
//                    disciplinaChamada.recuperarDisciplinasTurma(disciplinaId);
                    Log.i("RESPONSE", "SERIEDISCIPLINA RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<List<SerieDisciplina>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarSerieDisciplina(final Long serieDisciplina) {
        Call<SerieDisciplina> serieDisciplinaCall = apiService.getSerieDisciplinaEndPoint().getSerieDisciplinas(serieDisciplina);
        serieDisciplinaCall.enqueue(new Callback<SerieDisciplina>() {
            @Override
            public void onResponse(Call<SerieDisciplina> call, Response<SerieDisciplina> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    try {
                        Thread.sleep(1000);
//                        recuperarSerieAPI(response.body().getSerie());
                        Thread.sleep(1000);
//                        disciplinaChamada.recuperarDisciplinasTurma(response.body().getDisciplina());
                    } catch (InterruptedException ex) {
                    }
                    Log.i("RESPONSE", "SERIEDISCIPLINA RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<SerieDisciplina> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

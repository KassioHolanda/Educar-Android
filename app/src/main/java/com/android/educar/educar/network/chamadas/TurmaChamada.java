package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.SituacaoTurmaMes;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaGradeCursoAPI;
import com.android.educar.educar.network.service.ListaSituacaoTurmaMesAPI;
import com.android.educar.educar.network.service.ListaTurmaAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurmaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private int paginaAtualGradeCurso;
    private int paginaAtualTurma;
    private int paginaAtualSituacaoTurmames;

    public TurmaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualTurma = 1;
        paginaAtualGradeCurso = 1;
        paginaAtualSituacaoTurmames = 1;
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }


    public void gradeCursoAPI() {
        Call<ListaGradeCursoAPI> listaGradeCursoAPICall = apiService.getGradeCursoEndPoint().gradeCursos(paginaAtualGradeCurso);
        listaGradeCursoAPICall.enqueue(new Callback<ListaGradeCursoAPI>() {
            @Override
            public void onResponse(Call<ListaGradeCursoAPI> call, Response<ListaGradeCursoAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarGradeCursoRealm(response.body().getResults());
                    if (response.body().getNext() != null) {
                        paginaAtualGradeCurso = paginaAtualGradeCurso + 1;
                        gradeCursoAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaGradeCursoAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarGradeCursoRealm(List<GradeCurso> gradeCursos) {
        realm.beginTransaction();
        for (int i = 0; i < gradeCursos.size(); i++) {
            realm.copyToRealmOrUpdate(gradeCursos.get(i));
        }
        realm.commitTransaction();
    }

    public void turmasAPI() {
        Call<ListaTurmaAPI> listaUnidadesAPICall = apiService.getTurmaEndPoint().turmas(paginaAtualTurma);
        listaUnidadesAPICall.enqueue(new Callback<ListaTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaTurmaAPI> call, Response<ListaTurmaAPI> response) {
                if (response.isSuccessful()) {

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();

                    if (response.body().getNext() != null) {
                        paginaAtualTurma = paginaAtualTurma + 1;
                        turmasAPI();
                    }

                }
            }

            @Override
            public void onFailure(Call<ListaTurmaAPI> call, Throwable t) {

            }
        });
    }


    public void recuperarSituacaoTurmaMesAPI() {
        Call<ListaSituacaoTurmaMesAPI> listaUnidadesAPICall = apiService.getSituacaoTurmaMesEndPoint().situacoes(paginaAtualSituacaoTurmames);
        listaUnidadesAPICall.enqueue(new Callback<ListaSituacaoTurmaMesAPI>() {
            @Override
            public void onResponse(Call<ListaSituacaoTurmaMesAPI> call, Response<ListaSituacaoTurmaMesAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualSituacaoTurmames = paginaAtualSituacaoTurmames + 1;
                        recuperarSituacaoTurmaMesAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaSituacaoTurmaMesAPI> call, Throwable t) {

            }
        });
    }


    public void postSituacaoTurmaMesAPI(SituacaoTurmaMes situacaoTurmaMes) {
        Call<SituacaoTurmaMes> situacaoTurmaMesCall = apiService.getSituacaoTurmaMesEndPoint().postSituacaoTurmaMes(situacaoTurmaMes);
        situacaoTurmaMesCall.enqueue(new Callback<SituacaoTurmaMes>() {
            @Override
            public void onResponse(Call<SituacaoTurmaMes> call, Response<SituacaoTurmaMes> response) {

            }

            @Override
            public void onFailure(Call<SituacaoTurmaMes> call, Throwable t) {

            }
        });
    }


}

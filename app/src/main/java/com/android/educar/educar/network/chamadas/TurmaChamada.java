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

    public TurmaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
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
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarGradeCursoRealm(response.body().getResults());
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
        Call<ListaTurmaAPI> listaUnidadesAPICall = apiService.getTurmaEndPoint().turmas();
        listaUnidadesAPICall.enqueue(new Callback<ListaTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaTurmaAPI> call, Response<ListaTurmaAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarTurmaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaTurmaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarTurmaRealm(List<Turma> turmas) {
        realm.beginTransaction();
        for (int i = 0; i < turmas.size(); i++) {
            realm.copyToRealmOrUpdate(turmas.get(i));
        }
        realm.commitTransaction();
    }

    public void recuperarSituacaoTurmaMesAPI() {
        Call<ListaSituacaoTurmaMesAPI> listaUnidadesAPICall = apiService.getSituacaoTurmaMesEndPoint().situacoes();
        listaUnidadesAPICall.enqueue(new Callback<ListaSituacaoTurmaMesAPI>() {
            @Override
            public void onResponse(Call<ListaSituacaoTurmaMesAPI> call, Response<ListaSituacaoTurmaMesAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarSituacaoTurmaMesRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSituacaoTurmaMesAPI> call, Throwable t) {

            }
        });
    }

    public void salvarSituacaoTurmaMesRealm(List<SituacaoTurmaMes> situacaoTurmaMes) {
        realm.beginTransaction();
        for (int i = 0; i < situacaoTurmaMes.size(); i++) {
            realm.copyToRealmOrUpdate(situacaoTurmaMes.get(i));
        }
        realm.commitTransaction();
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

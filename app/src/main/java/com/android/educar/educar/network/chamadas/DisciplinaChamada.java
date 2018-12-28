package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaDisciplinasAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisciplinaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public DisciplinaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void disciplinasAPI() {
        Call<ListaDisciplinasAPI> listaProfessoresAPICall = apiService.getDisciplinaEndPoint().disciplinas();
        listaProfessoresAPICall.enqueue(new Callback<ListaDisciplinasAPI>() {
            @Override
            public void onResponse(Call<ListaDisciplinasAPI> call, Response<ListaDisciplinasAPI> response) {
                if (response.isSuccessful()) {
                    salvarDisciplinaRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaDisciplinasAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarDisciplinaRealm(List<Disciplina> disciplinas) {
        realm.beginTransaction();
        for (int i = 0; i < disciplinas.size(); i++) {
            realm.copyToRealmOrUpdate(disciplinas.get(i));
        }
        realm.commitTransaction();
    }
}

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
    private int paginaAtualDisciplina;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public DisciplinaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualDisciplina = 1;
    }

    public void disciplinasAPI() {
        Call<ListaDisciplinasAPI> listaProfessoresAPICall = apiService.getDisciplinaEndPoint().disciplinas();
        listaProfessoresAPICall.enqueue(new Callback<ListaDisciplinasAPI>() {
            @Override
            public void onResponse(Call<ListaDisciplinasAPI> call, Response<ListaDisciplinasAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualDisciplina = paginaAtualDisciplina + 1;
                        disciplinasAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaDisciplinasAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaDisciplinasAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
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

    public void recuperarDisciplinasTurma(Long disciplina) {
        Call<Disciplina> listCall = apiService.getDisciplinaEndPoint().getDisciplina(disciplina);
        listCall.enqueue(new Callback<Disciplina>() {
            @Override
            public void onResponse(Call<Disciplina> call, Response<Disciplina> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    Log.i("RESPONSE", "DISCIPLINAS RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<Disciplina> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }
}

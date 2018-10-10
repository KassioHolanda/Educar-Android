package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaDisciplinasAPI;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisciplinaMB {
    private Realm realm;
    private Context context;
    private APIService apiService;

    public DisciplinaMB(Context context) {
        apiService = new APIService("");
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void disciplinasAPI() {
        Call<ListaDisciplinasAPI> listaProfessoresAPICall = apiService.getDisciplinaEndPoint().disciplinas();
        listaProfessoresAPICall.enqueue(new Callback<ListaDisciplinasAPI>() {
            @Override
            public void onResponse(Call<ListaDisciplinasAPI> call, Response<ListaDisciplinasAPI> response) {
                if (response.isSuccessful()) {
                    salvarDisiciplina(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaDisciplinasAPI> call, Throwable t) {

            }
        });
    }

    public void salvarDisiciplina(final List<Disciplina> disciplinas) {
        realm.beginTransaction();
        for (int i = 0; i < disciplinas.size(); i++) {
            realm.copyToRealmOrUpdate(disciplinas.get(i));
            Log.i("disciplinas", "" + disciplinas.get(i).getDesceicao());
        }
        realm.commitTransaction();
    }
}

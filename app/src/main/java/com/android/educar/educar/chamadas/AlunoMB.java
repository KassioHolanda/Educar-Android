package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaDisciplinasAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoMB {
    private Realm realm;
    private Context context;
    private APIService apiService;

    public AlunoMB(Context context) {
        apiService = new APIService("");
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void alunosAPI() {
        Call<ListaAlunosAPI> listaAlunosAPICall = apiService.getAlunoEndPoint().alunos();
        listaAlunosAPICall.enqueue(new Callback<ListaAlunosAPI>() {
            @Override
            public void onResponse(Call<ListaAlunosAPI> call, Response<ListaAlunosAPI> response) {
                if (response.isSuccessful()) {
                    salvarDisiciplina(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAlunosAPI> call, Throwable t) {

            }
        });
    }

    public void salvarDisiciplina(final List<Aluno> alunos) {
        realm.beginTransaction();
        for (int i = 0; i < alunos.size(); i++) {
            realm.copyToRealmOrUpdate(alunos.get(i));
            Log.i("disciplinas", "" + alunos.get(i).getPessoaFisica());
        }
        realm.commitTransaction();
    }
}

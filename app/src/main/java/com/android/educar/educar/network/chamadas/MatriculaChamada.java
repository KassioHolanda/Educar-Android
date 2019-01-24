package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaMatriculaAPI;
import com.android.educar.educar.utils.Preferences;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatriculaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private AlunoChamada alunoChamada;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public MatriculaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        alunoChamada = new AlunoChamada(context);
    }


    public void recuperarMatriculasTurmas(Long turmaId) {
        Call<List<Matricula>> listCall = apiService.getMatriculaEndPoint().getmatriculas(turmaId);
        listCall.enqueue(new Callback<List<Matricula>>() {
            @Override
            public void onResponse(Call<List<Matricula>> call, Response<List<Matricula>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    recuperarAlunosMatricula(response.body());
                    Log.i("RESPONSE", "MATRICULAS RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<List<Matricula>> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    public void recuperarAlunoFrequenciaMesAPI(Long matriculaId) {
        Call<List<AlunoFrequenciaMes>> listCall = apiService.getAlunoFrequenciaMesEndPoint().alunoFrequenciaMes(matriculaId);
        listCall.enqueue(new Callback<List<AlunoFrequenciaMes>>() {
            @Override
            public void onResponse(Call<List<AlunoFrequenciaMes>> call, Response<List<AlunoFrequenciaMes>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    Log.i("RESPONSE", "ALUNOFREQUENCIAMES RECUPERADOS");
                }
            }

            @Override
            public void onFailure(Call<List<AlunoFrequenciaMes>> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    public void recuperarAlunosMatricula(List<Matricula> matriculas) {
        for (int i = 0; i < matriculas.size(); i++) {
            alunoChamada.recuperarAlunosMatricula(matriculas.get(i).getAluno());
            alunoChamada.recuperarDisciplinaAlunoMatricula(matriculas.get(i).getId());
            recuperarAlunoFrequenciaMesAPI(matriculas.get(i).getId());
        }
    }

    public void atualizarAlunoFrequenciaMes() {
        RealmResults<AlunoFrequenciaMes> alunoFrequenciaMes = realm.where(AlunoFrequenciaMes.class).findAll();
        for (int i = 0; i < alunoFrequenciaMes.size(); i++) {
            if (alunoFrequenciaMes.get(i).isNovo()) {
                AlunoFrequenciaMes alunoFrequenciaMes1 = new AlunoFrequenciaMes();
                alunoFrequenciaMes1.setId(alunoFrequenciaMes.get(i).getId());
                alunoFrequenciaMes1.setTotalFaltas(alunoFrequenciaMes.get(i).getTotalFaltas());
                alunoFrequenciaMes1.setBimestre(alunoFrequenciaMes.get(i).getBimestre());
                alunoFrequenciaMes1.setMatricula(alunoFrequenciaMes.get(i).getMatricula());
                alunoFrequenciaMes1.setDisciplinaAluno(alunoFrequenciaMes.get(i).getDisciplinaAluno());
                alunoFrequenciaMes1.setTipoLancamentoFrequencia(alunoFrequenciaMes.get(i).getTipoLancamentoFrequencia());
                alunoFrequenciaMes1.setDisciplina(alunoFrequenciaMes.get(i).getDisciplina());
                publicarAlunoFrequenciaMes(alunoFrequenciaMes1);
            }
        }
    }

    public void publicarAlunoFrequenciaMes(final AlunoFrequenciaMes alunoFrequenciaMes) {
        Call<AlunoFrequenciaMes> alunoFrequenciaMesCall = apiService.getAlunoFrequenciaMesEndPoint().postAlunoFrequenciaMes(alunoFrequenciaMes.getId(), alunoFrequenciaMes);
        alunoFrequenciaMesCall.enqueue(new Callback<AlunoFrequenciaMes>() {
            @Override
            public void onResponse(Call<AlunoFrequenciaMes> call, Response<AlunoFrequenciaMes> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    alunoFrequenciaMes.setNovo(false);
                    realm.copyToRealmOrUpdate(alunoFrequenciaMes);
                    realm.commitTransaction();
                    Log.i("RESPONSE", "ALUNOFRQUENCIAMES PUBLICADO");
                }
            }

            @Override
            public void onFailure(Call<AlunoFrequenciaMes> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }
}

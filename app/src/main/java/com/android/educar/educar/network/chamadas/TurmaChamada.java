package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.modelalterado.GradeCurso;
import com.android.educar.educar.model.modelalterado.SerieTurma;
import com.android.educar.educar.model.modelalterado.SituacaoTurmaMes;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.network.service.APIService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurmaChamada {
    private Context context;
    private APIService apiService;
    private Realm realm;
    private SerieChamada serieChamada;
    private DisciplinaChamada disciplinaChamada;
    private MatriculaChamada matriculaChamada;

    public TurmaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
        serieChamada = new SerieChamada(context);
        disciplinaChamada = new DisciplinaChamada(context);
        matriculaChamada = new MatriculaChamada(context);
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarGradeCurso(Long professorId) {
        Call<List<GradeCurso>> gradeCursoCall = apiService.getGradeCursoEndPoint().getGradeCrusoTurmaProfessor(professorId);
        gradeCursoCall.enqueue(new Callback<List<GradeCurso>>() {
            @Override
            public void onResponse(Call<List<GradeCurso>> call, Response<List<GradeCurso>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    recuperarSerieDisciplina(response.body());
                    Log.i("RESPONSE", "GRADECURSO RECUPERADOS");
                }
            }

            @Override
            public void onFailure(Call<List<GradeCurso>> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    public void recuperarSerieDisciplina(List<GradeCurso> gradeCursos) {
        for (int i = 0; i < gradeCursos.size(); i++) {
//            serieChamada.recuperarSerieDisciplina(gradeCursos.get(i).getSeriedisciplina());
//            serieChamada.recuperarSerieDisciplinaPelaDisciplina(gradeCursos.get(i).getDisciplina());
//            disciplinaChamada.recuperarDisciplinasTurma(gradeCursos.get(i).getDisciplina());
        }
    }


    public void recuperarTurmasDaUnidade(final Long sala) {
        Call<List<Turma>> listCall = apiService.getTurmaEndPoint().turmasUnidade(sala);
        listCall.enqueue(new Callback<List<Turma>>() {
            @Override
            public void onResponse(Call<List<Turma>> call, Response<List<Turma>> response) {
                if (response.isSuccessful()) {
                    long id = sala;
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    try {
                        Thread.sleep(1000);
                        recuerarSeriesDaTurma(response.body());
                        Thread.sleep(1000);
                        recuperarMatriculasDaTurma(response.body());
                        Thread.sleep(1000);
                        recuperarSituacaoTurmaMes(response.body());
                        Thread.sleep(1000);
                        recuperarSerieTurma(response.body());
                    } catch (InterruptedException ex) {
                    }
                    Log.i("RESPONSE", "TURMAS RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<List<Turma>> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    public void recuperarSerieTurma(List<Turma> turmas) {
        for (Turma t : turmas) {
            recuperarSerieTurmaApi(t.getId());
        }
    }

    public void recuperarSituacaoTurmaMes(List<Turma> turmas) {
        for (int i = 0; i < turmas.size(); i++) {
            recuperarSituacaoTurmaMesAPI(turmas.get(i).getId());
        }
    }

    public void recuperarMatriculasDaTurma(List<Turma> turmas) {
        for (int i = 0; i < turmas.size(); i++) {
            matriculaChamada.recuperarMatriculasTurmas(turmas.get(i).getId());
        }
    }

    public void recuerarSeriesDaTurma(List<Turma> turmas) {
        for (int i = 0; i < turmas.size(); i++) {
//            serieChamada.recuperarSerieAPI(turmas.get(i).getSerie());
        }
    }

    public void recuperarSerieTurmaApi(Long serieTurma) {
        Call<List<SerieTurma>> listCall = apiService.getSerieTurmaEndPoint().seriesTurma(serieTurma);
        listCall.enqueue(new Callback<List<SerieTurma>>() {
            @Override
            public void onResponse(Call<List<SerieTurma>> call, Response<List<SerieTurma>> response) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                realm.commitTransaction();
                Log.i("RESPONSE", "serieturma RECUPERADAS");
            }

            @Override
            public void onFailure(Call<List<SerieTurma>> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }


    public void recuperarSituacaoTurmaMesAPI(Long turmaId) {
        Call<List<SituacaoTurmaMes>> listaUnidadesAPICall = apiService.getSituacaoTurmaMesEndPoint().situacaoTurmaMes(turmaId);
        listaUnidadesAPICall.enqueue(new Callback<List<SituacaoTurmaMes>>() {
            @Override
            public void onResponse(Call<List<SituacaoTurmaMes>> call, Response<List<SituacaoTurmaMes>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    Log.i("RESPONSE", "SITUACAOTURMAMES RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<List<SituacaoTurmaMes>> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }


    public void postSituacaoTurmaMesAPI(final SituacaoTurmaMes situacaoTurmaMes) {
        Call<SituacaoTurmaMes> situacaoTurmaMesCall = apiService.getSituacaoTurmaMesEndPoint().postSituacaoTurmaMes(situacaoTurmaMes);
        situacaoTurmaMesCall.enqueue(new Callback<SituacaoTurmaMes>() {
            @Override
            public void onResponse(Call<SituacaoTurmaMes> call, Response<SituacaoTurmaMes> response) {
                if (response.isSuccessful()) {
//                    realm.beginTransaction();
//                    situacaoTurmaMes.setNovo(false);
//                    realm.copyToRealmOrUpdate(situacaoTurmaMes);
//                    realm.commitTransaction();
                    Log.i("RESPONSE", "SITUACAOTURMAMES PUBLICADA");
                }
            }

            @Override
            public void onFailure(Call<SituacaoTurmaMes> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }


    public void publicarSituacaoTurmaMes() {
        RealmResults<SituacaoTurmaMes> situacaoTurmaMes = realm.where(SituacaoTurmaMes.class).findAll();
        for (int i = 0; i < situacaoTurmaMes.size(); i++) {
            if (situacaoTurmaMes.get(i).isNovo()) {
                SituacaoTurmaMes situacaoTurmaMes1 = new SituacaoTurmaMes();
                situacaoTurmaMes1.setDataHora(situacaoTurmaMes.get(i).getDataHora());
                situacaoTurmaMes1.setStatus(situacaoTurmaMes.get(i).getStatus());
                situacaoTurmaMes1.setQuantidadeAprovados(situacaoTurmaMes.get(i).getQuantidadeAprovados());
//                situacaoTurmaMes1.setTurma(situacaoTurmaMes.get(i).getTurma());
                situacaoTurmaMes1.setQuantidadeReprovados(situacaoTurmaMes.get(i).getQuantidadeReprovados());
                situacaoTurmaMes1.getBimestre();

                realm.beginTransaction();
                situacaoTurmaMes.get(i).setNovo(false);
                realm.copyFromRealm(situacaoTurmaMes.get(i));
                realm.commitTransaction();

                postSituacaoTurmaMesAPI(situacaoTurmaMes1);
            }
        }
    }
}

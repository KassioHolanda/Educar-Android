package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaAlunoNotaMesAPI;
import com.android.educar.educar.network.service.ListaAlunosAPI;
import com.android.educar.educar.network.service.ListaDisciplinaAlunoAPI;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoChamada {
    private Realm realm;
    private Context context;
    private APIService apiService;
    private PessoaChamada pessoaChamada;
    private OcorrenciaChamada ocorrenciaChamada;
    private Preferences preferences;

    public AlunoChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
        pessoaChamada = new PessoaChamada(context);
        ocorrenciaChamada = new OcorrenciaChamada(context);
        preferences = new Preferences(context);
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarDisciplinaAlunoMatricula(Long matriculaId) {
        Call<List<DisciplinaAluno>> listCall = apiService.getDisciplinaAlunoEndPoint().disciplinasAluno(matriculaId);
        listCall.enqueue(new Callback<List<DisciplinaAluno>>() {
            @Override
            public void onResponse(Call<List<DisciplinaAluno>> call, Response<List<DisciplinaAluno>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<List<DisciplinaAluno>> call, Throwable t) {

            }
        });
    }

    private void atualizarDisciplinaAluno(Long id, DisciplinaAluno disciplinaAluno) {
        Call<DisciplinaAluno> disciplinaAlunoCall = apiService.getDisciplinaAlunoEndPoint().atualizarDisciplinaAluno(id, disciplinaAluno);
        disciplinaAlunoCall.enqueue(new Callback<DisciplinaAluno>() {
            @Override
            public void onResponse(Call<DisciplinaAluno> call, Response<DisciplinaAluno> response) {
                if (response.isSuccessful()) {
                    Log.i("RESPONSE", "DISCIPLINAALUNO PUBLICADO");
                }
            }

            @Override
            public void onFailure(Call<DisciplinaAluno> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    private void postAlunoNotaMes(AlunoNotaMes alunoNotaMes) {
        Call<AlunoNotaMes> alunoNotaMesCall = apiService.getAlunoNotaMesEndPoint().postAlunoNotaMes(alunoNotaMes);
        alunoNotaMesCall.enqueue(new Callback<AlunoNotaMes>() {
            @Override
            public void onResponse(Call<AlunoNotaMes> call, Response<AlunoNotaMes> response) {
                if (response.isSuccessful()) {
//                    realm.beginTransaction();
//                    alunoNotaMes.setNovo(false);
//                    realm.copyToRealmOrUpdate(alunoNotaMes);
//                    realm.commitTransaction();
                    Log.i("RESPONSE", "ALUNONTOAMES PUBLICADO");
                }
            }

            @Override
            public void onFailure(Call<AlunoNotaMes> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    private void atualizarAlunoNotaMes(Long alunoNotamesId, AlunoNotaMes alunoNotaMes) {
        Call<AlunoNotaMes> alunoNotaMesCall = apiService.getAlunoNotaMesEndPoint().atualizarAlunoNotaMes(alunoNotamesId, alunoNotaMes);
        alunoNotaMesCall.enqueue(new Callback<AlunoNotaMes>() {
            @Override
            public void onResponse(Call<AlunoNotaMes> call, Response<AlunoNotaMes> response) {
                Log.i("RESPONSE", "ALUNONTOAMES ALTERADO");
            }

            @Override
            public void onFailure(Call<AlunoNotaMes> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }

    public void publicarAlunoNotaMesNaAPI() {
        RealmResults<AlunoNotaMes> alunoNotaMes = realm.where(AlunoNotaMes.class).findAll();

        for (int i = 0; i < alunoNotaMes.size(); i++) {

            AlunoNotaMes alunoNotaMes1 = new AlunoNotaMes();
            alunoNotaMes1.setDisciplina(alunoNotaMes.get(i).getDisciplina());
            alunoNotaMes1.setInseridoFechamento(alunoNotaMes.get(i).isInseridoFechamento());
            alunoNotaMes1.setAnoLetivo(alunoNotaMes.get(i).getAnoLetivo());
            alunoNotaMes1.setId(0);
            alunoNotaMes1.setDisciplinaAluno(alunoNotaMes.get(i).getDisciplinaAluno());
            alunoNotaMes1.setMatricula(alunoNotaMes.get(i).getMatricula());
            alunoNotaMes1.setDatahora(UtilsFunctions.formatoDataPadrao().format(new Date()));
            alunoNotaMes1.setUsuario(preferences.getSavedLong(Messages.ID_UNIDADE));
            alunoNotaMes1.setUnidade(alunoNotaMes.get(i).getUnidade());
            alunoNotaMes1.setTipoLancamentoNota(alunoNotaMes.get(i).getTipoLancamentoNota());
            alunoNotaMes1.setBimestre(alunoNotaMes.get(i).getBimestre());
            alunoNotaMes1.setSequencia(0);
            alunoNotaMes1.setTipoLancamentoNota("LANCADO_APP");

            if (alunoNotaMes.get(i).isNovo()) {
                alunoNotaMes1.setNota(alunoNotaMes.get(i).getNota());
                realm.beginTransaction();
                alunoNotaMes.get(i).setNovo(false);
                realm.copyFromRealm(alunoNotaMes.get(i));
                realm.commitTransaction();

                postAlunoNotaMes(alunoNotaMes1);

            } else if (alunoNotaMes.get(i).isAlterado()) {
                alunoNotaMes1.setNota(alunoNotaMes.get(i).getNota());
                alunoNotaMes1.setId(alunoNotaMes.get(i).getId());
                realm.beginTransaction();
//                alunoNotaMes.get(i).setNovo(false);
                alunoNotaMes.get(i).setAlterado(false);
                realm.copyFromRealm(alunoNotaMes.get(i));
                realm.commitTransaction();

                atualizarAlunoNotaMes(alunoNotaMes1.getId(), alunoNotaMes1);
            }
        }
    }

    public void atualizarDadosDisciplinaAluno() {
        RealmResults<DisciplinaAluno> disciplinaAlunos = realm.where(DisciplinaAluno.class).findAll();
        for (int i = 0; i < disciplinaAlunos.size(); i++) {
            if (disciplinaAlunos.get(i).isAlterado()) {
                DisciplinaAluno disciplinaAluno = new DisciplinaAluno();
                disciplinaAluno.setStatusAtual(disciplinaAlunos.get(i).getStatusAtual());
                disciplinaAluno.setSerieDisciplina(disciplinaAlunos.get(i).getSerieDisciplina());
                disciplinaAluno.setStatusDisciplinaAluno(disciplinaAlunos.get(i).getStatusDisciplinaAluno());
                disciplinaAluno.setMesesFechadosNota(disciplinaAlunos.get(i).getMesesFechadosNota());
                disciplinaAluno.setNotaAcumulada(disciplinaAlunos.get(i).getNotaAcumulada());
                disciplinaAluno.setDataCadastroAtualizacaoProvaFinal(disciplinaAlunos.get(i).getDataCadastroAtualizacaoProvaFinal());
                disciplinaAluno.setNotaProvaFinal(disciplinaAlunos.get(i).getNotaProvaFinal());
                disciplinaAluno.setFechadoProvaFInal(disciplinaAlunos.get(i).getFechadoProvaFInal());
                disciplinaAluno.setDataCadastroAtualizacaoProvaFinal(disciplinaAlunos.get(i).getDataCadastroAtualizacaoProvaFinal());
                disciplinaAluno.setNotaAntigaProvaFinal(disciplinaAlunos.get(i).getNotaAntigaProvaFinal());
                disciplinaAluno.setUsuarioAtualizacaoProvaFinal(disciplinaAlunos.get(i).getUsuarioAtualizacaoProvaFinal());
                disciplinaAluno.setStatusAtual(disciplinaAlunos.get(i).getStatusAtual());
                disciplinaAluno.setId(disciplinaAlunos.get(i).getId());
                disciplinaAluno.setMediaAculumada(disciplinaAlunos.get(i).getMediaAculumada());
                disciplinaAluno.setCargaHoraria(disciplinaAlunos.get(i).getCargaHoraria());

                realm.beginTransaction();
                disciplinaAlunos.get(i).setAlterado(false);
                realm.copyFromRealm(disciplinaAlunos.get(i));
                realm.commitTransaction();

                atualizarDisciplinaAluno(disciplinaAluno.getId(), disciplinaAluno);
            }
        }
    }

    public void recuperarAlunosMatricula() {
        RealmResults<Matricula> matriculas = realm.where(Matricula.class).findAll();
        for (int i = 0; i < matriculas.size(); i++) {
//            recuperarAlunosMatricula(matriculas.get(i).getAluno());
        }
    }

    public void recuperarAlunosMatricula(Long alunoId) {
        Call<Aluno> alunoCall = apiService.getAlunoEndPoint().getAluno(alunoId);
        alunoCall.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
//                    pessoaChamada.recuperarPessoaFisicaAluno(response.body().getPessoaFisica());
//                    ocorrenciaChamada.recuperarOcorrenciasAluno(response.body().getId());
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.i("ERRO API", "" + t.getMessage());
            }
        });
    }
}

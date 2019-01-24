package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
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
import com.google.gson.annotations.SerializedName;

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
    private RealmObjectsDAO realmObjectsDAO;
    private PessoaChamada pessoaChamada;

    public AlunoChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        pessoaChamada = new PessoaChamada(context);

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

            }

            @Override
            public void onFailure(Call<DisciplinaAluno> call, Throwable t) {

            }
        });
    }

    private void postAlunoNotaMes(final AlunoNotaMes alunoNotaMes) {
        Call<AlunoNotaMes> alunoNotaMesCall = apiService.getAlunoNotaMesEndPoint().postAlunoNotaMes(alunoNotaMes);
        alunoNotaMesCall.enqueue(new Callback<AlunoNotaMes>() {
            @Override
            public void onResponse(Call<AlunoNotaMes> call, Response<AlunoNotaMes> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    alunoNotaMes.setNovo(false);
                    realm.copyToRealmOrUpdate(alunoNotaMes);
                    realm.commitTransaction();
                    Log.i("RESPONSE", "ALUNONTOAMES PUBLICADO");
                }
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
            if (alunoNotaMes.get(i).isNovo()) {
                AlunoNotaMes alunoNotaMes1 = new AlunoNotaMes();

                alunoNotaMes1.setDisciplina(alunoNotaMes.get(i).getDisciplina());
                alunoNotaMes1.setInseridoFechamento(alunoNotaMes.get(i).isInseridoFechamento());
                alunoNotaMes1.setAnoLetivo(alunoNotaMes.get(i).getAnoLetivo());
                alunoNotaMes1.setId(alunoNotaMes.get(i).getId());
                alunoNotaMes1.setDisciplinaAluno(alunoNotaMes.get(i).getDisciplinaAluno());
                alunoNotaMes1.setMatricula(alunoNotaMes.get(i).getMatricula());
                alunoNotaMes1.setDatahora(alunoNotaMes.get(i).getDatahora());
                alunoNotaMes1.setNota(alunoNotaMes.get(i).getNota());
                alunoNotaMes1.setUsuario(alunoNotaMes.get(i).getUsuario());
                alunoNotaMes1.setUnidade(alunoNotaMes.get(i).getUnidade());
                alunoNotaMes1.setTipoLancamentoNota(alunoNotaMes.get(i).getTipoLancamentoNota());
                alunoNotaMes1.setBimestre(alunoNotaMes.get(i).getBimestre());
                alunoNotaMes1.setSequencia(0);
                postAlunoNotaMes(alunoNotaMes1);
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

                realm.beginTransaction();
                disciplinaAluno.setAlterado(false);
                realm.commitTransaction();

                atualizarDisciplinaAluno(disciplinaAluno.getId(), disciplinaAluno);
            }
        }
    }

    public void recuperarAlunosMatricula() {
        RealmResults<Matricula> matriculas = realm.where(Matricula.class).findAll();
        for (int i = 0; i < matriculas.size(); i++) {
            recuperarAlunosMatricula(matriculas.get(i).getAluno());
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
                    pessoaChamada.recuperarPessoaFisicaAluno(response.body().getPessoaFisica());
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {

            }
        });
    }

}

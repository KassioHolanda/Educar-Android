package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunoNotaMesAPI;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaDisciplinaAlunoAPI;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoChamada {
    private Realm realm;
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;

    public AlunoChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }


    public void recuperarTodosAlunosAPI() {
        Call<ListaAlunosAPI> listaAlunosAPICall = apiService.getAlunoEndPoint().alunos();
        listaAlunosAPICall.enqueue(new Callback<ListaAlunosAPI>() {
            @Override
            public void onResponse(Call<ListaAlunosAPI> call, Response<ListaAlunosAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAlunosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void recuperarTodosAlunoNotaMesAPI() {
        Call<ListaAlunoNotaMesAPI> listaAlunosAPICall = apiService.getAlunoNotaMesEndPoint().alunosNotaMes();
        listaAlunosAPICall.enqueue(new Callback<ListaAlunoNotaMesAPI>() {
            @Override
            public void onResponse(Call<ListaAlunoNotaMesAPI> call, Response<ListaAlunoNotaMesAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAlunoNotaMesAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recuperarTodasDisciplinaAlunoAPI() {
        Call<ListaDisciplinaAlunoAPI> listaAlunosAPICall = apiService.getDisciplinaAlunoEndPoint().disciplinasAluno();
        listaAlunosAPICall.enqueue(new Callback<ListaDisciplinaAlunoAPI>() {
            @Override
            public void onResponse(Call<ListaDisciplinaAlunoAPI> call, Response<ListaDisciplinaAlunoAPI> response) {
                if (response.isSuccessful()) {
                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaDisciplinaAlunoAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postAlunoNotaMes(AlunoNotaMes alunoNotaMes) {
        Call<AlunoNotaMes> alunoNotaMesCall = apiService.getAlunoNotaMesEndPoint().postAlunoNotaMes(alunoNotaMes);
        alunoNotaMesCall.enqueue(new Callback<AlunoNotaMes>() {
            @Override
            public void onResponse(Call<AlunoNotaMes> call, Response<AlunoNotaMes> response) {

            }

            @Override
            public void onFailure(Call<AlunoNotaMes> call, Throwable t) {

            }
        });
    }

    public void publicarDadosRealmParaAPI() {
        RealmResults<AlunoNotaMes> alunoNotaMes = realm.where(AlunoNotaMes.class).findAll();

        for (int i = 0; i < alunoNotaMes.size(); i++) {
            if (alunoNotaMes.get(i).isNovo()) {
//                AlunoNotaMes alunoNotaMes1 = new AlunoNotaMes();
//                alunoNotaMes1.setNovo(false);
//                alunoNotaMes1.setDisciplina(alunoNotaMes.get(i).getDisciplina());
//                alunoNotaMes1.setInseridoFechamento(alunoNotaMes.get(i).isInseridoFechamento());
//                alunoNotaMes1.setAnoLetivo(alunoNotaMes.get(i).getAnoLetivo());
//                alunoNotaMes1.setId(alunoNotaMes.get(i).getId());
//                alunoNotaMes1.setDisciplinaAluno(alunoNotaMes.get(i).getDisciplinaAluno());
//                alunoNotaMes1.setMatricula(alunoNotaMes.get(i).getMatricula());
//                alunoNotaMes1.setDatahora(alunoNotaMes.get(i).getDatahora());
//                alunoNotaMes1.setNota(alunoNotaMes.get(i).getNota());
//                alunoNotaMes1.setUsuario(alunoNotaMes.get(i).getUsuario());
//                alunoNotaMes1.setUnidade(alunoNotaMes.get(i).getUnidade());
//                alunoNotaMes1.setTipoLancamentoNota(alunoNotaMes.get(i).getTipoLancamentoNota());
//                alunoNotaMes1.setBimestre(alunoNotaMes.get(i).getBimestre());
//                alunoNotaMes1.setSequencia(0);
                alunoNotaMes.get(i).setNovo(false);
                realmObjectsDAO.salvarRealm(alunoNotaMes.get(i));

                postAlunoNotaMes(alunoNotaMes.get(i));
            }
        }
    }


}

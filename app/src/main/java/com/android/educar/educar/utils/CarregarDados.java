package com.android.educar.educar.utils;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.AlunoDAO;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.DisciplinaDAO;
import com.android.educar.educar.dao.TurmaDAO;
import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.TurmaAluno;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaDisciplinasAPI;
import com.android.educar.educar.service.ListaTurmaAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarregarDados {
    private APIService apiService;
    private UnidadeDAO unidadeDAO;
    private TurmaDAO turmaDAO;
    private ClassDAO classDAO;
    private AlunoDAO alunoDAO;
    private DisciplinaDAO disciplinaDAO;

    public CarregarDados(Context context) {
        apiService = new APIService("");
        unidadeDAO = new UnidadeDAO(context);
        classDAO = new ClassDAO(context);
        turmaDAO = new TurmaDAO(context);
        disciplinaDAO = new DisciplinaDAO(context);
        alunoDAO = new AlunoDAO(context);
    }

    public void carregarDados() {
        carregarUnidadesAPI();
        carregarTurmasAPI();
        carregarDisciplinasAPI();
        carregarAlunosAPI();

    }

    public void turmaAluno() {
        for (int i = 0; i < alunoDAO.alunos().size() - 3; i++) {
            TurmaAluno turmaAluno = new TurmaAluno();
            turmaAluno.setAluno(alunoDAO.alunos().get(i).getPk());
            turmaAluno.setTurma(1);
            classDAO.addTurmaAluno(turmaAluno);
        }
    }

    public void carregarUnidadesAPI() {
        Call<ListaUnidadesAPI> listaUnidadesAPICall = apiService.getUnidadeEndPoint().unidades();
        listaUnidadesAPICall.enqueue(new Callback<ListaUnidadesAPI>() {
            @Override
            public void onResponse(Call<ListaUnidadesAPI> call, Response<ListaUnidadesAPI> response) {
                if (response.isSuccessful()) {
//                    atualizarAdapterListaUnidades(response.body().getResults());
                    salvarUnidadeBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaUnidadesAPI> call, Throwable t) {

            }
        });
    }


    public void carregarTurmasAPI() {
        Call<ListaTurmaAPI> listaTurmaAPICall = apiService.getTurmaEndPoint().turmas();
        listaTurmaAPICall.enqueue(new Callback<ListaTurmaAPI>() {
            @Override
            public void onResponse(Call<ListaTurmaAPI> call, Response<ListaTurmaAPI> response) {
                if (response.isSuccessful()) {
//                    atualizarAdapterListaTurmas(response.body().getResults());
                    salvarTurmaBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaTurmaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarTurmaBD(List<Turma> turmas) {
        for (int i = 0; i < turmas.size(); i++) {
            turmaDAO.addTurma(turmas.get(i));
        }
    }

    public void salvarUnidadeBD(List<Unidade> unidades) {
        for (int i = 0; i < unidades.size(); i++) {
            unidadeDAO.addUnidade(unidades.get(i));
        }
    }

    public void salvarDisciplinaBD(List<Disciplina> disciplinas) {
        for (int i = 0; i < disciplinas.size(); i++) {
            disciplinaDAO.addDisiciplina(disciplinas.get(i));
            Log.i("DISCIPLINA", "Disciplina " + disciplinas.get(i) + " add");
        }
    }

    public void salvarAlunoBD(List<Aluno> alunos) {
        for (int i = 0; i < alunos.size(); i++) {
            classDAO.addAluno(alunos.get(i));
            Log.i("DISCIPLINA", "Aluno " + alunos.get(i) + " add");
        }
    }

    public void carregarDisciplinasAPI() {
        Call<ListaDisciplinasAPI> listaDisciplinasAPICall = apiService.getDisciplinaEndPoint().disciplinas();
        listaDisciplinasAPICall.enqueue(new Callback<ListaDisciplinasAPI>() {
            @Override
            public void onResponse(Call<ListaDisciplinasAPI> call, Response<ListaDisciplinasAPI> response) {
                if (response.isSuccessful()) {
//                    atualizarAdapterListaDisciplinas(response.body().getResults());
                    salvarDisciplinaBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaDisciplinasAPI> call, Throwable t) {

            }
        });
    }

    public void carregarAlunosAPI() {
        Call<ListaAlunosAPI> listaAlunosAPICall = apiService.getAlunoEndPoint().alunos();
        listaAlunosAPICall.enqueue(new Callback<ListaAlunosAPI>() {
            @Override
            public void onResponse(Call<ListaAlunosAPI> call, Response<ListaAlunosAPI> response) {
                if (response.isSuccessful()) {
                    salvarAlunoBD(response.body().getResults());
//                    atualizarAdapterFrequencia(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAlunosAPI> call, Throwable t) {

            }
        });

    }
}

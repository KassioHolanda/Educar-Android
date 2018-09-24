package com.android.educar.educar.utils;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.AlunoDAO;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.DisciplinaDAO;
import com.android.educar.educar.dao.ProfessorDAO;
import com.android.educar.educar.dao.TurmaAlunoDAO;
import com.android.educar.educar.dao.TurmaDAO;
import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.TurmaAluno;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.model.UnidadeProfessor;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaDisciplinasAPI;
import com.android.educar.educar.service.ListaTurmaAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import okhttp3.internal.Util;
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
    private TurmaAlunoDAO turmaAlunoDAO;
    private ProfessorDAO professorDAO;

    public CarregarDados(Context context) {
        apiService = new APIService("");
        unidadeDAO = new UnidadeDAO(context);
        classDAO = new ClassDAO(context);
        turmaDAO = new TurmaDAO(context);
        disciplinaDAO = new DisciplinaDAO(context);
        alunoDAO = new AlunoDAO(context);
        turmaAlunoDAO = new TurmaAlunoDAO(context);
        professorDAO = new ProfessorDAO(context);
    }

    public void carregarDados() {
//        carregarUnidadesAPI();
//        carregarTurmasAPI();
//        carregarDisciplinasAPI();
//        carregarAlunosAPI();
        salvarUniadadesBD();
        salvarTurmasBD();
        salvarDisciplinaBD();
        salvarAlunoBD();
        turmaAluno();
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
            alunoDAO.addAluno(alunos.get(i));
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

    public void salvarProfessoresBD() {
        Professor professor = null;
        try {
            professor = new Professor("Kassio Holanda", "06365717342", UtilsFunctions.criptografaSenha("1"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        classDAO.addProfessor(professor);
    }

    public void salvarUniadadesBD() {
        Unidade unidade1 = new Unidade("EMEF ALCIDES SOTERO DE ASSUNCAO");
        Unidade unidade2 = new Unidade("EMEF ALICE MENDES");
        Unidade unidade3 = new Unidade("EMEF ANGELICA MARIA DOS SANTOS");
        unidadeDAO.addUnidade(unidade1);
        unidadeDAO.addUnidade(unidade2);
        unidadeDAO.addUnidade(unidade3);
    }

    public void salvarUnidadeProfessor() {
        UnidadeProfessor unidadeProfessor = null;
//        unidadeProfessor = new UnidadeProfessor()
    }

    public void salvarTurmasBD() {
//        long unidade, String descricao, String turno
        Turma turma1 = new Turma(1, "MA10", "Tarde");
        Turma turma2 = new Turma(1, "MT02", "Tarde");
        Turma turma3 = new Turma(1, "TA01", "Tarde");
        Turma turma4 = new Turma(1, "MA201", "Tarde");
        turmaDAO.addTurma(turma1);
        turmaDAO.addTurma(turma2);
        turmaDAO.addTurma(turma3);
        turmaDAO.addTurma(turma4);
    }

    public void salvarAlunoBD() {
//        String nomeAluno, long turma, int quantidadeFalta
        Aluno aluno1 = new Aluno("CLEICIANE DOS SANTOS MELO", 0);
        Aluno aluno2 = new Aluno("KAYLANE VITORIA MELO DE OLIVEIRA", 0);
        Aluno aluno3 = new Aluno("LUAN GOMES DE ASSUNCAO", 0);
        Aluno aluno4 = new Aluno("LUIS OTAVIO BEZERRA DA SILVA", 0);
        Aluno aluno5 = new Aluno("MARIA GABRIELY ALVES MELO", 0);
        Aluno aluno6 = new Aluno("MAXSUEL ASSUNCAO MUNIZ", 0);
        Aluno aluno7 = new Aluno("PEDRO HENRIQUE SILVA OLIVEIRA", 0);
        Aluno aluno8 = new Aluno("REGISLANE BRANDAO DE SOUSA", 0);

        alunoDAO.addAluno(aluno1);
        alunoDAO.addAluno(aluno2);
        alunoDAO.addAluno(aluno3);
        alunoDAO.addAluno(aluno4);
        alunoDAO.addAluno(aluno5);
        alunoDAO.addAluno(aluno6);
        alunoDAO.addAluno(aluno7);
        alunoDAO.addAluno(aluno8);
    }

    public void turmaAluno() {
        for (int i = 0; i < alunoDAO.alunos().size() - 3; i++) {
            TurmaAluno turmaAluno = new TurmaAluno();
            turmaAluno.setAluno(alunoDAO.alunos().get(i).getPk());
            turmaAluno.setTurma(1);
            turmaAlunoDAO.addTurmaAluno(turmaAluno);
        }
    }

    public void salvarDisciplinaBD() {
        Disciplina disciplina1 = new Disciplina(1, "Português");
        Disciplina disciplina2 = new Disciplina(1, "Matematica");
        Disciplina disciplina3 = new Disciplina(1, "Engenharia");
        Disciplina disciplina4 = new Disciplina(1, "Fisica");

        disciplinaDAO.addDisiciplina(disciplina1);
        disciplinaDAO.addDisiciplina(disciplina2);
        disciplinaDAO.addDisiciplina(disciplina3);
        disciplinaDAO.addDisiciplina(disciplina4);
    }

}

package com.android.educar.educar.bo;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.model.AnoLetivo;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.service.ListaUsuariosAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class RealmBO {
    private Context context;
    private Realm realm;

    public RealmBO(Context context) {
        this.context = context;
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarAlunosRealm(List<Aluno> alunos) {
        realm.beginTransaction();
        for (int i = 0; i < alunos.size(); i++) {
            realm.copyToRealmOrUpdate(alunos.get(i));
            Log.i("alunos", "" + alunos.get(i).getPessoaFisica());
        }
        realm.commitTransaction();
    }

    public void salvarAlunosNotaMesRealm(List<AlunoNotaMes> alunoNotaMes) {
        realm.beginTransaction();
        for (int i = 0; i < alunoNotaMes.size(); i++) {
            realm.copyToRealmOrUpdate(alunoNotaMes.get(i));
            Log.i("alunos", "" + alunoNotaMes.get(i).getMatricula());
        }
        realm.commitTransaction();
    }

    public void salvarDisciplinasRealm(List<Disciplina> disciplinas) {
        realm.beginTransaction();
        for (int i = 0; i < disciplinas.size(); i++) {
            realm.copyToRealmOrUpdate(disciplinas.get(i));
            Log.i("disciplinas", "" + disciplinas.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvaAnoLetivoRealm(List<AnoLetivo> anoLetivos) {
        realm.beginTransaction();
        for (int i = 0; i < anoLetivos.size(); i++) {
            realm.copyToRealmOrUpdate(anoLetivos.get(i));
            Log.i("anoletivo", "" + anoLetivos.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarBimestreRealm(List<Bimestre> bimestres) {
        realm.beginTransaction();
        for (int i = 0; i < bimestres.size(); i++) {
            realm.copyToRealmOrUpdate(bimestres.get(i));
            Log.i("anoletivo", "" + bimestres.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarFuncionarioEscolaRealm(final List<FuncionarioEscola> funcionarios) {
        realm.beginTransaction();
        for (int i = 0; i < funcionarios.size(); i++) {
            realm.copyToRealmOrUpdate(funcionarios.get(i));
            Log.i("funcionarioescola", "" + funcionarios.get(i).getFuncionario());
        }
        realm.commitTransaction();
    }

    public void salvarFuncionarioRealm(final List<Funcionario> funcionarios) {
        realm.beginTransaction();
        for (int i = 0; i < funcionarios.size(); i++) {
            realm.copyToRealmOrUpdate(funcionarios.get(i));
            Log.i("funcionario", "" + funcionarios.get(i).getCargo());
        }
        realm.commitTransaction();
    }

    public void salvarLocalEscolaRealm(final List<LocalEscola> localEscolas) {
        realm.beginTransaction();
        for (int i = 0; i < localEscolas.size(); i++) {
            realm.copyToRealmOrUpdate(localEscolas.get(i));
            Log.i("localescola", "" + localEscolas.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarMatriculaRealm(final List<Matricula> matriculas) {
        realm.beginTransaction();
        for (int i = 0; i < matriculas.size(); i++) {
            realm.copyToRealmOrUpdate(matriculas.get(i));
            Log.i("matricula", "" + matriculas.get(i).getStatusMatricula());
        }
        realm.commitTransaction();
    }

    public void salvarPessoaFisicaRealm(final List<PessoaFisica> pessoaFisicas) {
        realm.beginTransaction();
        for (int i = 0; i < pessoaFisicas.size(); i++) {
            realm.copyToRealmOrUpdate(pessoaFisicas.get(i));
            Log.i("pessoafisica", "" + pessoaFisicas.get(i).getNome());
        }
        realm.commitTransaction();
    }

    public void serieDisciplinaRealm(final List<SerieDisciplina> serieDisciplinas) {
        realm.beginTransaction();
        for (int i = 0; i < serieDisciplinas.size(); i++) {
            realm.copyToRealmOrUpdate(serieDisciplinas.get(i));
            Log.i("seriedisciplina", "" + serieDisciplinas.get(i).getId());
        }
        realm.commitTransaction();
    }

    public void salvarGradeCursoRealm(List<GradeCurso> gradeCursos) {
        realm.beginTransaction();
        for (int i = 0; i < gradeCursos.size(); i++) {
            realm.copyToRealmOrUpdate(gradeCursos.get(i));
            Log.i("gradecurso", "" + gradeCursos.get(i).getId());
        }
        realm.commitTransaction();
    }

    public void salvarTurmaRealm(List<Turma> turmas) {
        realm.beginTransaction();
        for (int i = 0; i < turmas.size(); i++) {
            realm.copyToRealmOrUpdate(turmas.get(i));
            Log.i("turma", "" + turmas.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarTiposOcorrenciaRealm(List<TipoOcorrencia> ocorrencias) {
        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            realm.copyToRealmOrUpdate(ocorrencias.get(i));
            Log.i("tipoocorrencia", "" + ocorrencias.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarOcorrenciaRealm(List<Ocorrencia> ocorrencias) {
        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            realm.copyToRealmOrUpdate(ocorrencias.get(i));
            Log.i("ocorrencia", "" + ocorrencias.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarUnidadeRealm(List<Unidade> unidades) {
        realm.beginTransaction();
        for (int i = 0; i < unidades.size(); i++) {
            realm.copyToRealmOrUpdate(unidades.get(i));
            Log.i("unidade", "" + unidades.get(i).getAbreviacao());
        }
        realm.commitTransaction();
    }

    public void salvarUsuariosRealm(List<Usuario> usuarios) {
        realm.beginTransaction();
        for (int i = 0; i < usuarios.size(); i++) {
            realm.copyToRealmOrUpdate(usuarios.get(i));
            Log.i("usuario", "" + usuarios.get(i).getLogin());
        }
        realm.commitTransaction();
    }

    public void salvarPerfisRealm(List<Perfil> perfis) {
        realm.beginTransaction();
        for (int i = 0; i < perfis.size(); i++) {
            realm.copyToRealmOrUpdate(perfis.get(i));
            Log.i("perfil", "" + perfis.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarRealm(Object o) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate((RealmModel) o);
        realm.commitTransaction();
    }
}

package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.network.chamadas.AlunoChamada;
import com.android.educar.educar.network.chamadas.AnoLetivoChamada;
import com.android.educar.educar.network.chamadas.DisciplinaChamada;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.FuncionarioEscolaChamada;
import com.android.educar.educar.network.chamadas.LocalEscolaChamada;
import com.android.educar.educar.network.chamadas.MatriculaChamada;
import com.android.educar.educar.network.chamadas.OcorrenciaChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.network.chamadas.SerieChamada;
import com.android.educar.educar.network.chamadas.TurmaChamada;
import com.android.educar.educar.network.chamadas.UnidadeChamada;
import com.android.educar.educar.utils.Preferences;

public class SincronizarComAPiMB {

    private TurmaChamada turmaChamada;
    private UnidadeChamada unidadeChamada;
    private DisciplinaChamada disciplinaChamada;
    private FuncionarioEscolaChamada funcionarioEscola;
    private LocalEscolaChamada localEscolaChamada;
    private SerieChamada serieChamada;
    private AlunoChamada alunoChamada;
    private MatriculaChamada matriculaChamada;
    private OcorrenciaChamada ocorrenciaChamada;
    private AnoLetivoChamada anoLetivoChamada;
    private PessoaChamada pessoaChamada;
    private FuncionarioChamada funcionarioChamada;

    private Preferences preferences;


    public SincronizarComAPiMB(Context context) {
        preferences = new Preferences(context);

        unidadeChamada = new UnidadeChamada(context);
        turmaChamada = new TurmaChamada(context);
        disciplinaChamada = new DisciplinaChamada(context);
        funcionarioEscola = new FuncionarioEscolaChamada(context);
        localEscolaChamada = new LocalEscolaChamada(context);
        serieChamada = new SerieChamada(context);
        alunoChamada = new AlunoChamada(context);
        matriculaChamada = new MatriculaChamada(context);
        anoLetivoChamada = new AnoLetivoChamada(context);
        ocorrenciaChamada = new OcorrenciaChamada(context);
        funcionarioChamada = new FuncionarioChamada(context);
        pessoaChamada = new PessoaChamada(context);
    }

    public void sincronizarRealmComAPi() {
//        unidadeChamada.recuperarUnidadesDoFuncionario(preferences.getSavedLong("id_funcionario"));
//        alunoChamada.recuperarTodosAlunosAPI();
//        turmaChamada.turmasAPI();
//        turmaChamada.gradeCursoAPI();
//        disciplinaChamada.disciplinasAPI();
//        funcionarioEscola.funcionariosEscola();
//        localEscolaChamada.localEscolaAPI();
//        serieChamada.serieDisciplina();
//        serieChamada.recuperarSerieAPI();


        alunoChamada.recuperarTodosAlunoNotaMesAPI();
        alunoChamada.recuperarTodasDisciplinaAlunoAPI();
//        turmaChamada.recuperarSituacaoTurmaMesAPI();


//        serieChamada.recuperarSerieTurmaAPI();
//        matriculaChamada.matriculaAPI();

//        anoLetivoChamada.anoLetivoAPI();
//        anoLetivoChamada.recuperarBimestreAPI();
//
//        ocorrenciaChamada.recuperarTodasOcorrenciasAPI();
//        ocorrenciaChamada.recuperarTodosTiposOcorrenciaAPI();


    }

    public void sincronizarAPiComRealm() {
        alunoChamada.publicarDadosRealmParaAPI();
        alunoChamada.atualizarDadosDisciplinaAluno();
        ocorrenciaChamada.publicarOcorrenciaAPI();
    }
}

package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.chamadas.AlunoChamada;
import com.android.educar.educar.chamadas.AnoLetivoChamada;
import com.android.educar.educar.chamadas.DisciplinaChamada;
import com.android.educar.educar.chamadas.FuncionarioEscolaChamada;
import com.android.educar.educar.chamadas.LocalEscolaChamada;
import com.android.educar.educar.chamadas.MatriculaChamada;
import com.android.educar.educar.chamadas.OcorrenciaChamada;
import com.android.educar.educar.chamadas.SerieChamada;
import com.android.educar.educar.chamadas.TurmaChamada;
import com.android.educar.educar.chamadas.UnidadeChamada;

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


    public SincronizarComAPiMB(Context context) {
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
    }

    public void sincronizarRealmComAPi() {
        unidadeChamada.unidadesAPI();

        turmaChamada.turmasAPI();
        turmaChamada.gradeCursoAPI();
        turmaChamada.recuperarSituacaoTurmaMesAPI();

        disciplinaChamada.disciplinasAPI();
        funcionarioEscola.funcionariosEscola();
        localEscolaChamada.localEscolaAPI();

        serieChamada.serieDisciplina();
        serieChamada.recuperarSerieAPI();
        serieChamada.recuperarSerieTurmaAPI();

        alunoChamada.recuperarTodosAlunosAPI();
        alunoChamada.recuperarTodosAlunoNotaMesAPI();
        alunoChamada.recuperarTodasDisciplinaAlunoAPI();

        matriculaChamada.matriculaAPI();

        anoLetivoChamada.anoLetivoAPI();
        anoLetivoChamada.recuperarBimestreAPI();

        ocorrenciaChamada.recuperarTodasOcorrenciasAPI();
        ocorrenciaChamada.recuperarTodosTiposOcorrenciaAPI();

    }

    public void sincronizarAPiComRealm() {
//        sincronizarOcorrenciaMB.publicarDadosRealmParaAPI();
//        sincronizarNotaMB.publicarDadosRealmParaAPI();

        alunoChamada.publicarDadosRealmParaAPI();
        ocorrenciaChamada.publicarOcorrenciaAPI();
    }
}

package com.android.educar.educar.bo;

import android.content.Context;

import com.android.educar.educar.chamadas.AlunoChamada;
import com.android.educar.educar.chamadas.AnoLetivoChamada;
import com.android.educar.educar.chamadas.DisciplinaChamada;
import com.android.educar.educar.chamadas.FuncionarioEscolaChamada;
import com.android.educar.educar.chamadas.LocalEscolaChamada;
import com.android.educar.educar.chamadas.MatriculaChamada;
import com.android.educar.educar.chamadas.OcorrenciaChamada;
import com.android.educar.educar.chamadas.SerieDisciplinaChamada;
import com.android.educar.educar.chamadas.TurmaChamada;
import com.android.educar.educar.chamadas.UnidadeChamada;
import com.android.educar.educar.mb.SincronizarOcorrenciaMB;

public class SincronizarAPiParaRealmBO {

    private TurmaChamada turmaChamada;
    private UnidadeChamada unidadeChamada;
    private DisciplinaChamada disciplinaChamada;
    private FuncionarioEscolaChamada funcionarioEscola;
    private LocalEscolaChamada localEscolaChamada;
    private SerieDisciplinaChamada serieDisciplinaChamada;
    private AlunoChamada alunoChamada;
    private MatriculaChamada matriculaChamada;
    private OcorrenciaChamada ocorrenciaChamada;
    private AnoLetivoChamada anoLetivoChamada;

    private SincronizarOcorrenciaMB sincronizarOcorrenciaMB;

    public SincronizarAPiParaRealmBO(Context context) {
        unidadeChamada = new UnidadeChamada(context);
        turmaChamada = new TurmaChamada(context);
        disciplinaChamada = new DisciplinaChamada(context);
        funcionarioEscola = new FuncionarioEscolaChamada(context);
        localEscolaChamada = new LocalEscolaChamada(context);
        serieDisciplinaChamada = new SerieDisciplinaChamada(context);
        alunoChamada = new AlunoChamada(context);
        matriculaChamada = new MatriculaChamada(context);
        anoLetivoChamada = new AnoLetivoChamada(context);
        ocorrenciaChamada = new OcorrenciaChamada(context);

        sincronizarOcorrenciaMB = new SincronizarOcorrenciaMB(context);
    }

    public void sincronizarRealmComAPi() {
        unidadeChamada.unidadesAPI();
        turmaChamada.turmasAPI();
        turmaChamada.gradeCursoAPI();
        disciplinaChamada.disciplinasAPI();
        funcionarioEscola.funcionariosEscola();
        localEscolaChamada.localEscolaAPI();
        serieDisciplinaChamada.serieDisciplina();
        alunoChamada.alunosAPI();
        alunoChamada.recuperarAlunoNotaMesAPI();
        matriculaChamada.matriculaAPI();
        anoLetivoChamada.anoLetivoAPI();
        anoLetivoChamada.recuperarBimestreAPI();

        ocorrenciaChamada.salvarDadosOcorrenciaApiBancoDeDados();
        ocorrenciaChamada.salvarDadosTipoOcorrenciaApiBancoDeDados();

    }

    public void sincronizarAPiComRealm() {
        sincronizarOcorrenciaMB.publicarDadosRealmParaAPI();

    }
}

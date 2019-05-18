package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.network.chamadas.AlunoChamada;
import com.android.educar.educar.network.chamadas.AnoLetivoChamada;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.FuncionarioEscolaChamada;
import com.android.educar.educar.network.chamadas.MatriculaChamada;
import com.android.educar.educar.network.chamadas.OcorrenciaChamada;
import com.android.educar.educar.network.chamadas.TurmaChamada;
import com.android.educar.educar.utils.Preferences;

public class SincronizarComAPiMB {


    private FuncionarioChamada funcionarioChamada;
    private TurmaChamada turmaChamada;
    private OcorrenciaChamada ocorrenciaChamada;
    private AnoLetivoChamada anoLetivoChamada;
    private Preferences preferences;
    private AlunoChamada alunoChamada;
    private FuncionarioEscolaChamada funcionarioEscola;
    private MatriculaChamada matriculaChamada;


    public SincronizarComAPiMB(Context context) {
        matriculaChamada = new MatriculaChamada(context);
        alunoChamada = new AlunoChamada(context);
        preferences = new Preferences(context);
        turmaChamada = new TurmaChamada(context);
        anoLetivoChamada = new AnoLetivoChamada(context);
        ocorrenciaChamada = new OcorrenciaChamada(context);
        funcionarioChamada = new FuncionarioChamada(context);
        funcionarioEscola = new FuncionarioEscolaChamada(context);
    }

    public void recuperarDadosDaAPISalvarBancoDeDadosRealm() {
        funcionarioEscola.funcionariosEscola(preferences.getSavedLong("id_funcionario"));
        turmaChamada.recuperarGradeCurso(preferences.getSavedLong("id_funcionario"));
        ocorrenciaChamada.recuperarTodosTiposOcorrenciaAPI();
        anoLetivoChamada.recuperarBimestreAPI();
//        anoLetivoChamada.anoLetivoAPI();
    }

    public void enviarDadosDoBancoDeDadosParaAPI() {
        alunoChamada.publicarAlunoNotaMesNaAPI();
//        turmaChamada.publicarSituacaoTurmaMes();
//        matriculaChamada.atualizarAlunoFrequenciaMes();
//        alunoChamada.atualizarDadosDisciplinaAluno();
//        ocorrenciaChamada.publicarNovaOcorrenciaParaAPI();
    }
}

package com.android.educar.educar.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.OcorrenciaListaAdapter;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.utils.Preferences;

import org.w3c.dom.Text;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotificacoesAlunoAcitivity extends AppCompatActivity {

    private Realm realm;
    private TextView alunoDetalhe;
    private TextView turmaDetalhe;
    private TextView disciplinaDetalhe;
    private ListView notificacoes;
    private PessoaFisica pessoaFisica;
    private Matricula matricula;
    private Aluno aluno;
    private CalendarView calendarView;
    private Preferences preferences;
    private Turma turma;
    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes_aluno_acitivity);
        configRealm();
        binding();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupInit();
        recuperarDadosRealm();
        atualizarListaDeNotificacoes();
        atualizarDadosTela();
    }

    public void setupInit() {
        preferences = new Preferences(this);
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void binding() {
        alunoDetalhe = findViewById(R.id.aluno_notificacoes_id);
        turmaDetalhe = findViewById(R.id.turma_notificacoes_aluno_id);
        disciplinaDetalhe = findViewById(R.id.disciplina_notificacao_aluno_id);
        notificacoes = findViewById(R.id.notificacoes_aluno_id);
//        calendarView = findViewById(R.id.calendarView);
    }

    public void configurarCalendario() {
//        calendarView.set
    }

    public void recuperarDadosRealm() {
        pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", preferences.getSavedLong("id_pessoafisica_aluno")).findFirst();
        aluno = realm.where(Aluno.class).equalTo("pessoaFisica", pessoaFisica.getId()).findFirst();
        matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();
        turma = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplina = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }

    public void atualizarListaDeNotificacoes() {
        RealmResults<Ocorrencia> ocorrencias = realm.where(Ocorrencia.class).equalTo("matriculaAluno", matricula.getId()).findAll();

        OcorrenciaListaAdapter ocorrenciaListaAdapter = new OcorrenciaListaAdapter(this, ocorrencias);
        notificacoes.setAdapter(ocorrenciaListaAdapter);
    }

    public void atualizarDadosTela() {
        turmaDetalhe.setText(turma.getDescricao());
        alunoDetalhe.setText(pessoaFisica.getNome());
        disciplinaDetalhe.setText(disciplina.getDescricao());
    }
}

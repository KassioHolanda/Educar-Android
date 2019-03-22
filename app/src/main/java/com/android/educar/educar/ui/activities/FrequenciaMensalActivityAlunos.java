package com.android.educar.educar.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.FrequenciaMB;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.utils.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrequenciaMensalActivityAlunos extends AppCompatActivity {

    private ListView alunosFrequencia;
    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;
    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;
    private LinearLayout unidadeFrequecia;
    private LinearLayout turmaFrequencia;
    private LinearLayout disciplinaFrequencia;
    private TextView bimestreFragmentFrequencia;

    private Realm realm;
    private Preferences preferences;
    private List<PessoaFisica> pessoaFisicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencia_alunos);
        setupInit();
        bindind();
        configRealm();
    }



    public void recuperarAlunosRealm() {
        List<Aluno> alunos = new ArrayList<>();

        RealmResults<Matricula> matriculas = realm.where(Matricula.class).equalTo("turma", preferences.getSavedLong("id_turma")).findAll();

        for (Matricula matricula : matriculas) {
            Aluno aluno = realm.where(Aluno.class).equalTo("id", matricula.getAluno()).findFirst();
            if (aluno != null) {
                alunos.add(aluno);
            }
        }

        for (Aluno aluno : alunos) {
            PessoaFisica pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", aluno.getPessoaFisica()).findFirst();
            if (pessoaFisica != null) {
                this.pessoaFisicas.add(pessoaFisica);
            }
        }

        Collections.sort(pessoaFisicas);
    }

    private void atualizarLista(List<PessoaFisica> results) {
        ArrayAdapter<PessoaFisica> alunoArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, results);
        alunosFrequencia.setAdapter(alunoArrayAdapter);
    }


    public void bindind() {
        alunosFrequencia = findViewById(R.id.alunos6_frequencia_recycler_id);
        unidadeSelecionadaAula = findViewById(R.id.unidadeselecionada6_aula_id);
        disciplinaSelecionadaAula = findViewById(R.id.disciplinaselecionada6_aula_id);
        turmaSelecionadaAula = findViewById(R.id.turmaselecionada6_aula_id);
//        salvarFrequencia = view.findViewById(R.id.button_salvarfrequencia_id);
        unidadeFrequecia = findViewById(R.id.frequencia6_unidade_id);
        turmaFrequencia = findViewById(R.id.frequencia6_turma_id);
        disciplinaFrequencia = findViewById(R.id.frequencia6_disciplina_id);
        bimestreFragmentFrequencia = findViewById(R.id.bimestre6_fragment_frequencia_id);
    }

    public void onClick() {
        alunosFrequencia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PessoaFisica pessoaFisica = (PessoaFisica) alunosFrequencia.getItemAtPosition(i);
                preferences.saveLong("id_pessoafisica_aluno", pessoaFisica.getId());
                startActivity(new Intent(getApplicationContext(), FrequenciaMensalActivity.class));
            }
        });
    }

    public void configRealm() {
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
    }

    public void setupInit() {
        preferences = new Preferences(getApplicationContext());
//        frequenciaMB = new FrequenciaMB(getApplicationContext());
        pessoaFisicas = new ArrayList<>();
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getAbreviacao());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
        bimestreFragmentFrequencia.setText(realm.where(Bimestre.class).equalTo("id", preferences.getSavedLong("id_bimestre")).findFirst().getDescricao());
    }

    public void recuperarDadosRealm() {
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong("id_unidade")).findFirst();
        turmaSelecionada = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplinaSelecionada = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarAlunosRealm();
        recuperarDadosRealm();
        atualizarDadosTela();
        atualizarLista(pessoaFisicas);
        onClick();
    }
}

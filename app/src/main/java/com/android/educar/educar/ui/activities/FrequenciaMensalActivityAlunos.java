package com.android.educar.educar.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.model.modelalterado.Disciplina;
import com.android.educar.educar.model.modelalterado.Funcionario;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.model.modelalterado.Unidade;
import com.android.educar.educar.utils.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;

public class FrequenciaMensalActivityAlunos extends AppCompatActivity {

    private ListView alunosFrequencia;
    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;
    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;
//    private LinearLayout unidadeFrequecia;
//    private LinearLayout turmaFrequencia;
//    private LinearLayout disciplinaFrequencia;
    private TextView bimestreFragmentFrequencia;

    private Realm realm;
    private Preferences preferences;
    private List<Matricula> pessoaFisicas;
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencia_alunos);
        configRealm();
        setupInit();
        bindind();
    }



    public void recuperarAlunosRealm() {
        Turma turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst());
        pessoaFisicas = turma.getMatriculas();
        Collections.sort(pessoaFisicas);
    }

    private void atualizarLista(List<Matricula> results) {
        ArrayAdapter<Matricula> alunoArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, results);
        alunosFrequencia.setAdapter(alunoArrayAdapter);
    }


    public void bindind() {
        alunosFrequencia = findViewById(R.id.alunos6_frequencia_recycler_id);
        unidadeSelecionadaAula = findViewById(R.id.unidadeselecionada6_aula_id);
        disciplinaSelecionadaAula = findViewById(R.id.disciplinaselecionada6_aula_id);
        turmaSelecionadaAula = findViewById(R.id.turmaselecionada6_aula_id);
        bimestreFragmentFrequencia = findViewById(R.id.bimestre6_fragment_frequencia_id);
    }

    public void onClick() {
        alunosFrequencia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Matricula matricula = (Matricula) alunosFrequencia.getItemAtPosition(i);
                preferences.saveLong("id_matricula_lista", matricula.getId());
                startActivity(new Intent(getApplicationContext(), FrequenciaMensalActivity.class));
            }
        });
    }

    public void configRealm() {
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
    }

    public void setupInit() {
        funcionario = realm.copyFromRealm(realm.where(Funcionario.class).findFirst());
        preferences = new Preferences(getApplicationContext());
//        frequenciaMB = new FrequenciaMB(getApplicationContext());
        pessoaFisicas = new ArrayList<>();
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getAbreviacao());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
//        bimestreFragmentFrequencia.setText(realm.where(Bimestre.class).equalTo("id", preferences.getSavedLong("id_bimestre")).findFirst().getDescricao());
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

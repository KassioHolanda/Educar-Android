package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
//import com.android.educar.educar.dao.ClassDAO;
//import com.android.educar.educar.dao.DisciplinaDAO;
//import com.android.educar.educar.dao.ProfessorDAO;
//import com.android.educar.educar.dao.TurmaDAO;
//import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.AlunoChamada;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

public class DisciplinaActivity extends AppCompatActivity {

    private Preferences preferences;
    private Messages messages;
    private ListView disciplinas;
    private TextView turmaSelecionaDisciplina;
    private TextView unidadeSelecionadaDisciplina;
    private Turma turmaSelecionada;
    private Unidade unidadeSelecionada;
    private ArrayAdapter<Disciplina> disciplinaArrayAdapter;

    private LinearLayout unidadeDisciplina;
    private LinearLayout turmaDisciplina;

    private AlunoChamada alunoChamada;

    private Realm realm;
    private Set<Disciplina> disciplinasLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disicplina);
        binding();
        setupInit();
        onClickItem();
        configRealm();
        recuperarDadosRealm();
        atualizarDadosTela();
        recuperarDisciplinasRealm();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        syncDados();
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarDadosRealm() {
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong(messages.ID_UNIDADE)).findFirst();
        turmaSelecionada = realm.where(Turma.class).equalTo("id", preferences.getSavedLong(messages.ID_TURMA)).findFirst();
    }

    public void recuperarDisciplinasRealm() {
        RealmResults<GradeCurso> gradeCursos = realm.where(GradeCurso.class).equalTo("professor", preferences.getSavedLong("id_funcionario")).findAll();
        List<SerieDisciplina> serieDisciplinas = new ArrayList<>();

        for (int i = 0; i < gradeCursos.size(); i++) {
            SerieDisciplina serieDisciplina = realm.where(SerieDisciplina.class).equalTo("id", gradeCursos.get(i).getSeriedisciplina()).findFirst();
            if (serieDisciplina != null) {
                serieDisciplinas.add(serieDisciplina);
            }
        }

        for (int i = 0; i < serieDisciplinas.size(); i++) {
            Disciplina disciplina = realm.where(Disciplina.class).equalTo("id", serieDisciplinas.get(i).getDisciplina()).findFirst();
            if (disciplina != null) {
                disciplinasLista.add(disciplina);
            }
        }
    }

    public void binding() {
        disciplinas = findViewById(R.id.disciplinas_list_view_id);
        turmaSelecionaDisciplina = findViewById(R.id.turmaselecionada_disciplina_id);
        unidadeSelecionadaDisciplina = findViewById(R.id.unidadeselecionada_disciplina_id);
        unidadeDisciplina = findViewById(R.id.unidade_disciplina_id);
        turmaDisciplina = findViewById(R.id.turma_disciplina_id);
    }

    public void onClickItem() {
        disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Disciplina disciplina = (Disciplina) disciplinas.getItemAtPosition(position);
                preferences.saveLong("id_disciplina", disciplina.getId());
                nextActivity();
            }
        });

        unidadeDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "" + unidadeSelecionada.getNome(), Snackbar.LENGTH_SHORT).show();
            }
        });

        turmaDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "" + turmaSelecionada.getDescricao(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), AulaAcoesActivity.class));
    }

    public void setupInit() {
        preferences = new Preferences(this);
        messages = new Messages();
        disciplinasLista = new HashSet<>();
        alunoChamada = new AlunoChamada(getApplicationContext());
    }

    public void atualizarAdapterListaDisciplinas() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>(disciplinasLista);
        disciplinaArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, disciplinas);
        this.disciplinas.setAdapter(disciplinaArrayAdapter);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaDisciplina.setText(unidadeSelecionada.getNome());
        turmaSelecionaDisciplina.setText(turmaSelecionada.getDescricao());
    }

    public void alertaInformacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações!");
        builder.setMessage("Selecione uma Disciplina para Continuar!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_infor_aluno:
                alertaInformacao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dados_alunos, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarAdapterListaDisciplinas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void syncDados() {
        alunoChamada.recuperarAlunosMatricula();
    }
}

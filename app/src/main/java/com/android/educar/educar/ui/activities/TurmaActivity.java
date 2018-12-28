package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TurmaActivity extends AppCompatActivity {

    private ListView turmas;
    private Preferences preferences;
    private UtilsFunctions utilsFunctions;
    private Unidade unidadeSelecionada;
    private TextView unidadeSelecionadaTurma;
    private CardView unidade;
    private ArrayAdapter<Turma> turmaArrayAdapter;
    private List<Turma> turmaList;
    private Realm realm;
    private Messages messages;
    private List<GradeCurso> gradeCursos;
    private List<LocalEscola> localEscolas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turma);
        bindind();
        setupInit();
        onClickItem();
        configRealm();
        recuperarDadosRealm();
        atualizarAdapterListaTurmas();
        atualizarDadosTela();
        recuperarTurmas();
        settings();
    }

    public void settings() {
        unidade.setCardElevation(2);
        unidade.setElevation(2);
    }

    public void bindind() {
        turmas = findViewById(R.id.turmas_list_view_id);
        unidadeSelecionadaTurma = findViewById(R.id.unidadeselecionada_turma_id);
        unidade = findViewById(R.id.unidade_turma_id);
    }

    public void recuperarTurmas() {

        RealmResults<LocalEscola> localEscolas = realm.where(LocalEscola.class).equalTo("unidade", preferences.getSavedLong("id_unidade")).findAll();
        List<Turma> turmasEscola = new ArrayList<>();

        for (int i = 0; i < localEscolas.size(); i++) {
            Turma turma = realm.where(Turma.class).equalTo("sala", localEscolas.get(i).getId()).equalTo("statusTurma", "CADASTRADA").findFirst();
            if (turma != null) {
                turmasEscola.add(turma);
            }
        }

        for (int i = 0; i < turmasEscola.size(); i++) {

            long idturrmaescola = turmasEscola.get(i).getId();
            long idprofessor = preferences.getSavedLong("id_funcionario");

            GradeCurso gradeCursos = realm.where(GradeCurso.class)
                    .equalTo("turma", turmasEscola.get(i).getId())
                    .equalTo("professor", preferences.getSavedLong("id_funcionario")).findFirst();

            if (gradeCursos != null) {
                turmaList.add(realm.where(Turma.class).equalTo("id", turmasEscola.get(i).getId()).findFirst());
            }
        }
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarDadosRealm() {
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong(messages.ID_UNIDADE)).findFirst();
    }

    public void setupInit() {
        turmaList = new ArrayList<>();
        preferences = new Preferences(this);
        utilsFunctions = new UtilsFunctions();
        messages = new Messages();
        gradeCursos = new ArrayList<>();
        localEscolas = new ArrayList<>();
    }

    public void onClickItem() {
        turmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Turma turma = (Turma) turmas.getItemAtPosition(position);
                preferences.saveLong("id_turma", turma.getId());
                preferences.saveLong("id_anoletivo", turma.getAnoLetivo());
                preferences.saveLong("id_serie", turma.getSerie());
                nextAcitivity();
            }
        });

        unidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "" + unidadeSelecionada.getNome(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void nextAcitivity() {
        startActivity(new Intent(getApplicationContext(), DisciplinaActivity.class));
    }

    public void atualizarAdapterListaTurmas() {
        turmaArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, turmaList);
        this.turmas.setAdapter(turmaArrayAdapter);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaTurma.setText(unidadeSelecionada.getNome());
    }

    public void alertaInformacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações!");
        builder.setMessage("Selecione uma Turma para Continuar!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public void alertaInformacaoSemTurmas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações!");
        builder.setMessage("Você não possui Turmas!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
}

package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.NotaMB;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.AlunoChamada;
import com.android.educar.educar.network.chamadas.OcorrenciaChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Preferences;

import io.realm.Realm;

public class AulaAcoesActivity extends AppCompatActivity {
    private TextView unidade;
    private TextView disciplina;
    private TextView turma;
    private TextView serieacoes;
    private TextView bimestre;
    private Preferences preferences;

    private LinearLayout paginaNotas;
    private LinearLayout paginaOcorrencia;
    private LinearLayout paginaFrequenciaMensal;
    private LinearLayout paginaFrequencia;
    private FloatingActionButton floatingActionButton;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_acoes);
        binding();
        setupInit();
        settings();
        clickOnItem();
    }

    public void setupInit() {
        preferences = new Preferences(getApplicationContext());
    }


    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void atualizarDados() {
        unidade.setText(realm.where(Unidade.class).equalTo("id", preferences.getSavedLong("id_unidade")).findFirst().getNome());
        turma.setText(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst().getDescricao());
        disciplina.setText(realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst().getDescricao());
        try {
            serieacoes.setText(realm.where(Serie.class).equalTo("id", preferences.getSavedLong("id_serie")).findFirst().getDescricao());
        } catch (Exception e) {
            serieacoes.setText("");
        }

        bimestre.setText(realm.where(Bimestre.class).equalTo("id", new NotaMB(getApplicationContext()).verificarBimestreAtual()).findFirst().getDescricao());
    }

    public void clickOnItem() {
        paginaFrequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FrequenciaActivity.class));
            }
        });

        paginaNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotaFragmentActivity.class));
            }
        });

        paginaOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OcorrenciaActivity.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UnidadeActivity.class));
            }
        });

        paginaFrequenciaMensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FrequenciaMensalActivityAlunos.class));
            }
        });
    }

    public void binding() {
        unidade = findViewById(R.id.unidade_id);
        bimestre = findViewById(R.id.bimestre_acoes_id);
        turma = findViewById(R.id.turma_id);
        disciplina = findViewById(R.id.disciplina_id);
        paginaFrequencia = findViewById(R.id.frequencias_diaria_id);
        paginaFrequenciaMensal = findViewById(R.id.frequencias_mensal_id);
        paginaNotas = findViewById(R.id.notas_id);
        paginaOcorrencia = findViewById(R.id.ocorrencias_id);
        serieacoes = findViewById(R.id.serieacoes_id);
        floatingActionButton = findViewById(R.id.fab_home_id);
    }

    public void settings() {
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setTitle("2019");
    }

    public void alertaInformacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações!");
        builder.setMessage("Selecione uma Opção para Continuar!");
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
        configRealm();
        atualizarDados();
    }
}

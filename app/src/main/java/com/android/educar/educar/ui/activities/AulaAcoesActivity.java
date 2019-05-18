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
import com.android.educar.educar.mb.BimestreMB;
import com.android.educar.educar.mb.NotaMB;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.AlunoChamada;
import com.android.educar.educar.network.chamadas.OcorrenciaChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Messages;
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
    private LinearLayout linearSerie;

    private Realm realm;

    private Funcionario funcionario;
    private Unidade unidadeSelecionada;
    private Turma turmaSelecionada;
    private Disciplina disciplinaSelecionada;
    private Serie serieSelecionada;
    private Bimestre bimestreSelecionado;

    private BimestreMB bimestreMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula_acoes);
        binding();
        configRealm();
        setupInit();
        settings();
        clickOnItem();
    }


    @Override
    protected void onResume() {
        super.onResume();
        recuperarUnidade();
        recuperarTurma();
        recuperarDisciplina();
        atualizarDados();
    }

    public void setupInit() {
        preferences = new Preferences(getApplicationContext());
        funcionario = realm.copyFromRealm(realm.where(Funcionario.class).findFirst());
        bimestreMB = new BimestreMB(getApplicationContext());
        bimestreSelecionado = realm.copyFromRealm(realm.where(Bimestre.class).equalTo("id", bimestreMB.getBimestreAtual()).findFirst());
    }


    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarUnidade() {
        for (int i = 0; i < funcionario.getFuncionarioEscolas().size(); i++) {
            if (funcionario.getFuncionarioEscolas().get(i).getUnidade().getId() == preferences.getSavedLong(Messages.ID_UNIDADE) && funcionario.getFuncionarioEscolas().get(i).getAtivo()) {
                this.unidadeSelecionada = funcionario.getFuncionarioEscolas().get(i).getUnidade();
            }
        }
    }

    public void recuperarTurma() {
        for (int i = 0; i < unidadeSelecionada.getLocalEscolas().size(); i++) {
            for (int j = 0; j < unidadeSelecionada.getLocalEscolas().get(i).getTurmas().size(); j++) {
                if (unidadeSelecionada.getLocalEscolas().get(i).getTurmas().get(j).getId() == preferences.getSavedLong(Messages.ID_TURMA)) {
                    this.turmaSelecionada = (unidadeSelecionada.getLocalEscolas().get(i).getTurmas().get(j));
                }
            }
        }
    }

    public void recuperarDisciplina() {
        for (int i = 0; i < turmaSelecionada.getGradeCursos().size(); i++) {
            try {
                if (turmaSelecionada.getGradeCursos().get(i).getSeriedisciplina().getDisciplina().getId() == preferences.getSavedLong("id_disciplina")) {
                    disciplinaSelecionada = turmaSelecionada.getGradeCursos().get(i).getDisciplina();
                } else if (turmaSelecionada.getGradeCursos().get(i).getSeriedisciplina().getSerie().getId() == preferences.getSavedLong("id_serie")) {
                    serieSelecionada = turmaSelecionada.getGradeCursos().get(i).getSeriedisciplina().getSerie();
                }
            } catch (NullPointerException e) {

            }
            if (turmaSelecionada.getGradeCursos().get(i).getDisciplina().getId() == preferences.getSavedLong("id_disciplina")) {
                disciplinaSelecionada = turmaSelecionada.getGradeCursos().get(i).getDisciplina();
            }
        }
    }

    public void atualizarDados() {
        unidade.setText(unidadeSelecionada.getNome());
        turma.setText(turmaSelecionada.getDescricao());
        disciplina.setText(disciplinaSelecionada.getDescricao());
        try {
            serieacoes.setText(serieSelecionada.getDescricao());
        } catch (Exception e) {
            linearSerie.setVisibility(View.GONE);
            serieacoes.setText("");
        }
        bimestre.setText(bimestreSelecionado.getDescricao());
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
        linearSerie = findViewById(R.id.serie_acoes_id);
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


}

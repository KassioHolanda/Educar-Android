package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.FrequenciaAdapterLista;
import com.android.educar.educar.adapter.TurmaAdapter;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.DisciplinaChamada;
import com.android.educar.educar.network.chamadas.MatriculaChamada;
import com.android.educar.educar.network.chamadas.SerieChamada;
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
    private Unidade unidadeSelecionada;
    private TextView unidadeSelecionadaTurma;
    private CardView unidade;
    private TurmaAdapter turmaArrayAdapter;
    private List<Turma> turmaList;
    private Realm realm;
    private Messages messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turma);
        bindind();
        setupInit();
        onClickItem();
        settings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configRealm();
        recuperarDadosRealm();
        atualizarDadosTela();
        recuperarTurmas();
        atualizarAdapterListaTurmas();
        mensagemInicial();
    }

    public void settings() {
        unidade.setCardElevation(2);
        unidade.setElevation(2);
    }

    public void mensagemInicial() {
        Toast.makeText(getApplicationContext(), "Selecione uma Turma para Continuar!", Toast.LENGTH_LONG).show();
    }

    public void bindind() {
        turmas = findViewById(R.id.turmas_list_view_id);
        unidadeSelecionadaTurma = findViewById(R.id.unidadeselecionada_turma_id);
        unidade = findViewById(R.id.unidade_turma_id);
    }

    public void recuperarTurmas() {
        RealmResults<LocalEscola> localEscolas = realm.where(LocalEscola.class).equalTo("unidade", preferences.getSavedLong("id_unidade"))
                .notEqualTo("descricao", "LOCAL MIGRACAO")
                .notEqualTo("descricao", "DEPOSITO DE ALIMENTOS")
                .notEqualTo("descricao", "CANTINA")
                .notEqualTo("descricao", "BANHEIRO MASCULINO")
                .notEqualTo("descricao", "BANHEIRO FEMININO")
                .findAll();
        List<Turma> turmasEscola = new ArrayList<>();

        for (LocalEscola localEscola : localEscolas) {
            List<Turma> turmas = realm.where(Turma.class).equalTo("sala", localEscola.getId())
                    .equalTo("statusTurma", "CADASTRADA")
                    .notEqualTo("nivel", "INFANTIL").findAll();

            for (Turma turma : turmas) {
                turmasEscola.add(turma);
            }

        }

        for (Turma turma : turmasEscola) {
            List<GradeCurso> gradeCursos = realm.where(GradeCurso.class)
                    .equalTo("turma", turma.getId())
                    .equalTo("professor", preferences.getSavedLong("id_funcionario")).findAll();
            if (gradeCursos.size() > 0) {
                turmaList.add(realm.where(Turma.class).equalTo("id", turma.getId()).findFirst());
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
        messages = new Messages();
    }

    public void onClickItem() {
        turmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Turma turma = (Turma) turmas.getItemAtPosition(position);
                preferences.saveLong("id_turma", turma.getId());
                preferences.saveLong("id_serie", turma.getSerie());
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
        Intent intent = new Intent(getApplicationContext(), DisciplinaActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(TurmaActivity.this, R.anim.mover_esquerda, R.anim.fade_out);
        ActivityCompat.startActivity(TurmaActivity.this, intent, activityOptionsCompat.toBundle());
    }

    public void atualizarAdapterListaTurmas() {
        if (turmaList.size() == 0) {
            alertaInformacaoSemTurmas();
        }

        turmaArrayAdapter = new TurmaAdapter(getApplicationContext(), turmaList);
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
        builder.setMessage("Esta Unidade não possui Turmas!");
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

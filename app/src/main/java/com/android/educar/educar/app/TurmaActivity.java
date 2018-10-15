package com.android.educar.educar.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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
    }

    public void bindind() {
        turmas = findViewById(R.id.turmas_list_view_id);
        unidadeSelecionadaTurma = findViewById(R.id.unidadeselecionada_turma_id);
        unidade = findViewById(R.id.unidade_turma_id);
    }

    public void recuperarTurmas() {

        RealmResults<LocalEscola> localEscolas = realm.where(LocalEscola.class).equalTo("unidade", preferences.getSavedLong("id_unidade")).findAll();

        long idUnidade = preferences.getSavedLong("id_unidade");

        List<Turma> turmasEscola = new ArrayList<>();

        for (int i = 0; i < localEscolas.size(); i++) {
            Turma turma = realm.where(Turma.class).equalTo("sala", localEscolas.get(i).getId()).findFirst();
            if (turma != null) {
                turmasEscola.add(turma);
            }
        }

        for (int i = 0; i < turmasEscola.size(); i++) {
            GradeCurso gradeCursos = realm.where(GradeCurso.class)
                    .equalTo("turma", turmasEscola.get(i).getId())
                    .equalTo("professor", preferences.getSavedLong("id_funcionario")).findFirst();

            long idturma = turmasEscola.get(i).getId();
            long idfuncionario = preferences.getSavedLong("id_funcionario");

            if (gradeCursos != null) {
                turmaList.add(realm.where(Turma.class).equalTo("id", turmasEscola.get(i).getId()).findFirst());
            }
        }
    }

//        if (gradeCursos.size() == 0) {
//            alertaInformacaoSemTurmas();
//        }


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
                nextAcitivity();
            }
        });

        unidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, ""+unidadeSelecionada.getNome(), Snackbar.LENGTH_SHORT).show();
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
        unidadeSelecionadaTurma.setText(unidadeSelecionada.getAbreviacao());
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

        getMenuInflater().inflate(R.menu.menu_search, menu);
        getMenuInflater().inflate(R.menu.menu_dados_alunos, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                turmaArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

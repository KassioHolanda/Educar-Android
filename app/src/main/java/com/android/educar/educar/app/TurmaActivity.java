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

import com.android.educar.educar.R;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class TurmaActivity extends AppCompatActivity {

    private ListView turmas;
    private Preferences preferences;
    private APIService apiService;
    private UtilsFunctions utilsFunctions;
    private MaterialSearchView materialSearchView;
    private Unidade unidadeSelecionada;
    private TextView unidadeSelecionadaTurma;
    private ClassDAO classDAO;
    private CardView unidade;
    private ArrayAdapter<Turma> turmaArrayAdapter;

    private UnidadeDAO unidadeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turma);
        bindind();
        setupInit();
        onClickItem();
        atualizarDadosTela();
    }

    public void bindind() {
        turmas = findViewById(R.id.turmas_list_view_id);
        materialSearchView = findViewById(R.id.search_viewturma_id);
        unidadeSelecionadaTurma = findViewById(R.id.unidadeselecionada_turma_id);
        unidade = findViewById(R.id.unidade_turma_id);
    }

    public void setupInit() {
        unidadeDAO = new UnidadeDAO(getApplicationContext());
        preferences = new Preferences(this);
        apiService = new APIService("");
        utilsFunctions = new UtilsFunctions();
        classDAO = new ClassDAO(getApplicationContext());
        unidadeSelecionada = unidadeDAO.selecionarUnidade(preferences.getSavedLong("id_unidade"));
        atualizarAdapterListaTurmas(classDAO.turmasUnidade(preferences.getSavedLong("id_unidade")));
    }

    public void onClickItem() {
        turmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Turma turma = (Turma) turmas.getItemAtPosition(position);
                preferences.saveLong("id_turma", turma.getPk());
                nextAcitivity();
            }
        });

        unidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, ""+unidadeSelecionada.getNomeUnidade(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void nextAcitivity() {
        startActivity(new Intent(getApplicationContext(), DisciplinaActivity.class));
    }

    public void atualizarAdapterListaTurmas(List<Turma> turmas) {
        turmaArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, turmas);
        this.turmas.setAdapter(turmaArrayAdapter);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaTurma.setText(unidadeSelecionada.getNomeUnidade());
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

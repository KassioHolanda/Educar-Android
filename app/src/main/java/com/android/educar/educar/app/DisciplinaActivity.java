package com.android.educar.educar.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.DisciplinaDAO;
import com.android.educar.educar.dao.ProfessorDAO;
import com.android.educar.educar.dao.TurmaDAO;
import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.helpers.DisciplinaEndPoint;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class DisciplinaActivity extends AppCompatActivity {

    private APIService apiService;
    private Preferences preferences;
    private UtilsFunctions utilsFunctions;
    private Messages messages;
    private ListView disciplinas;
    private TextView turmaSelecionaDisciplina;
    private TextView unidadeSelecionadaDisciplina;
    private Turma turmaSelecionada;
    private Unidade unidadeSelecionada;
    private Professor professorLogado;
    //    private ClassDAO classDAO;
    private List<Disciplina> disciplinasList;
    private ArrayAdapter<Disciplina> disciplinaArrayAdapter;

    private LinearLayout unidadeDisciplina;
    private LinearLayout turmaDisciplina;

    private UnidadeDAO unidadeDAO;
    private DisciplinaDAO disciplinaDAO;
    private ProfessorDAO professorDAO;
    private TurmaDAO turmaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disicplina);
        binding();
        setupInit();
        onClickItem();
        atualizarDadosTela();
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
                preferences.saveLong("id_disciplina", disciplina.getPk());
                nextActivity();
            }
        });

        unidadeDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "" + unidadeSelecionada.getNomeUnidade(), Snackbar.LENGTH_SHORT).show();
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
//        classDAO = new ClassDAO(getApplicationContext());

        professorDAO = new ProfessorDAO(getApplicationContext());
        unidadeDAO = new UnidadeDAO(getApplicationContext());
        turmaDAO = new TurmaDAO(getApplicationContext());
        disciplinaDAO = new DisciplinaDAO(getApplicationContext());

        apiService = new APIService("");
        utilsFunctions = new UtilsFunctions();
        messages = new Messages();
        recuperarDados();
        atualizarAdapterListaDisciplinas(disciplinasList);
    }

    public void recuperarDados() {
        professorLogado = professorDAO.selecionarProfessor(preferences.getSavedLong("id_usuario"));
        unidadeSelecionada = unidadeDAO.selecionarUnidade(preferences.getSavedLong("id_unidade"));
        turmaSelecionada = turmaDAO.selecionarTurma(preferences.getSavedLong("id_turma"));
        disciplinasList = disciplinaDAO.selecionarDisciplinasProfessor(preferences.getSavedLong("id_usuario"));
    }

    public void atualizarAdapterListaDisciplinas(List<Disciplina> disciplinas) {
        disciplinaArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, disciplinas);
        this.disciplinas.setAdapter(disciplinaArrayAdapter);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaDisciplina.setText(unidadeSelecionada.getNomeUnidade());
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
                disciplinaArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

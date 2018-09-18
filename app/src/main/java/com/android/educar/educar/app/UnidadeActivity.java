package com.android.educar.educar.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.ProfessorDAO;
import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import android.app.ProgressDialog;


import java.util.ArrayList;
import java.util.List;

public class UnidadeActivity extends AppCompatActivity {

    private ClassDAO classDAO;
    private UnidadeDAO unidadeDAO;
    private ListView unidades;
    private Preferences preferences;
    private Messages messages;
    private List<Unidade> unidadesList;
    private ArrayAdapter<Unidade> unidadeArrayAdapter;
    private ProgressDialog progressDialog;

    private ProfessorDAO professorDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);
        bindind();
        setupInit();
        onClickItem();
        atualizarAdapterListaUnidades(unidadesList);

//        bemVindo();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void bemVindo() {
        Toast.makeText(getApplicationContext(), "Bem Vindo " + professorDAO.selecionarProfessor(preferences.getSavedLong(messages.ID_USUARIO)).getNome(), Toast.LENGTH_SHORT).show();
    }

    public void bindind() {
        unidades = findViewById(R.id.unidades_list_view_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        getMenuInflater().inflate(R.menu.menu_item_configuracoes, menu);
        getMenuInflater().inflate(R.menu.menu_item_sair, menu);
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
                unidadeArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_infor_aluno:
                alertaInformacao();
                break;
            case R.id.configuracoes_id:
                startActivity(new Intent(getApplicationContext(), ConfiguracoesActivity.class));
                break;
            case R.id.sair_id:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                onStop();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickItem() {
        unidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Unidade unidade = (Unidade) unidades.getItemAtPosition(position);
                preferences.saveLong(messages.ID_UNIDADE, unidade.getPk());
                nextAcivity();
            }
        });
    }

    public void nextAcivity() {
        startActivity(new Intent(getApplicationContext(), TurmaActivity.class));
    }

    public void setupInit() {
        professorDAO = new ProfessorDAO(getApplicationContext());
        progressDialog = UtilsFunctions.progressDialog(this, "Aguarde...");
        unidadeDAO = new UnidadeDAO(getApplicationContext());
        classDAO = new ClassDAO(getApplicationContext());
        preferences = new Preferences(this);
        messages = new Messages();
        unidadesList = new ArrayList<>();
        unidadesList = unidadeDAO.unidades();
    }

    public void atualizarAdapterListaUnidades(List<Unidade> unidades) {
        unidadeArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, unidades);
        this.unidades.setAdapter(unidadeArrayAdapter);
    }

    public void alertaInformacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações!");
        builder.setMessage("Selecione uma Unidade para Continuar!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}

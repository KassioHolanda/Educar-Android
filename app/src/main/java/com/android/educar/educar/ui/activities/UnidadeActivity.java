package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
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

import com.android.educar.educar.R;
import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.mb.SincronizarComAPiMB;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.TurmaChamada;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import android.app.ProgressDialog;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UnidadeActivity extends AppCompatActivity {

    private ListView unidades;
    private Preferences preferences;
    private Messages messages;
    private List<Unidade> unidadesList;
    private ArrayAdapter<Unidade> unidadeArrayAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton sincronizarDados;
    private Realm realm;
    private RealmObjectsBO realmObjectsBO;
    private SincronizarComAPiMB sincronizarComAPiMB;

    private TurmaChamada turmaChamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);
        bindind();
        setupInit();
        configRealm();
        onClickItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarAdapterListaUnidades();
        sync();
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.refresh();
    }

    public void bindind() {
        unidades = findViewById(R.id.unidades_list_view_id);
        sincronizarDados = findViewById(R.id.sincronizar_dados);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_sair, menu);
        getMenuInflater().inflate(R.menu.menu_dados_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_infor_aluno:
                alertaInformacao();
                break;
            case R.id.sair_id:
                preferences.saveBoolean("logado", false);
                preferences.saveBoolean("sync", false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                deletarBancoRealm();
                onStop();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deletarBancoRealm() {
        realm.deleteAll();
    }

    public void onClickItem() {
        unidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Unidade unidade = (Unidade) unidades.getItemAtPosition(position);
                preferences.saveLong(messages.ID_UNIDADE, unidade.getId());
                nextAcivity();
            }
        });

        sincronizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsFunctions.isConnect(getApplicationContext())) {
                    sincronizarComAPiMB.sincronizarRealmComAPi();
                    sincronizarComAPiMB.sincronizarAPiComRealm();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "SEM CONXÃO", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

//    public void syncInit() {
//        if (UtilsFunctions.isConnect(getApplicationContext())) {
//            sincronizarComAPiMB.sincronizarRealmComAPi();
//            sincronizarComAPiMB.sincronizarAPiComRealm();
//        }
//    }

    public void nextAcivity() {
        startActivity(new Intent(getApplicationContext(), TurmaActivity.class));
    }

    public void setupInit() {
        progressBar = new ProgressBar(getApplicationContext());
        preferences = new Preferences(this);
        messages = new Messages();
        sincronizarComAPiMB = new SincronizarComAPiMB(getApplicationContext());
        unidadesList = new ArrayList<>();
        realmObjectsBO = new RealmObjectsBO(getApplicationContext());
        preferences.saveBoolean("sync", true);
        turmaChamada = new TurmaChamada(getApplicationContext());
    }

    public void atualizarAdapterListaUnidades() {
        List<Unidade> unidades = realm.where(Unidade.class).findAll();
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

    public void sync() {
        turmaChamada.recuperarTurmasUnidade();
        turmaChamada.recuperarGradeCursoTurma(preferences.getSavedLong("id_funcionario"));
    }

}

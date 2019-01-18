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
import com.android.educar.educar.network.chamadas.SerieChamada;
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
    private ArrayAdapter<Unidade> unidadeArrayAdapter;
    private FloatingActionButton sincronizarDados;
    private Realm realm;
    private SincronizarComAPiMB sincronizarComAPiMB;
    private SerieChamada serieChamada;

    private TurmaChamada turmaChamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);
        bindind();
        setupInit();
        onClickItem();
    }

    public void mensagemInicial() {
        Toast.makeText(getApplicationContext(), "Selecione uma Turma para Continuar...", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configRealm();
        atualizarAdapterListaUnidades();
        sync();
        mensagemInicial();
        alertaInformacaoPrimeiraUtilizacao();
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
                alertaInformacaoSincronizar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deletarBanco() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        preferences.limpar();
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
                    Snackbar.make(findViewById(android.R.id.content), "SEM CONEXÃO", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void nextAcivity() {
        startActivity(new Intent(getApplicationContext(), TurmaActivity.class));
    }

    public void setupInit() {
        preferences = new Preferences(this);
        messages = new Messages();
        sincronizarComAPiMB = new SincronizarComAPiMB(getApplicationContext());
        preferences.saveBoolean("sync", true);
        turmaChamada = new TurmaChamada(getApplicationContext());
        serieChamada = new SerieChamada(getApplicationContext());
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
//        if (!preferences.getSavedBoolean("sync_unidade")) {
        turmaChamada.recuperarTurmasUnidade();
        turmaChamada.recuperarGradeCursoTurma(preferences.getSavedLong("id_funcionario"));
//        serieChamada.recuperarSerieAPI();
        preferences.saveBoolean("sync_unidade", true);
//        }
    }

    public void alertaInformacaoSincronizar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta!");
        builder.setMessage("Antes de Sair sincronize seus dados, ao sair seus dadaos serão apagados!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmarSaidaUsuario();
            }
        }).show();
    }

    public void confirmarSaidaUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta!");
        builder.setMessage("Você deseja realmente sair?");
        builder.setCancelable(false);
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                deletarBanco();
                onStop();
            }
        }).setNegativeButton("NÂO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    public void alertaInformacaoPrimeiraUtilizacao() {
        if (!preferences.getSavedBoolean("alerta_info_primeiro_acesso")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alerta!");
            builder.setMessage("Certifique-se de manter internet na primeira utilização!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
            preferences.saveBoolean("alerta_info_primeiro_acesso", true);
        }
    }
}

package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.network.chamadas.SerieChamada;
import com.android.educar.educar.network.chamadas.TurmaChamada;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import android.app.ProgressDialog;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private TextView professorLogado;
    private ProgressDialog progressDialog;

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
        settings();
        atualizarAdapterListaUnidades();
        mensagemInicial();
        alertaInformacaoPrimeiraUtilizacao();
        atualizarDadosTela();
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.refresh();
    }

    public void settings() {
//        getSupportActionBar().setTitle(Html.fromHtml("Unidades"));
    }

    public void bindind() {
        unidades = findViewById(R.id.unidades_list_view_id);
        sincronizarDados = findViewById(R.id.sincronizar_dados);
        professorLogado = findViewById(R.id.professorlogado_id);
    }

    public void atualizarDadosTela() {
        professorLogado.setText(realm.where(PessoaFisica.class).equalTo("id", preferences.getSavedLong("id_pessoafisica")).findFirst().getNome());
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
                    progressDialog.show();
                    sincronizarComAPiMB.recuperarDadosDaAPISalvarBancoDeDadosRealm();
                    sincronizarComAPiMB.enviarDadosDoBancoDeDadosParaAPI();
                    progressDialog.hide();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "SEM CONEXÃO", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void nextAcivity() {
        Intent intent = new Intent(getApplicationContext(), TurmaActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(UnidadeActivity.this, R.anim.mover_esquerda, R.anim.fade_out);
        ActivityCompat.startActivity(UnidadeActivity.this, intent, activityOptionsCompat.toBundle());
    }

    public void setupInit() {
        preferences = new Preferences(this);
        messages = new Messages();
        sincronizarComAPiMB = new SincronizarComAPiMB(getApplicationContext());
        progressDialog = UtilsFunctions.progressDialog(this, "Carregando...");
    }

    public void atualizarAdapterListaUnidades() {
        List<Unidade> unidades = realm.where(Unidade.class).sort("nome").findAll();
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
            builder.setMessage("Seus Dados estão Atualizados!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
            preferences.saveBoolean("alerta_info_primeiro_acesso", true);
        }
    }
}

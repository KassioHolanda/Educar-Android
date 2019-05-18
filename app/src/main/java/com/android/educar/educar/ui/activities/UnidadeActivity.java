package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.SincronizarComAPiMB;
import com.android.educar.educar.model.AnoLetivo;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.chamadas.AnoLetivoChamada;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaTurmaAPI;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import android.app.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeActivity extends AppCompatActivity {

    private ListView unidades;
    private Preferences preferences;
    private Messages messages;
    private ArrayAdapter<Unidade> unidadeArrayAdapter;
    private FloatingActionButton sincronizarDados;
    private Realm realm;
    private Funcionario funcionario;
    private APIService apiService;
    private SincronizarComAPiMB sincronizarComAPiMB;
    private TextView professorLogado;
    private ProgressDialog progressDialog;
    private List<Unidade> listaDeUnidades;
    private AnoLetivoChamada anoLetivoChamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);
        bindind();
        configRealm();
        setupInit();
        onClickItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarDadosIndependentesAPI();
        verificarPrimeiroAcesso();
    }

    public void recuperarDadosIndependentesAPI() {
//        if (realm.where(AnoLetivo.class).findAll().size() == 0 && realm.where(Bimestre.class).findAll().size() == 0) {
        anoLetivoChamada.recuperarBimestreAPI();
        anoLetivoChamada.recuperarAlunoLetivoAPI(1);
//        }
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void bindind() {
        unidades = findViewById(R.id.unidades_list_view_id);
        sincronizarDados = findViewById(R.id.sincronizar_dados);
        professorLogado = findViewById(R.id.professorlogado_id);
    }

    public void atualizarDadosTela() {
        professorLogado.setText(funcionario.getPessoaFisica().getNome());
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
//                    progressDialog.show();
//                    sincronizarComAPiMB.recuperarDadosDaAPISalvarBancoDeDadosRealm();
//                    sincronizarComAPiMB.enviarDadosDoBancoDeDadosParaAPI();
//                    recuperarDadosFuncionario();
//                    progressDialog.hide();
                    startActivity(new Intent(getApplicationContext(), SincronizacaoActivity.class));
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
        this.funcionario = new Funcionario();
        apiService = new APIService("");
        preferences = new Preferences(this);
        messages = new Messages();
        sincronizarComAPiMB = new SincronizarComAPiMB(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Carregando");
        progressDialog.setCancelable(false);
//        progressDialog.setIcon(R.drawable.sync);
        progressDialog.setMessage("Recuperando seus dados...");
        anoLetivoChamada = new AnoLetivoChamada(getApplicationContext());
    }

    public void atualizarAdapterListaUnidades() {
        unidadeArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, this.listaDeUnidades);
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
        builder.setMessage("Antes de sair sincronize seus dados, ao sair seus dadaos serão apagados!");
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


    public void recuperarDadosFuncionario() {
        progressDialog.show();
        Call<List<Funcionario>> funcionarioCall = apiService.getCorpoEndPoint().getFuncionario(preferences.getSavedString("cpf"));
        funcionarioCall.enqueue(new Callback<List<Funcionario>>() {
            @Override
            public void onResponse(Call<List<Funcionario>> call, Response<List<Funcionario>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        recuperarFuncionario(response.body().get(0));
                    }
                }
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Funcionario>> call, Throwable t) {
                progressDialog.hide();
                Log.i("erro_api", t.getMessage());
//                Snackbar.make(findViewById(android.R.id.content), "Ocorreu um Erro, Solicite Administrador.", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                }).show();
            }
        });
    }

    public void novaThreadTurma() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                recuperarDadosTurmas();
            }
        }).start();
    }

    public void recuperarDadosTurmas() {
//        progressDialog.setMessage("Recuperando turmas");
        for (FuncionarioEscola funcionarioEscola : this.funcionario.getFuncionarioEscolas()) {
            for (LocalEscola localEscola : funcionarioEscola.getUnidade().getLocalEscolas()) {
                for (Turma turma : localEscola.getTurmas()) {
                    recuperarTurmas(turma);
                }
            }
        }
//        progressDialog.hide();
    }

    public void recuperarTurmas(Turma turma) {
//        progressDialog.show();
        progressDialog.setMessage("Recuperando Turmas");
        Call<Turma> listaTurmaAPICall = apiService.getTurmaEndPoint().getTurmaCompleta(turma.getId());
        listaTurmaAPICall.enqueue(new Callback<Turma>() {
            @Override
            public void onResponse(Call<Turma> call, Response<Turma> response) {
                if (response.isSuccessful()) {
                    recuperarTurma(response.body(), turma.getId());
                }
                if (response.code() == 500) {
                    Log.i("erro_api", response.message());
                }

//                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Turma> call, Throwable t) {
//                progressDialog.hide();
                Log.i("erro_api", t.getMessage());
//                Snackbar.make(findViewById(android.R.id.content), "Ocorreu um Erro, Solicite Administrador, ao salvar as turmas.", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                }).show();
            }
        });
    }


    private void recuperarTurma(Turma body, Long turmaId) {
        realm.beginTransaction();
        Turma turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", turmaId).findFirst());
        turma.setMatriculas(body.getMatriculas());
        realm.copyToRealmOrUpdate(turma);
        realm.commitTransaction();
    }

    private void recuperarFuncionario(Funcionario body) {
        this.funcionario = body;

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this.funcionario);
        realm.commitTransaction();

        novaThreadTurma();
        atualizarDadosTela();
        recuperarUnidadesFuncionario();
        atualizarAdapterListaUnidades();
//        alertaInformacaoPrimeiraUtilizacao();
    }

    public void recuperarUnidadesFuncionario() {
        this.listaDeUnidades = new ArrayList<>();
        for (FuncionarioEscola funcionarioEscola : funcionario.getFuncionarioEscolas()) {
            this.listaDeUnidades.add(funcionarioEscola.getUnidade());
        }
    }

    public void verificarPrimeiroAcesso() {
        if (!preferences.getSavedBoolean("alerta_info_primeiro_acesso")) {
            recuperarDadosFuncionario();
            preferences.saveBoolean("alerta_info_primeiro_acesso", true);
        } else {
            Funcionario f = realm.where(Funcionario.class).findFirst();
            this.funcionario = realm.copyFromRealm(f);
            atualizarDadosTela();
            recuperarUnidadesFuncionario();
            atualizarAdapterListaUnidades();
        }
    }
}

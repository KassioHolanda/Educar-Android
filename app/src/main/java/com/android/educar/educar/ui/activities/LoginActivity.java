package com.android.educar.educar.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.FuncionarioMB;
import com.android.educar.educar.mb.PessoaFisicaMB;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.realm.Realm;
import io.realm.internal.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button acessarLogin;
    private Preferences preferences;
    private TextView cpf;
    private TextView senha;
    private Realm realm;
    private PessoaFisica pessoaFisica;
    private Funcionario funcionario;
    private Usuario usuario;
    private Perfil perfil;
    private Messages messages;
    private int statusLoading;
    private ProgressBar progressBar;

    private PessoaFisicaMB pessoaFisicaMB;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.new_page, R.anim.old_page);
        binding();
        setupInit();
        onClickItem();
        settings();
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public void limpar() {
        cpf.setText("");
        senha.setText("");
    }

    public void verificarUsuarioLogado() {
        if (preferences.getSavedBoolean("logado")) {
            nextActivity();
        }
    }

    public void settings() {
        getSupportActionBar().hide();
//        progressBar.setVisibility(View.INVISIBLE);
    }

    public void setupInit() {
        messages = new Messages();
        progressBar = new ProgressBar(getApplicationContext());
        preferences = new Preferences(this);
        pessoaFisica = new PessoaFisica();
        usuario = new Usuario();
        funcionario = new Funcionario();
        perfil = new Perfil();
        pessoaFisicaMB = new PessoaFisicaMB(getApplicationContext());
        handler = new Handler();
        statusLoading = 0;
    }

    private void binding() {
        this.acessarLogin = findViewById(R.id.acessar_login_id);
        this.cpf = findViewById(R.id.cpf_login_id);
        this.senha = findViewById(R.id.senha_login_id);
    }

    private void onClickItem() {
        this.acessarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempoParaCarregarDadosDeLoging();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        configRealm();
        verificarUsuarioLogado();
        verificarConexao();
        limpar();
    }

    public void consultarUsuario() {
        if (preferences.getSavedBoolean("usuario_encontrado")) {
            consultarSenha();
        } else {
            Toast.makeText(getApplicationContext(), "Usuário não Encontrado!", Toast.LENGTH_SHORT).show();
            mostrarCamposDeLogin();
        }
    }

    public void tempoParaCarregarDadosDeLoging() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setVisibility(View.VISIBLE);
//                        cpf.setVisibility(View.INVISIBLE);
//                        acessarLogin.setVisibility(View.INVISIBLE);
//                        senha.setVisibility(View.INVISIBLE);
                        pessoaFisicaMB.recuperarPessoaFisicaPeloCPF(cpf.getText().toString());
                    }
                });

                while (statusLoading < 1) {
                    statusLoading++;
                    android.os.SystemClock.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            recuperarPessoaFisica();
                        }
                    });
                }
            }
        }).start();
    }

    public void recuperarPessoaFisica() {
        if (preferences.getSavedBoolean("pessoafisica_encontrado")) {
            recuperarFuncionario();
        } else {
            Toast.makeText(getApplicationContext(), "CPF não Encontrado!", Toast.LENGTH_LONG).show();
            mostrarCamposDeLogin();
            statusLoading = 0;
        }
    }

    public void recuperarFuncionario() {
        if (preferences.getSavedBoolean("funcionario_encontrado")) {
            if (preferences.getSavedLong("funcionario_cargo") == 5) {
                consultarUsuario();
            } else {
                Toast.makeText(getApplicationContext(), "Usuário não é um Professor!", Toast.LENGTH_LONG).show();
                mostrarCamposDeLogin();
                statusLoading = 0;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Funcionário não Encontrado!", Toast.LENGTH_LONG).show();
            mostrarCamposDeLogin();
            statusLoading = 0;
        }
    }

    public void mostrarCamposDeLogin() {
        progressBar.setVisibility(View.INVISIBLE);
        cpf.setVisibility(View.VISIBLE);
        acessarLogin.setVisibility(View.VISIBLE);
        senha.setVisibility(View.VISIBLE);
    }


    public void consultarSenha() {
        String senhaDigitada = null;
        try {
            senhaDigitada = UtilsFunctions.criptografaSenha(senha.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            mostrarCamposDeLogin();
            statusLoading = 0;
        }
        if (senhaDigitada.equals(preferences.getSavedString("pessoafisica_senha"))) {
            preferences.saveBoolean("logado", true);
            nextActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Senha Inválida!", Toast.LENGTH_LONG).show();
            mostrarCamposDeLogin();
            statusLoading = 0;
//            onResume();
        }
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
    }

    public void verificarConexao() {
        if (preferences.getSavedBoolean("usuario_logado")) {
            if (UtilsFunctions.isConnect(getApplicationContext())) {
                alertaInformacao("Seu Dispositivo está Conectado!");
                preferences.saveBoolean(messages.CONEXAO, true);
            } else {
                alertaInformacao("Seu Dispositivo está Desconectado! Não é possivel efetuar o login.");
                preferences.saveBoolean(messages.CONEXAO, false);
            }
        }
    }

    public void alertaInformacao(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }
}

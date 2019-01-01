package com.android.educar.educar.ui.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Progress;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

public class LoginActivity extends AppCompatActivity {

    private Button acessarLogin;
    private Preferences preferences;
    private TextView cpf;
    private TextView senha;
    private Realm realm;
    private PessoaFisica pessoaFisica;
    private Funcionario funcionario;
    private Usuario usuario;
    private PessoaChamada pessoaChamada;
    private FuncionarioChamada funcionarioChamada;
    private ProgressDialog progressDialog;
    private Messages messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
        setupInit();
        verificarUsuarioLogado();
        verificarConexao();
        onClickItem();
        settings();
        limpar();
        configRealm();
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
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
    }

    public void setupInit() {
        messages = new Messages();
        preferences = new Preferences(this);
        pessoaFisica = new PessoaFisica();
        usuario = new Usuario();
        pessoaChamada = new PessoaChamada(this);
        funcionarioChamada = new FuncionarioChamada(this);
        progressDialog = new ProgressDialog(this);
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
                recuperarPessoaFisica();
            }
        });
    }

    public void recuperarUsuario() {
        usuario = realm.where(Usuario.class).equalTo("pessoaFisica", pessoaFisica.getId()).findFirst();
        if (usuario != null) {
            preferences.saveLong("id_usuario", usuario.getId());
        }
    }

    public void recuperarPessoaFisica() {
        pessoaFisica = realm.where(PessoaFisica.class).equalTo("cpf", cpf.getText().toString()).findFirst();
        if (pessoaFisica != null) {
            recuperarFuncionario();
            recuperarUsuario();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "CPF não encontrado!", Snackbar.LENGTH_LONG).show();
        }
    }

    public void recuperarFuncionario() {
        try {
            funcionario = realm.where(Funcionario.class).equalTo("pessoaFisicaId", pessoaFisica.getId()).findFirst();

            if (funcionario != null) {
                if (funcionario.getCargo() == 5) {
                    salvarDadosUsuarioLogin();
                    nextActivity();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Usuario não é um Professor!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Funcionario não Encontrado!", Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            progressDialog.hide();
            Snackbar.make(findViewById(android.R.id.content), "Ocorreu um Erro, Solicite Administrador!", Snackbar.LENGTH_LONG).show();
        }
    }

    public void salvarDadosUsuarioLogin() {
        if (pessoaFisica.getCpf() != null) {
            preferences.saveLong("id_pessoafisica", pessoaFisica.getId());
            preferences.saveLong("id_funcionario", funcionario.getId());
            preferences.saveBoolean("logado", true);
        }
    }

    public void consultarSenha() {
        String senhaDigitada = null;
        try {
            senhaDigitada = UtilsFunctions.criptografaSenha(senha.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        String senhaProfessor = professorDAO.selecionarProfessor(preferences.getSavedLong(messages.ID_USUARIO)).getSenha();
//        if (senhaDigitada.equals(senhaProfessor)) {
//            nextActivity();
//        } else {
//            Toast.makeText(getApplicationContext(), "Senha Inválida!", Toast.LENGTH_SHORT).show();
//        }
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), UnidadeActivity.class));
    }

//    public void carregarDadosSync() {
//        if (!preferences.getSavedBoolean("sync")) {
//            if (UtilsFunctions.isConnect(getApplicationContext())) {
//
//                pessoaChamada.pessoaFisicaAPI();
//                pessoaChamada.recuperarPerfilAPI();
//                pessoaChamada.recuperarUsuariosAPI();
//                funcionarioChamada.recuperarFuncionariosAPI();
//
//                preferences.saveBoolean("sync", true);
//            }
//        }
//    }

    public void verificarConexao() {
        if (UtilsFunctions.isConnect(getApplicationContext())) {
            alertaInformacao("Seu Dispositivo está Conectado!");
            preferences.saveBoolean(messages.CONEXAO, true);
        } else {
            alertaInformacao("Seu Dispositivo está Desconectado! Não é possivel efetuar o login.");
            preferences.saveBoolean(messages.CONEXAO, false);
        }
    }

    public void alertaInformacao(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Conexão!");
//        builder.setMessage(message);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//
//        }).show();
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }
}

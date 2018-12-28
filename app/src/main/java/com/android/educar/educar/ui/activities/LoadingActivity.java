package com.android.educar.educar.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

public class LoadingActivity extends AppCompatActivity {

    private Preferences preferences;
    private UtilsFunctions utilsFunctions;
    private ProgressDialog progressDialog;
    private Messages messages;

    private FuncionarioChamada funcionarioChamada;
    private PessoaChamada pessoaChamada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        setupInit();
        verificarConexao();
        mostrarLogo();
    }

    public void mostrarLogo() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 4000);
    }

    public void sincronizarUsuariosRealm() {

        if (preferences.getSavedBoolean(messages.USUARIO_LOGADO)) {
            nextActivity();
        }
//
        if (preferences.getSavedBoolean(messages.CONEXAO)) {
            pessoaChamada.pessoaFisicaAPI();
            pessoaChamada.recuperarPerfilAPI();
            pessoaChamada.recuperarUsuariosAPI();
            funcionarioChamada.recuperarFuncionariosAPI();
        }
    }

    public void verificarConexao() {
        if (UtilsFunctions.isConnect(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Seu Dispositivo está Conectado!", Toast.LENGTH_LONG).show();
            preferences.saveBoolean(messages.CONEXAO, true);
        } else {
            Toast.makeText(getApplicationContext(), "Seu Dispositivo está Desconectado!", Toast.LENGTH_LONG).show();
            preferences.saveBoolean(messages.CONEXAO, false);
        }
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void setupInit() {
        preferences = new Preferences(this);
        utilsFunctions = new UtilsFunctions();
        messages = new Messages();

        progressDialog = UtilsFunctions.progressDialog(this, "Carregando...");

        funcionarioChamada = new FuncionarioChamada(this);
        pessoaChamada = new PessoaChamada(this);

        sincronizarUsuariosRealm();
    }
}

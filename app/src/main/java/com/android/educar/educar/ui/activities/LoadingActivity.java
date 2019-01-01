package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Preferences preferences;
    private PessoaChamada pessoaChamada;
    private FuncionarioChamada funcionarioChamada;
    private TextView textLoad;
    private int statusLoading;
    private ImageView botaoAcessar;
    private Handler handler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading2);
        binding();
        settings();
        setupInit();
        verificarConexao();
        onClick();
    }

    public void verificarConexao() {
        if (UtilsFunctions.isConnect(getApplicationContext())) {
            loading();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Dispositivo sem Conexão com Internet, verifique sua conexão!", Snackbar.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            textLoad.setVisibility(View.INVISIBLE);
//            botaoAcessar.setVisibility(View.VISIBLE);
        }
    }

    public void loading() {
        if (!preferences.getSavedBoolean("sync")) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            carregarDadosSync();
                        }
                    });

                    while (statusLoading < 1) {
                        statusLoading++;
                        android.os.SystemClock.sleep(60000);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(statusLoading);

                            }
                        });
                    }
                }
            }).start();
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            textLoad.setVisibility(View.INVISIBLE);
            botaoAcessar.setVisibility(View.VISIBLE);
        }
    }

    public void onClick() {
        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    public void binding() {
        progressBar = findViewById(R.id.progresbar_loading);
        textLoad = findViewById(R.id.textocarregamento_id);
        botaoAcessar = findViewById(R.id.botao_acessar_id);
    }

    public void setupInit() {
        preferences = new Preferences(getApplicationContext());
        pessoaChamada = new PessoaChamada(getApplicationContext());
        funcionarioChamada = new FuncionarioChamada(getApplicationContext());
        statusLoading = 0;
        handler = new Handler();
    }

    public void settings() {
        getSupportActionBar().hide();
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void carregarDadosSync() {
        if (UtilsFunctions.isConnect(getApplicationContext())) {

            pessoaChamada.pessoaFisicaAPI();
            pessoaChamada.recuperarPerfilAPI();
            pessoaChamada.recuperarUsuariosAPI();
            funcionarioChamada.recuperarFuncionariosAPI();

            preferences.saveBoolean("sync", true);
        }
    }
}

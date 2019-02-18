package com.android.educar.educar.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.SincronizarComAPiMB;
import com.android.educar.educar.network.chamadas.AnoLetivoChamada;
import com.android.educar.educar.network.chamadas.FuncionarioEscolaChamada;
import com.android.educar.educar.network.chamadas.OcorrenciaChamada;
import com.android.educar.educar.network.chamadas.TurmaChamada;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import io.realm.Realm;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Preferences preferences;
    private TextView textLoad;
    private int statusLoading;
    private ImageView botaoAcessar;
    private Handler handler;
    private Realm realm;
    private SincronizarComAPiMB sincronizarComAPiMB;

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading2);
        binding();
        settings();
        setupInit();
        onClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configRealm();
        verificarConexao();
    }

    public void verificarConexao() {
        if (UtilsFunctions.isConnect(getApplicationContext())) {
            loading();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Dispositivo sem Conexão com Internet, verifique sua conexão!", Snackbar.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            textLoad.setVisibility(View.INVISIBLE);
            botaoAcessar.setVisibility(View.VISIBLE);
        }
    }

    public void loading() {
        if (!preferences.getSavedBoolean("sync_dados")){
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
                        android.os.SystemClock.sleep(40000);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(statusLoading);
                                progressBar.setVisibility(View.INVISIBLE);
                                textLoad.setVisibility(View.INVISIBLE);
                                botaoAcessar.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(LoadingActivity.this, UnidadeActivity.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(LoadingActivity.this, R.anim.mover_esquerda, R.anim.fade_out);
                ActivityCompat.startActivity(LoadingActivity.this, intent, activityOptionsCompat.toBundle());
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

        statusLoading = 0;
        handler = new Handler();
        sincronizarComAPiMB = new SincronizarComAPiMB(getApplicationContext());
    }

    public void settings() {
        getSupportActionBar().hide();
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void carregarDadosSync() {
        if (!preferences.getSavedBoolean("sync_dados")) {
            sincronizarComAPiMB.recuperarDadosDaAPISalvarBancoDeDadosRealm();
            preferences.saveBoolean("sync_dados", true);
        }
    }
}

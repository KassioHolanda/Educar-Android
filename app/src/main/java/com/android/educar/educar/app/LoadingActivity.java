package com.android.educar.educar.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.ProfessorDAO;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaProfessoresAPI;
import com.android.educar.educar.utils.CarregarDados;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {

    private Preferences preferences;
    private APIService apiService;
    private UtilsFunctions utilsFunctions;
    private ProgressDialog progressDialog;
    private List<Professor> professorList;
    private Messages messages;
    private List<Professor> professores;
//    private ClassDAO ClassDAO;

    private ProfessorDAO professorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        setupInit();
//        settings();
        verificarConexao();
        mostrarLogo();
    }

    public void settings() {
        getSupportActionBar().hide();
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

    public void verificarConexao() {
        if (isConnect(getApplicationContext())) {
//            CarregarDados carregarDados = new CarregarDados(getApplicationContext());
//            carregarDados.salvarProfessoresBD();
            Toast.makeText(getApplicationContext(), "Seu Dispositivo está Conectado!", Toast.LENGTH_LONG).show();
//            nextActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Seu Dispositivo está Desconectado!", Toast.LENGTH_LONG).show();
            nextActivity();
        }
        preferences.saveBoolean("connection", isConnect(getApplicationContext()));
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public boolean isConnect(Context contexto) {
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) {
            return true;
        } else {
            return false;
        }
    }

    public void setupInit() {
//        ClassDAO = new ClassDAO(getApplicationContext());
        professorDAO = new ProfessorDAO(getApplicationContext());
        professorList = new ArrayList<>();
        preferences = new Preferences(this);
        apiService = new APIService("");
        utilsFunctions = new UtilsFunctions();
        messages = new Messages();
        progressDialog = UtilsFunctions.progressDialog(getApplicationContext(), "Carregando...");
    }
}

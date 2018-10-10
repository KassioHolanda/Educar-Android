package com.android.educar.educar.app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.educar.educar.R;
//import com.android.educar.educar.dao.ClassDAO;
//import com.android.educar.educar.dao.ProfessorDAO;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.utils.CarregarDados;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracoesActivity extends AppCompatActivity {

    private UtilsFunctions utilsFunctions;
    private Messages messages;
    private Preferences preferences;
    private List<Professor> professorList;
    private ProgressDialog progressDialog;
    private List<Professor> professores;
    private Button sincronizarBanco;
    private CarregarDados carregarDados;
    private Button alterarSenha;
    private EditText senha;
//    private ClassDAO classDAO;

//    private ProfessorDAO professorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        binding();
        setupInit();
//        settings();
//        bancoDeDados();
//        carregarUsuarioAPI();
    }

    private void binding() {
        sincronizarBanco = findViewById(R.id.sincronizar_banco_id);
        alterarSenha = findViewById(R.id.alterar_senha_id);
        senha = findViewById(R.id.nova_senha_id);

        sincronizarBanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarDados.carregarDados();
            }
        });

        alterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Professor professor = professorDAO.selecionarProfessor(preferences.getSavedLong("id_usuario"));
//                try {
//                    professor.setSenha(UtilsFunctions.criptografaSenha(senha.getText().toString()));
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//                professorDAO.atualizarProfessor(professor);
            }
        });
    }

    public void settings() {
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }


    public void setupInit() {
//        classDAO = new ClassDAO(this);
        professorList = new ArrayList<>();
//        professorDAO = new ProfessorDAO(getApplicationContext());
        preferences = new Preferences(this);
        utilsFunctions = new UtilsFunctions();
        messages = new Messages();
        progressDialog = UtilsFunctions.progressDialog(getApplicationContext(), "Carregando...");
        carregarDados = new CarregarDados(getApplicationContext());
    }
}

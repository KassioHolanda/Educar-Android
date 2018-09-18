package com.android.educar.educar.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.ProfessorDAO;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class LoginActivity extends AppCompatActivity {

    private Button acessarLogin;
    private Preferences preferences;
    private UtilsFunctions utilsFunctions;
    private TextView cpf;
    private TextView senha;
    private List<Professor> professorList;
    private ProgressDialog progressDialog;
    private Messages messages;
    private ClassDAO classDAO;
    private ProfessorDAO professorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
        setupInit();
        onClickItem();
        settings();
        limpar();
    }

    public void limpar() {
        cpf.setText("");
        senha.setText("");
    }

    public void settings() {
        getSupportActionBar().hide();
    }

    public void setupInit() {
        progressDialog = UtilsFunctions.progressDialog(this, "Aguarde...");
        professorList = new ArrayList<>();
        preferences = new Preferences(this);
        messages = new Messages();
        utilsFunctions = new UtilsFunctions();
//        classDAO = new ClassDAO(getApplicationContext());
        professorDAO = new ProfessorDAO(getApplicationContext());
        professorList = professorDAO.professores();
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
                verificarUsuario();
            }
        });
    }

    public void verificarUsuario() {
        progressDialog.show();
        for (int i = 0; i < professorList.size(); i++) {
            if (cpf.getText().toString().equals(professorList.get(i).getCpf())) {
                preferences.saveLong("id_usuario", professorList.get(i).getPk());
                consultarSenha();
            } else {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "CPF não encontrado!", Toast.LENGTH_SHORT).show();
            }
        }
        progressDialog.hide();
    }

    public void consultarSenha() {
        String senhaDigitada = null;
        try {
            senhaDigitada = UtilsFunctions.criptografaSenha(senha.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String senhaProfessor = professorDAO.selecionarProfessor(preferences.getSavedLong(messages.ID_USUARIO)).getSenha();
        if (senhaDigitada.equals(senhaProfessor)) {
            nextActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Senha Inválida!", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), UnidadeActivity.class));
    }
}

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
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    private Button acessarLogin;
    private Preferences preferences;
    private UtilsFunctions utilsFunctions;
    private TextView cpf;
    private TextView senha;
    private List<Funcionario> funcionarios;
    private List<PessoaFisica> pessoaFisicas;
    private ProgressDialog progressDialog;
    private Messages messages;
    private Realm realm;
    private PessoaFisica pessoaFisica;
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
        setupInit();
        onClickItem();
        settings();
        limpar();
        verificarUsuarioLogado();
        configRealm();
        carregarUsuariosBandoDeDadosRealm();
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void carregarUsuariosBandoDeDadosRealm() {
        final RealmResults<PessoaFisica> pessoaFisicas = realm.where(PessoaFisica.class).findAll();
        this.pessoaFisicas = pessoaFisicas;
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
        progressDialog = UtilsFunctions.progressDialog(this, "Aguarde...");
        pessoaFisicas = new ArrayList<>();
        preferences = new Preferences(this);
        messages = new Messages();
        utilsFunctions = new UtilsFunctions();
        pessoaFisica = new PessoaFisica();
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

    public void recuperarPessoaFisica() {
        pessoaFisica = realm.where(PessoaFisica.class).equalTo("cpf", cpf.getText().toString()).findFirst();
        if (pessoaFisica != null) {
            recuperarFuncionario();
        } else {
            Toast.makeText(getApplicationContext(), "CPF não encontrado!", Toast.LENGTH_LONG).show();
        }
    }

    public void recuperarFuncionario() {
        try {
            funcionario = realm.where(Funcionario.class).equalTo("pessoaFisicaId", pessoaFisica.getId()).findFirst();
            if (funcionario != null) {
                if (funcionario.getCargo() == 5) {
                    verificarUsuario();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario não é um Professor!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Funcionario não Encontrado!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ocorreu um Erro, Solicite Administrador!", Toast.LENGTH_LONG).show();
        }
    }

    public void verificarUsuario() {
        if (pessoaFisica.getCpf() != null) {
            preferences.saveLong("id_pessoafisica", pessoaFisica.getId());
            preferences.saveLong("id_funcionario", funcionario.getId());
            preferences.saveBoolean("logado", true);
            progressDialog.hide();
            nextActivity();
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
}

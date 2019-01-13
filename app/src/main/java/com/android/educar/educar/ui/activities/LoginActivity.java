package com.android.educar.educar.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.FuncionarioMB;
import com.android.educar.educar.mb.PessoaFisicaMB;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;

import io.realm.Realm;

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
    private boolean onresume;

    private PessoaFisicaMB pessoaFisicaMB;
    private FuncionarioMB funcionarioMB;

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
    }

    public void setupInit() {
        messages = new Messages();
        preferences = new Preferences(this);
        pessoaFisica = new PessoaFisica();
        usuario = new Usuario();
        funcionario = new Funcionario();
        perfil = new Perfil();
        pessoaFisicaMB = new PessoaFisicaMB(getApplicationContext());
        funcionarioMB = new FuncionarioMB(getApplicationContext());
        onresume = false;
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
                try {
                    pessoaFisicaMB.recuperarPesoaFisica(cpf.getText().toString());

                    long id = preferences.getSavedLong("id_pessoafisica");
                    funcionarioMB.recuperarFuncionarioUsuario(preferences.getSavedLong("id_pessoafisica"));
                    pessoaFisicaMB.recuperarUsuario(preferences.getSavedLong("id_pessoafisica"));


//                    funcionarioMB.recuperarFuncionarioUsuario();
                    recuperarPessoaFisica();

                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "CPF Invalido", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        realm.refresh();
    }

    public void recuperarUsuario() {
//        usuario = realm.where(Usuario.class).equalTo("pessoaFisica", preferences.getSavedLong("id_pessoafisica")).findFirst();
        if (preferences.getSavedBoolean("usuario_encontrado")) {
            pessoaFisicaMB.recuperarPerfil(preferences.getSavedLong("usuario_perfil"));
//            perfil = realm.where(Perfil.class).equalTo("id", usuario.getPerfil()).findFirst();
//            preferences.saveLong("id_usuario", usuario.getId());
        } else {
            Toast.makeText(getApplicationContext(), "Usuário não Encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarPessoaFisica() {
        if (preferences.getSavedBoolean("pessoafisica_encontrado")) {
            recuperarUsuario();
            recuperarFuncionario();
        } else {
            Toast.makeText(getApplicationContext(), "CPF não Encontrado!", Toast.LENGTH_LONG).show();
        }
    }

    public void recuperarFuncionario() {
//        funcionario = realm.where(Funcionario.class).equalTo("pessoaFisicaId", pessoaFisica.getId()).findFirst();
//        try {
        if (preferences.getSavedBoolean("funcionario_encontrado")) {
            if (preferences.getSavedLong("funcionario_cargo") == 5) {
                consultarSenha();
            } else {
                Toast.makeText(getApplicationContext(), "Usuário não é um Professor!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Funcionário não Encontrado!", Toast.LENGTH_LONG).show();
        }
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "Ocorreu um Erro, Solicite Administrador!", Toast.LENGTH_LONG).show();
//        }
    }


    public void consultarSenha() {
        String senhaDigitada = null;
        try {
            senhaDigitada = UtilsFunctions.criptografaSenha(senha.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (senhaDigitada.equals(preferences.getSavedString("pessoafisica_senha"))) {
            preferences.saveBoolean("logado", true);
            nextActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Senha Inválida!", Toast.LENGTH_LONG).show();
        }
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
    }

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
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }
}

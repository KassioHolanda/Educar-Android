package com.android.educar.educar.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.modelalterado.Funcionario;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button acessarLogin;
    private Preferences preferences;
    private TextInputEditText cpf;
    private TextInputEditText senha;
    private Funcionario funcionario;
    private Messages messages;
    private APIService apiService;
    private ProgressDialog progressDialog;

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
        funcionario = new Funcionario();
        apiService = new APIService("");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Carregando");
        progressDialog.setMessage("Aguarde alguns Segundos...");
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
                recuperarLogin();
//                consultarFuncionario();
            }
        });
    }

    private void consultarFuncionario() {
        if (funcionario.getCargo().getId() == 5) {
            consultarSenha();
        } else {
            Toast.makeText(getApplicationContext(), "Usuário não é um Professor", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarUsuarioLogado();
        verificarConexao();
    }

    public void consultarSenha() {
        String senhaDigitada = null;
        try {
            senhaDigitada = UtilsFunctions.criptografaSenha(senha.getText().toString());
            if (senhaDigitada.equals(funcionario.getPessoaFisica().getSenha())) {
                preferences.saveBoolean("logado", true);
                preferences.saveString("cpf", cpf.getText().toString());
                nextActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Senha Inválida!", Toast.LENGTH_LONG).show();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void nextActivity() {
        startActivity(new Intent(getApplicationContext(), UnidadeActivity.class));
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

    public void recuperarLogin() {
        progressDialog.show();
        Call<List<Funcionario>> listCall = apiService.getFuncionarioEndPoint().getFuncionarioPessoaFisica(cpf.getText().toString());
        listCall.enqueue(new Callback<List<Funcionario>>() {
            @Override
            public void onResponse(Call<List<Funcionario>> call, Response<List<Funcionario>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        recuperarFuncionario(response.body().get(0));
                    } else {
                        Toast.makeText(getApplicationContext(), "Nenhum Usuário Encontrado.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Funcionario>> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }

    public void recuperarFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        consultarFuncionario();
    }
}

package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;
import com.android.educar.educar.utils.Preferences;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioChamada {

    private APIService apiService;
    private Context context;
    private Preferences preferences;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public FuncionarioChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        preferences = new Preferences(context);
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void recuperarFuncionarioPessoaFisicaAPI(Long pessoafisica) {
        Call<List<Funcionario>> funcionarioCall = apiService.getFuncionarioEndPoint().getFuncionarioPessoaFisica(pessoafisica);
        funcionarioCall.enqueue(new Callback<List<Funcionario>>() {
            @Override
            public void onResponse(Call<List<Funcionario>> call, Response<List<Funcionario>> response) {
                if (response.isSuccessful()) {
                    salvarFuncionario(response.body());
                    Log.i("RESPONSE", "FUNCIONARIO RECUPERADO");
                }
            }

            @Override
            public void onFailure(Call<List<Funcionario>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarFuncionario(List<Funcionario> funcionarios) {

        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(funcionarios);
        Realm.getDefaultInstance().commitTransaction();

        try {
            preferences.saveBoolean("funcionario_encontrado", true);
            preferences.saveLong("id_funcionario", funcionarios.get(0).getId());
            preferences.saveLong("funcionario_cargo", funcionarios.get(0).getCargo());
        } catch (IndexOutOfBoundsException e) {
            Log.i("ERRO_API", e.getMessage());
        }
    }
}
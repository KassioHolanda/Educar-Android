package com.android.educar.educar.chamadas;

import android.app.ProgressDialog;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaFuncionariosAPI;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioMB {

    private APIService apiService;
    private List<Funcionario> funcionarioList;
    private List<PessoaFisica> pessoaFisicas;
    private Context context;
    private ProgressDialog progressBar;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public FuncionarioMB(Context context) {
        apiService = new APIService("");
        funcionarioList = new ArrayList<>();
        this.context = context;
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void funcionariosAPI() {
        final Call<ListaFuncionariosAPI> listaProfessoresAPICall = apiService.getFuncionarioEndPoint().funcionarios();
        listaProfessoresAPICall.enqueue(new Callback<ListaFuncionariosAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionariosAPI> call, Response<ListaFuncionariosAPI> response) {
                if (response.isSuccessful()) {
                    salvarFuncionarioBD(response.body().getResults());

                }
            }

            @Override
            public void onFailure(Call<ListaFuncionariosAPI> call, Throwable t) {

            }
        });
    }


    public void salvarFuncionarioBD(final List<Funcionario> funcionarios) {
        realm.beginTransaction();
        for (int i = 0; i < funcionarios.size(); i++) {
            realm.copyToRealmOrUpdate(funcionarios.get(i));
            Log.i("funcionario", "" + funcionarios.get(i).getCargo());
        }
        realm.commitTransaction();
    }
}

package com.android.educar.educar.chamadas;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaFuncionarioEscolaAPI;
import com.android.educar.educar.service.ListaFuncionariosAPI;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioEscolaMB {

    private APIService apiService;
    private Context context;
    private ProgressDialog progressBar;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public FuncionarioEscolaMB(Context context) {
        apiService = new APIService("");
        this.context = context;
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void funcionariosEscola() {
        final Call<ListaFuncionarioEscolaAPI> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola();
        listaFuncionarioEscolaAPICall.enqueue(new Callback<ListaFuncionarioEscolaAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionarioEscolaAPI> call, Response<ListaFuncionarioEscolaAPI> response) {
                if (response.isSuccessful()) {
                    salvarFuncionarioEscola(response.body().getResults());

                }
            }

            @Override
            public void onFailure(Call<ListaFuncionarioEscolaAPI> call, Throwable t) {

            }
        });
    }


    public void salvarFuncionarioEscola(final List<FuncionarioEscola> funcionarios) {
        realm.beginTransaction();
        for (int i = 0; i < funcionarios.size(); i++) {
            realm.copyToRealmOrUpdate(funcionarios.get(i));
            Log.i("funcionarioescola", "" + funcionarios.get(i).getFuncionario());
        }
        realm.commitTransaction();
    }
}

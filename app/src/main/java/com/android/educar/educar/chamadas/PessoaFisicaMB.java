package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;

import java.util.List;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaFisicaMB {

    private Context context;
    private APIService apiService;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public PessoaFisicaMB(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void pessoaFisicaAPI() {
        Call<ListaPessoaFisicaAPI> listaProfessoresAPICall = apiService.getPessoaFisicaEndPoint().pessoasFisicas();
        listaProfessoresAPICall.enqueue(new Callback<ListaPessoaFisicaAPI>() {
            @Override
            public void onResponse(Call<ListaPessoaFisicaAPI> call, Response<ListaPessoaFisicaAPI> response) {
                if (response.isSuccessful()) {
                    salvarPessoaFisicaBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaPessoaFisicaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarPessoaFisicaBD(final List<PessoaFisica> pessoaFisicas) {
        realm.beginTransaction();
        for (int i = 0; i < pessoaFisicas.size(); i++) {
            realm.copyToRealmOrUpdate(pessoaFisicas.get(i));
            Log.i("pessoafisica", "" + pessoaFisicas.get(i).getNome());
        }
        realm.commitTransaction();
    }
}

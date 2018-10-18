package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeMB {
    private Realm realm;
    private Context context;
    private APIService apiService;

    public UnidadeMB(Context context) {
        apiService = new APIService("");
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void unidadesAPI() {
        Call<ListaUnidadesAPI> listaUnidadesAPICall = apiService.getUnidadeEndPoint().unidades();
        listaUnidadesAPICall.enqueue(new Callback<ListaUnidadesAPI>() {
            @Override
            public void onResponse(Call<ListaUnidadesAPI> call, Response<ListaUnidadesAPI> response) {
                if (response.isSuccessful()) {
                    salvarUnidade(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaUnidadesAPI> call, Throwable t) {

            }
        });
    }


    public void salvarUnidade(List<Unidade> unidades) {
        realm.beginTransaction();
        for (int i = 0; i < unidades.size(); i++) {
            realm.copyToRealmOrUpdate(unidades.get(i));
            Log.i("unidade", "" + unidades.get(i).getAbreviacao());
        }
        realm.commitTransaction();
    }
}

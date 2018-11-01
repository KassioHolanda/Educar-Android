package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AnoLetivo;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaAnoLetivoAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnoLetivoMB {

    private Realm realm;
    private Context context;
    private APIService apiService;

    public AnoLetivoMB(Context context) {
        apiService = new APIService("");
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void anoLetivoAPI() {
        Call<ListaAnoLetivoAPI> listaAnoLetivoAPICall = apiService.getAnoLetivoEndPoint().anosLetivos();
        listaAnoLetivoAPICall.enqueue(new Callback<ListaAnoLetivoAPI>() {
            @Override
            public void onResponse(Call<ListaAnoLetivoAPI> call, Response<ListaAnoLetivoAPI> response) {
                if (response.isSuccessful()) {
                    salvaAnoLetivoBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAnoLetivoAPI> call, Throwable t) {

            }
        });
    }

    public void salvaAnoLetivoBD(final List<AnoLetivo> anoLetivos) {
        realm.beginTransaction();
        for (int i = 0; i < anoLetivos.size(); i++) {
            realm.copyToRealmOrUpdate(anoLetivos.get(i));
            Log.i("disciplinas", "" + anoLetivos.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

}

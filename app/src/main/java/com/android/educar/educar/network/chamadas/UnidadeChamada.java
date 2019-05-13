package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeChamada {
    private Context context;
    private APIService apiService;
    private Realm realm;

    private LocalEscolaChamada localEscolaChamada;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public UnidadeChamada(Context context) {
        apiService = new APIService("");
        configRealm();
        localEscolaChamada = new LocalEscolaChamada(context);
    }

    public void recuperarUnidadesDoProfessorAPI(Long unidadeId) {
        Call<Unidade> listaUnidadesAPICall = apiService.getUnidadeEndPoint().getUnidade(unidadeId);
        listaUnidadesAPICall.enqueue(new Callback<Unidade>() {
            @Override
            public void onResponse(Call<Unidade> call, Response<Unidade> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    try {
                        Thread.sleep(1000);
                        localEscolaChamada.recuperarLocalEscolaUnidade(response.body().getId());
                    } catch (InterruptedException ex) {
                    }

                    Log.i("RESPONSE", "UNIDADES RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<Unidade> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

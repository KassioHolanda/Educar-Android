package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaLocalEscolaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalEscolaChamada {
    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private TurmaChamada turmaChamada;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public LocalEscolaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        turmaChamada = new TurmaChamada(context);
    }


    public void recuperarLocalEscolaUnidade(Long unidadeId) {
        Call<List<LocalEscola>> listCall = apiService.getLocalEscolaEndPoint().locaisEscolaUnidade(unidadeId);
        listCall.enqueue(new Callback<List<LocalEscola>>() {
            @Override
            public void onResponse(Call<List<LocalEscola>> call, Response<List<LocalEscola>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    recuperarTodasAsTurmasDaUnidade(response.body());
                    Log.i("RESPONSE", "LOCALESCOLA RECUPERADOS");
                }
            }

            @Override
            public void onFailure(Call<List<LocalEscola>> call, Throwable t) {

            }
        });
    }

    public void recuperarTodasAsTurmasDaUnidade(List<LocalEscola> localEscolas) {
        for (int i = 0; i < localEscolas.size(); i++) {
            turmaChamada.recuperarTurmasDaUnidade(localEscolas.get(i).getId());
        }
    }
}

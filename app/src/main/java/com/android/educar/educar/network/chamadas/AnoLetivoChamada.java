package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.AnoLetivo;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaAnoLetivoAPI;
import com.android.educar.educar.network.service.ListaBimestreAPI;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnoLetivoChamada {

    private RealmObjectsDAO realmObjectsDAO;
    private Context context;
    private APIService apiService;
    private Realm realm;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public AnoLetivoChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void anoLetivoAPI() {
        Call<ListaAnoLetivoAPI> listaAnoLetivoAPICall = apiService.getAnoLetivoEndPoint().anosLetivos();
        listaAnoLetivoAPICall.enqueue(new Callback<ListaAnoLetivoAPI>() {
            @Override
            public void onResponse(Call<ListaAnoLetivoAPI> call, Response<ListaAnoLetivoAPI> response) {
                if (response.isSuccessful()) {
                    salvarAnoLetivoRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaAnoLetivoAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarAnoLetivoRealm(List<AnoLetivo> anoLetivos) {
        realm.beginTransaction();
        for (int i = 0; i < anoLetivos.size(); i++) {
            realm.copyToRealmOrUpdate(anoLetivos.get(i));
        }
        realm.commitTransaction();
    }

    public void recuperarBimestreAPI() {
        Call<ListaBimestreAPI> listaAnoLetivoAPICall = apiService.getBimestreEndPoint().bimestres();
        listaAnoLetivoAPICall.enqueue(new Callback<ListaBimestreAPI>() {
            @Override
            public void onResponse(Call<ListaBimestreAPI> call, Response<ListaBimestreAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarBimestreRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaBimestreAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarBimestreRealm(List<Bimestre> bimestres) {
        realm.beginTransaction();
        for (int i = 0; i < bimestres.size(); i++) {
            realm.copyToRealmOrUpdate(bimestres.get(i));
        }
        realm.commitTransaction();
    }
}

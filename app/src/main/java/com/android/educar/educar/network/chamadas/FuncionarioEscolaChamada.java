package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;
import com.android.educar.educar.model.modelalterado.FuncionarioEscola;
import com.android.educar.educar.network.service.APIService;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioEscolaChamada {

    private APIService apiService;
    private Context context;
    private Realm realm;

    private UnidadeChamada unidadeChamada;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public FuncionarioEscolaChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        unidadeChamada = new UnidadeChamada(context);
        configRealm();
    }

    public void funcionariosEscola(Long funcionarioId) {
        Call<List<FuncionarioEscola>> listaFuncionarioEscolaAPICall = apiService.getFuncionarioEscolaEndPoint().funcionariosEscola(funcionarioId);
        listaFuncionarioEscolaAPICall.enqueue(new Callback<List<FuncionarioEscola>>() {
            @Override
            public void onResponse(Call<List<FuncionarioEscola>> call, Response<List<FuncionarioEscola>> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    try {
                        Thread.sleep(1000);
                        recuperarUnidades(response.body());
                    } catch (InterruptedException ex) {
                    }

                    Log.i("RESPONSE", "FUNCIONARIOESCOLA CARREGADOS");
                }
            }

            @Override
            public void onFailure(Call<List<FuncionarioEscola>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarUnidades(List<FuncionarioEscola> funcionarioEscolas) {
        for (int i = 0; i < funcionarioEscolas.size(); i++) {
//            unidadeChamada.recuperarUnidadesDoProfessorAPI(funcionarioEscolas.get(i).getUnidade());
        }
    }
}

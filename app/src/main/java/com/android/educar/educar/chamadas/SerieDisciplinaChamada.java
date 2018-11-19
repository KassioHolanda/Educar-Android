package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.service.ListaSerieDisciplinaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerieDisciplinaChamada {
    private Context context;
    private APIService apiService;
    private RealmBO realmBO;

    public SerieDisciplinaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmBO = new RealmBO(context);
    }


    public void configRealm() {
        Realm.init(context);
        realmBO = new RealmBO(context);
    }

    public void serieDisciplina() {
        Call<ListaSerieDisciplinaAPI> listaSerieDisciplinaAPICall = apiService.getSerieDisciplinaEndPoint().serieDisciplinas();

        listaSerieDisciplinaAPICall.enqueue(new Callback<ListaSerieDisciplinaAPI>() {
            @Override
            public void onResponse(Call<ListaSerieDisciplinaAPI> call, Response<ListaSerieDisciplinaAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.serieDisciplinaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaSerieDisciplinaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

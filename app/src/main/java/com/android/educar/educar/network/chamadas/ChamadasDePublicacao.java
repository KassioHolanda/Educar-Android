package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.model.modelalterado.Ocorrencia;
import com.android.educar.educar.model.modeloriginal.AlunoNotaMes;
import com.android.educar.educar.network.service.APIService;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChamadasDePublicacao {

    private Context context;
    private Realm realm;
    private APIService apiService;

    private Ocorrencia ocorrenciaSelecionada;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public ChamadasDePublicacao(Context context) {
        this.context = context;
        apiService = new APIService("");
        configRealm();
    }

    public void verificarAlunoNotaMesParaPublicar() {

    }

    private void publicarAlunosNotaMes() {

    }

    public void atualizarAlunoNotaMes() {

    }

    private void publicarOcorrencia(Ocorrencia ocorrenciaSelecionada) {
        Call<Ocorrencia> ocorrenciaCall = apiService.getOcorrenciaEndPoint().postOcorrencia(ocorrenciaSelecionada);
        ocorrenciaCall.enqueue(new Callback<Ocorrencia>() {
            @Override
            public void onResponse(Call<Ocorrencia> call, Response<Ocorrencia> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    ocorrenciaSelecionada.setNovo(false);
                    realm.copyToRealmOrUpdate(ocorrenciaSelecionada);
                    realm.commitTransaction();
                    Log.i("RESPONSE", "OCORRENCIAS RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<Ocorrencia> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void verificarOcorrenciasParaPublicar() {
        List<Ocorrencia> ocorrencias = realm.copyFromRealm(realm.where(Ocorrencia.class).findAll());
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.isNovo()) {
                publicarOcorrencia(ocorrencia);
            }
        }
    }

}

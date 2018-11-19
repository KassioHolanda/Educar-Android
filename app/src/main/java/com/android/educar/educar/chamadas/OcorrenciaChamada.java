package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaOcorrenciaAPI;
import com.android.educar.educar.service.ListaTipoOcorrenciaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OcorrenciaChamada {
    private Context context;
    private RealmBO realmBO;
    private APIService apiService;

    public OcorrenciaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmBO = new RealmBO(context);
    }

    public void salvarDadosOcorrenciaApiBancoDeDados() {
        Call<ListaOcorrenciaAPI> listaOcorrenciaAPICall = apiService.getOcorrenciaEndPoint().ocorrencias();
        listaOcorrenciaAPICall.enqueue(new Callback<ListaOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaOcorrenciaAPI> call, Response<ListaOcorrenciaAPI> response) {
                if (response.isSuccessful()) {
                    realmBO.salvarOcorrenciaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaOcorrenciaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarDadosTipoOcorrenciaApiBancoDeDados() {
        Call<ListaTipoOcorrenciaAPI> listaTipoOcorrenciaAPICall = apiService.getTipoOcorrenciaEndPoint().tiposOcorrencia();
        listaTipoOcorrenciaAPICall.enqueue(new Callback<ListaTipoOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaTipoOcorrenciaAPI> call, Response<ListaTipoOcorrenciaAPI> response) {
                realmBO.salvarTiposOcorrenciaRealm(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ListaTipoOcorrenciaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postOcorrenciaAPI(Ocorrencia ocorrencia) {
        Call<Ocorrencia> ocorrenciaCall = apiService.getOcorrenciaEndPoint().postOcorrencia(ocorrencia);
        ocorrenciaCall.enqueue(new Callback<Ocorrencia>() {
            @Override
            public void onResponse(Call<Ocorrencia> call, Response<Ocorrencia> response) {
                if (response.code() == 400) {
                    Toast.makeText(context, "" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    Log.i("ERRO OCORRENCIA", response.errorBody().toString());
                }
                if (response.isSuccessful()) {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ocorrencia> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
//                Toast.makeText(context, "Ocorreu um Erro! Verifique sua Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

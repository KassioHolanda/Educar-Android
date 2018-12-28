package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaOcorrenciaAPI;
import com.android.educar.educar.network.service.ListaTipoOcorrenciaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OcorrenciaChamada {
    private Context context;
    private RealmObjectsDAO realmObjectsDAO;
    private APIService apiService;
    private Realm realm;

    public OcorrenciaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarTodasOcorrenciasAPI() {
        Call<ListaOcorrenciaAPI> listaOcorrenciaAPICall = apiService.getOcorrenciaEndPoint().ocorrencias();
        listaOcorrenciaAPICall.enqueue(new Callback<ListaOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaOcorrenciaAPI> call, Response<ListaOcorrenciaAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarOcorrenciaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaOcorrenciaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarOcorrenciaRealm(List<Ocorrencia> ocorrencias) {
        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            realm.copyToRealmOrUpdate(ocorrencias.get(i));
        }
        realm.commitTransaction();
    }

    public void recuperarTodosTiposOcorrenciaAPI() {
        Call<ListaTipoOcorrenciaAPI> listaTipoOcorrenciaAPICall = apiService.getTipoOcorrenciaEndPoint().tiposOcorrencia();
        listaTipoOcorrenciaAPICall.enqueue(new Callback<ListaTipoOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaTipoOcorrenciaAPI> call, Response<ListaTipoOcorrenciaAPI> response) {
                salvarTipoOcorrenciaRealm(response.body().getResults());
//                realmObjectsDAO.salvarListaRealm(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ListaTipoOcorrenciaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarTipoOcorrenciaRealm(List<TipoOcorrencia> ocorrencias) {
        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            realm.copyToRealmOrUpdate(ocorrencias.get(i));
        }
        realm.commitTransaction();
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
            }
        });
    }

    public void publicarOcorrenciaAPI() {
        RealmResults<Ocorrencia> ocorrencias = realm.where(Ocorrencia.class).findAll();

        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            if (ocorrencias.get(i).isNovo()) {
                ocorrencias.get(i).setNovo(false);
                Ocorrencia ocorrencia1 = new Ocorrencia();
                ocorrencia1.setDatahora(ocorrencias.get(i).getDatahora());
                ocorrencia1.setDatahoracadastro(ocorrencias.get(i).getDatahoracadastro());
                ocorrencia1.setDescricao(ocorrencias.get(i).getDescricao());
                ocorrencia1.setEnviadoSms(ocorrencias.get(i).isEnviadoSms());
                ocorrencia1.setResumoSms(ocorrencias.get(i).getResumoSms());
                ocorrencia1.setObservacao(ocorrencias.get(i).getObservacao());
                ocorrencia1.setNumeroTelefone(ocorrencias.get(i).getNumeroTelefone());
                ocorrencia1.setFuncionarioEscola(ocorrencias.get(i).getFuncionarioEscola());
                ocorrencia1.setMatriculaAluno(ocorrencias.get(i).getMatriculaAluno());
                ocorrencia1.setTipoOcorrencia(ocorrencias.get(i).getTipoOcorrencia());
                ocorrencia1.setAluno(ocorrencias.get(i).getAluno());
                ocorrencia1.setFuncionario(ocorrencias.get(i).getFuncionario());
                ocorrencia1.setUnidade(ocorrencias.get(i).getUnidade());
                ocorrencia1.setAnoLetivo(ocorrencias.get(i).getAnoLetivo());
                realmObjectsDAO.salvarRealm(ocorrencias.get(i));
                postOcorrenciaAPI(ocorrencia1);
            }
        }
        realm.commitTransaction();
    }
}

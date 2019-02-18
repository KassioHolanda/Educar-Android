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
import com.android.educar.educar.utils.Preferences;

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
    private int paginaAtualOcorrencia;
    private int paginaAtualTipoOcorrencia;
    private Preferences preferences;

    public OcorrenciaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        preferences = new Preferences(context);
        paginaAtualOcorrencia = 1;
        paginaAtualTipoOcorrencia = 1;
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarTodasOcorrenciasAPI() {
        Call<ListaOcorrenciaAPI> listaOcorrenciaAPICall = apiService.getOcorrenciaEndPoint().ocorrencias(paginaAtualOcorrencia);
        listaOcorrenciaAPICall.enqueue(new Callback<ListaOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaOcorrenciaAPI> call, Response<ListaOcorrenciaAPI> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();
                    if (response.body().getNext() != null) {
                        paginaAtualOcorrencia = paginaAtualOcorrencia + 1;
                        recuperarTodasOcorrenciasAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaOcorrenciaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarTodosTiposOcorrenciaAPI() {
        Call<ListaTipoOcorrenciaAPI> listaTipoOcorrenciaAPICall = apiService.getTipoOcorrenciaEndPoint().tiposOcorrencia(paginaAtualTipoOcorrencia);
        listaTipoOcorrenciaAPICall.enqueue(new Callback<ListaTipoOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaTipoOcorrenciaAPI> call, Response<ListaTipoOcorrenciaAPI> response) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body().getResults());
                realm.commitTransaction();
                if (response.body().getNext() != null) {
                    paginaAtualTipoOcorrencia = paginaAtualTipoOcorrencia + 1;
                    recuperarTodosTiposOcorrenciaAPI();
                }
                Log.i("RESPONSE", "TIPOS OCORRENCIA RECUPERADOS");
            }

            @Override
            public void onFailure(Call<ListaTipoOcorrenciaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarOcorrenciasAluno(long alunoId) {
        Call<List<Ocorrencia>> listCall = apiService.getOcorrenciaEndPoint().getOcorrenciasAluno(alunoId);
        listCall.enqueue(new Callback<List<Ocorrencia>>() {
            @Override
            public void onResponse(Call<List<Ocorrencia>> call, Response<List<Ocorrencia>> response) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                realm.commitTransaction();
                Log.i("RESPONSE", "TIPOS OCORRENCIA RECUPERADOS");
            }

            @Override
            public void onFailure(Call<List<Ocorrencia>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void postOcorrenciaAPI(final Ocorrencia ocorrencia) {
        Call<Ocorrencia> ocorrenciaCall = apiService.getOcorrenciaEndPoint().postOcorrencia(ocorrencia);
        ocorrenciaCall.enqueue(new Callback<Ocorrencia>() {
            @Override
            public void onResponse(Call<Ocorrencia> call, Response<Ocorrencia> response) {
                if (response.isSuccessful()) {

                    Log.i("RESPONSE", "OCORRENCIAS RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<Ocorrencia> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void publicarNovaOcorrenciaParaAPI() {
        RealmResults<Ocorrencia> ocorrencias = realm.where(Ocorrencia.class).findAll();

        for (int i = 0; i < ocorrencias.size(); i++) {
            if (ocorrencias.get(i).isNovo()) {
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

                realm.beginTransaction();
                ocorrencias.get(i).setNovo(false);
                realm.copyToRealmOrUpdate(ocorrencias.get(i));
                realm.commitTransaction();

                postOcorrenciaAPI(ocorrencia1);
//                preferences.saveInt("numero_registros_sincronizados", 1);
            }
        }
    }
}

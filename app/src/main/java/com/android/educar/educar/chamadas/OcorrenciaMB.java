package com.android.educar.educar.chamadas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaOcorrenciaAPI;
import com.android.educar.educar.service.ListaTipoOcorrenciaAPI;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OcorrenciaMB {
    private Context context;
    private APIService apiService;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    public OcorrenciaMB(Context context) {
        this.context = context;
        apiService = new APIService();
        configRealm();
        salvarDadosOcorrenciaApiBancoDeDados();
        salvarDadosTipoOcorrenciaApiBancoDeDados();
        publicarDados();
    }


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void salvarDadosOcorrenciaApiBancoDeDados() {
        Call<ListaOcorrenciaAPI> listaOcorrenciaAPICall = apiService.getOcorrenciaEndPoint().ocorrencias();
        listaOcorrenciaAPICall.enqueue(new Callback<ListaOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaOcorrenciaAPI> call, Response<ListaOcorrenciaAPI> response) {
                if (response.isSuccessful()) {
                    salvarOcorrenciaBD(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaOcorrenciaAPI> call, Throwable t) {

            }
        });
    }

    public void salvarDadosTipoOcorrenciaApiBancoDeDados() {
        Call<ListaTipoOcorrenciaAPI> listaTipoOcorrenciaAPICall = apiService.getTipoOcorrenciaEndPoint().tiposOcorrencia();
        listaTipoOcorrenciaAPICall.enqueue(new Callback<ListaTipoOcorrenciaAPI>() {
            @Override
            public void onResponse(Call<ListaTipoOcorrenciaAPI> call, Response<ListaTipoOcorrenciaAPI> response) {
                salvarTiposOcorrenciaBD(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ListaTipoOcorrenciaAPI> call, Throwable t) {

            }
        });
    }

    public void postOcorrenciaAPI(Ocorrencia ocorrencia) {
        Call<Ocorrencia> ocorrenciaCall = apiService.getOcorrenciaEndPoint().postOcorrencia(ocorrencia);
        ocorrenciaCall.enqueue(new Callback<Ocorrencia>() {
            @Override
            public void onResponse(Call<Ocorrencia> call, Response<Ocorrencia> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ocorrencia> call, Throwable t) {

            }
        });
    }

    public void salvarOcorrenciaBD(List<Ocorrencia> ocorrencias) {
        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
//            ocorrencias.get(i).setmId(realm.where(Ocorrencia.class).findAll().size() + 1);
            realm.copyToRealmOrUpdate(ocorrencias.get(i));
            Log.i("ocorrencia", "" + ocorrencias.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void salvarTiposOcorrenciaBD(List<TipoOcorrencia> ocorrencias) {
        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            realm.copyToRealmOrUpdate(ocorrencias.get(i));
            Log.i("tipoocorrencia", "" + ocorrencias.get(i).getDescricao());
        }
        realm.commitTransaction();
    }

    public void publicarDados() {
        realm.beginTransaction();
        RealmResults<Ocorrencia> ocorrencias = realm.where(Ocorrencia.class).findAll();
        for (int i = 0; i < ocorrencias.size(); i++) {
            if (ocorrencias.get(i).isNovo()) {

                ocorrencias.get(i).setNovo(false);
//                Ocorrencia ocorrencia1 = new Ocorrencia();
//                ocorrencia1.setDatahora(ocorrencias.get(i).getDatahora());
//                ocorrencia1.setDataHoraCadastro(ocorrencias.get(i).getDataHoraCadastro());
//                ocorrencia1.setDescricao(ocorrencias.get(i).getDescricao());
//                ocorrencia1.setEnviadoSms(ocorrencias.get(i).isEnviadoSms());
//                ocorrencia1.setResumoSms(ocorrencias.get(i).getResumoSms());
//                ocorrencia1.setObservacao(ocorrencias.get(i).getObservacao());
//                ocorrencia1.setNumeroTelefone(ocorrencias.get(i).getNumeroTelefone());
//                ocorrencia1.setFuncionarioEscola(ocorrencias.get(i).getFuncionarioEscola());
//                ocorrencia1.setMatriculaAluno(ocorrencias.get(i).getMatriculaAluno());
//                ocorrencia1.setTipoOcorrencia(ocorrencias.get(i).getTipoOcorrencia());
//                ocorrencia1.setAluno(ocorrencias.get(i).getAluno());
//                ocorrencia1.setFuncionario(ocorrencias.get(i).getFuncionario());
//                ocorrencia1.setUnidade(ocorrencias.get(i).getUnidade());
//                ocorrencia1.setAnoLetivo(ocorrencias.get(i).getAnoLetivo());

                postOcorrenciaAPI(ocorrencias.get(i));
            }
        }
        realm.commitTransaction();
    }

}

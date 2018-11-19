package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.bo.RealmBO;
import com.android.educar.educar.chamadas.OcorrenciaChamada;
import com.android.educar.educar.model.Ocorrencia;

import io.realm.Realm;
import io.realm.RealmResults;

public class SincronizarOcorrenciaMB {

    private RealmBO realmBO;
    private Realm realm;
    private Context context;
    private OcorrenciaChamada ocorrenciaChamada;

    public SincronizarOcorrenciaMB(Context context) {
        this.context = context;
        this.realmBO = new RealmBO(context);
        this.ocorrenciaChamada = new OcorrenciaChamada(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void publicarDadosRealmParaAPI() {
        RealmResults<Ocorrencia> ocorrencias = realm.where(Ocorrencia.class).findAll();

        realm.beginTransaction();
        for (int i = 0; i < ocorrencias.size(); i++) {
            if (ocorrencias.get(i).isNovo()) {
                ocorrencias.get(i).setNovo(false);
//                Ocorrencia ocorrencia1 = new Ocorrencia();
//                ocorrencia1.setDatahora(ocorrencias.get(i).getDatahora());
//                ocorrencia1.setDatahoracadastro(ocorrencias.get(i).getDatahoracadastro());
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
//                realmBO.salvarRealm(ocorrencias.get(i));
                ocorrenciaChamada.postOcorrenciaAPI(ocorrencias.get(i));
            }
        }
        realm.commitTransaction();
    }
}

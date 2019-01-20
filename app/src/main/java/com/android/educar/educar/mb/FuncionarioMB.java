package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;

import io.realm.Realm;

public class FuncionarioMB {
    private FuncionarioChamada funcionarioChamada;
    private Context context;
    private Realm realm;


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public FuncionarioMB(Context context){
        this.context = context;
        this.funcionarioChamada = new FuncionarioChamada(context);
        configRealm();
    }


    public void recuperarFuncionarioUsuario(long pessoaFisicaId) {
        funcionarioChamada.recuperarFuncionarioPessoaFisicaAPI(pessoaFisicaId);
    }

}

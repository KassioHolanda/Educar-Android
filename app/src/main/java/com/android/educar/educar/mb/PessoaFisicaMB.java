package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.network.chamadas.FuncionarioChamada;
import com.android.educar.educar.network.chamadas.PessoaChamada;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

public class PessoaFisicaMB {
    private PessoaChamada pessoaChamada;
    private Context context;
    private Realm realm;

    public PessoaFisicaMB(Context context) {
        this.context = context;
        pessoaChamada = new PessoaChamada(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public void recuperarPessoaFisicaPeloCPF(String cpf) {
        pessoaChamada.pessoaFisicaCPF(cpf);
    }

    public void recuperarPerfil(long perfil) {
        pessoaChamada.recuperarPerfilUsuario(perfil);
    }

    public void recuperarUsuario(long pessoaFisicaId) {
        pessoaChamada.recuperarUsuarioPessoaFisica(pessoaFisicaId);
    }
}


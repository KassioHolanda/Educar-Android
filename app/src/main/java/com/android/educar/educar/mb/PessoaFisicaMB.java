package com.android.educar.educar.mb;

import android.content.Context;

import com.android.educar.educar.network.chamadas.PessoaChamada;

import io.realm.Realm;

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

    public void recuperarPerfil(Long perfil) {
        pessoaChamada.recuperarPerfilUsuario(perfil);
    }

    public void recuperarUsuario(Long pessoaFisicaId) {
        pessoaChamada.recuperarUsuarioPessoaFisica(pessoaFisicaId);
    }
}


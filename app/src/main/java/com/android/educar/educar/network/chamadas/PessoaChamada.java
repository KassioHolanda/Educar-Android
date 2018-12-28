package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaPerfilAPI;
import com.android.educar.educar.network.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.network.service.ListaUsuariosAPI;


import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaChamada {

    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public PessoaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
    }

    public void pessoaFisicaAPI() {
        Call<ListaPessoaFisicaAPI> listaProfessoresAPICall = apiService.getPessoaFisicaEndPoint().pessoasFisicas();
        listaProfessoresAPICall.enqueue(new Callback<ListaPessoaFisicaAPI>() {
            @Override
            public void onResponse(Call<ListaPessoaFisicaAPI> call, Response<ListaPessoaFisicaAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarPessoaFisicaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaPessoaFisicaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarPessoaFisicaRealm(List<PessoaFisica> pessoaFisicas) {
        for (int i = 0; i < pessoaFisicas.size(); i++) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(pessoaFisicas.get(i));
            realm.commitTransaction();
        }
    }

    public void recuperarUsuariosAPI() {
        Call<ListaUsuariosAPI> listaProfessoresAPICall = apiService.getUsuarioEndPoint().usuarios();
        listaProfessoresAPICall.enqueue(new Callback<ListaUsuariosAPI>() {
            @Override
            public void onResponse(Call<ListaUsuariosAPI> call, Response<ListaUsuariosAPI> response) {
                if (response.isSuccessful()) {
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                    salvarUsuarioaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaUsuariosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarUsuarioaRealm(List<Usuario> usuarios) {
        for (int i = 0; i < usuarios.size(); i++) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(usuarios.get(i));
            realm.commitTransaction();
        }
    }

    public void recuperarPerfilAPI() {
        Call<ListaPerfilAPI> listaProfessoresAPICall = apiService.getPerfilEndPoint().perfis();
        listaProfessoresAPICall.enqueue(new Callback<ListaPerfilAPI>() {
            @Override
            public void onResponse(Call<ListaPerfilAPI> call, Response<ListaPerfilAPI> response) {
                if (response.isSuccessful()) {
                    salvarPerfilRealm(response.body().getResults());
//                    realmObjectsDAO.salvarListaRealm(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<ListaPerfilAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarPerfilRealm(List<Perfil> perfils) {
        for (int i = 0; i < perfils.size(); i++) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(perfils.get(i));
            realm.commitTransaction();
        }
    }
}

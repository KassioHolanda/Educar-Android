package com.android.educar.educar.network.chamadas;

import android.app.ProgressDialog;
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


import java.util.ArrayList;
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

    private List<PessoaFisica> pessoaFisicas;

    private int paginaAtualPessoaFisica;
    private int paginaAtualPerfil;
    private int paginaAtualUsuario;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public PessoaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        paginaAtualPessoaFisica = 1;
        paginaAtualPerfil = 1;
        paginaAtualUsuario = 1;
        pessoaFisicas = new ArrayList<>();
    }

    public void pessoaFisicaAPI() {
        Call<ListaPessoaFisicaAPI> listaProfessoresAPICall = apiService.getPessoaFisicaEndPoint().pessoasFisicasComPaginacao(paginaAtualPessoaFisica);
        listaProfessoresAPICall.enqueue(new Callback<ListaPessoaFisicaAPI>() {
            @Override
            public void onResponse(Call<ListaPessoaFisicaAPI> call, Response<ListaPessoaFisicaAPI> response) {

                if (response.isSuccessful()) {

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();

                    if (response.body().getNext() != null) {
                        paginaAtualPessoaFisica = paginaAtualPessoaFisica + 1;
                        pessoaFisicaAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaPessoaFisicaAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarUsuariosAPI() {
        Call<ListaUsuariosAPI> listaProfessoresAPICall = apiService.getUsuarioEndPoint().usuariosPaginacao(paginaAtualUsuario);
        listaProfessoresAPICall.enqueue(new Callback<ListaUsuariosAPI>() {
            @Override
            public void onResponse(Call<ListaUsuariosAPI> call, Response<ListaUsuariosAPI> response) {
                if (response.isSuccessful()) {

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();

                    if (response.body().getNext() != null) {
                        paginaAtualUsuario = paginaAtualUsuario + 1;
                        recuperarUsuariosAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaUsuariosAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void recuperarPerfilAPI() {
        Call<ListaPerfilAPI> listaProfessoresAPICall = apiService.getPerfilEndPoint().perfisPaginacao(paginaAtualPerfil);
        listaProfessoresAPICall.enqueue(new Callback<ListaPerfilAPI>() {
            @Override
            public void onResponse(Call<ListaPerfilAPI> call, Response<ListaPerfilAPI> response) {
                if (response.isSuccessful()) {

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body().getResults());
                    realm.commitTransaction();

                    if (response.body().getNext() != null) {
                        paginaAtualPerfil = paginaAtualPerfil + 1;
                        recuperarPerfilAPI();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListaPerfilAPI> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }
}

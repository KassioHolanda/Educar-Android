package com.android.educar.educar.network.chamadas;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaPerfilAPI;
import com.android.educar.educar.network.service.ListaPessoaFisicaAPI;
import com.android.educar.educar.network.service.ListaUsuariosAPI;
import com.android.educar.educar.utils.Preferences;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaChamada {

    private Context context;
    private APIService apiService;
    private RealmObjectsDAO realmObjectsDAO;
    private Realm realm;
    private Preferences preferences;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public PessoaChamada(Context context) {
        this.context = context;
        apiService = new APIService("");
        realmObjectsDAO = new RealmObjectsDAO(context);
        configRealm();
        preferences = new Preferences(context);
    }

    public void pessoaFisicaCPF(String cpf) {
        Call<List<PessoaFisica>> pessoaFisicaCall = apiService.getPessoaFisicaEndPoint().getPessoaFisicaCpf(cpf);
        pessoaFisicaCall.enqueue(new Callback<List<PessoaFisica>>() {
            @Override
            public void onResponse(Call<List<PessoaFisica>> call, Response<List<PessoaFisica>> response) {
                salvarPessoa(response.body());
            }

            @Override
            public void onFailure(Call<List<PessoaFisica>> call, Throwable t) {
                Log.i("ERRO RETROFIT", "" + t.getMessage());
            }
        });
    }

    public void salvarPessoa(List<PessoaFisica> objects) {
        try {
            realmObjectsDAO.salvarRealm(objects.get(0));
            preferences.saveLong("id_pessoafisica", objects.get(0).getId());
            preferences.saveBoolean("pessoafisica_encontrado", true);
            preferences.saveString("pessoafisica_senha", objects.get(0).getSenha());
        } catch (IndexOutOfBoundsException e) {

        }

    }

    public void recuperarUsuarioPessoaFisica(long pessoaFisica) {
        Call<List<Usuario>> usuarioCall = apiService.getUsuarioEndPoint().getUsuarioPessoaFisica(pessoaFisica);
        usuarioCall.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    salvarUsuario(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarUsuario(List<Usuario> usuarios) {
        try {
            realmObjectsDAO.salvarRealm(usuarios.get(0));
            preferences.saveLong("id_usuario", usuarios.get(0).getId());
            preferences.saveLong("usuario_perfil", usuarios.get(0).getPerfil());
            preferences.saveBoolean("usuario_encontrado", true);
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(context, "Solicite Adminstador", Toast.LENGTH_LONG).show();
            Log.e("ERRO_API", e.getMessage());
        }
    }


//    public void recuperarPessoaFisicaAPI() {
//        Call<ListaPessoaFisicaAPI> listaProfessoresAPICall = apiService.getPessoaFisicaEndPoint().pessoasFisicasComPaginacao(paginaAtualPessoaFisica);
//        listaProfessoresAPICall.enqueue(new Callback<ListaPessoaFisicaAPI>() {
//            @Override
//            public void onResponse(Call<ListaPessoaFisicaAPI> call, Response<ListaPessoaFisicaAPI> response) {
//                if (response.isSuccessful()) {
//
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(response.body().getResults());
//                    realm.commitTransaction();
//
//                    if (response.body().getNext() != null) {
//                        paginaAtualPessoaFisica = paginaAtualPessoaFisica + 1;
//                        recuperarUsuariosAPI();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListaPessoaFisicaAPI> call, Throwable t) {
//                Log.i("ERRO API", t.getMessage());
//            }
//        });
//    }


//    public void recuperarUsuariosAPI() {
//        Call<ListaUsuariosAPI> listaProfessoresAPICall = apiService.getUsuarioEndPoint().usuariosPaginacao(paginaAtualUsuario);
//        listaProfessoresAPICall.enqueue(new Callback<ListaUsuariosAPI>() {
//            @Override
//            public void onResponse(Call<ListaUsuariosAPI> call, Response<ListaUsuariosAPI> response) {
//                if (response.isSuccessful()) {
//
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(response.body().getResults());
//                    realm.commitTransaction();
//
//                    if (response.body().getNext() != null) {
//                        paginaAtualUsuario = paginaAtualUsuario + 1;
//                        recuperarUsuariosAPI();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListaUsuariosAPI> call, Throwable t) {
//                Log.i("ERRO API", t.getMessage());
//            }
//        });
//    }

    public void recuperarPerfilUsuario(long id) {
        Call<List<Perfil>> perfilCall = apiService.getPerfilEndPoint().getPerfil(id);
        perfilCall.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (response.isSuccessful()) {
                    salvarPerfil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());

            }
        });
    }

    public void salvarPerfil(List<Perfil> perfils) {
        try {
            realmObjectsDAO.salvarRealm(perfils.get(0));
            preferences.saveLong("id_perfil", perfils.get(0).getId());
            preferences.saveBoolean("usuario_encontrado", true);
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(context, "Solicite Administrador", Toast.LENGTH_LONG).show();
            Log.i("ERRo_API", e.getMessage());
        }

    }

    public void recuperarPessoaAlunos() {
        RealmResults<Aluno> alunos = realm.where(Aluno.class).findAll();
        for (int i = 0; i < alunos.size(); i++) {
            recuperarPessoaAlunos(alunos.get(i).getPessoaFisica());
        }
    }

    private void recuperarPessoaAlunos(long pessoaFisica) {
        Call<PessoaFisica> pessoaFisicaCall = apiService.getPessoaFisicaEndPoint().getPessoaFisica(pessoaFisica);
        pessoaFisicaCall.enqueue(new Callback<PessoaFisica>() {
            @Override
            public void onResponse(Call<PessoaFisica> call, Response<PessoaFisica> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<PessoaFisica> call, Throwable t) {

            }
        });
    }
//
//    public void recuperarPerfilAPI() {
//        Call<ListaPerfilAPI> listaProfessoresAPICall = apiService.getPerfilEndPoint().perfisPaginacao(paginaAtualPerfil);
//        listaProfessoresAPICall.enqueue(new Callback<ListaPerfilAPI>() {
//            @Override
//            public void onResponse(Call<ListaPerfilAPI> call, Response<ListaPerfilAPI> response) {
//                if (response.isSuccessful()) {
//
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(response.body().getResults());
//                    realm.commitTransaction();
//
//                    if (response.body().getNext() != null) {
//                        paginaAtualPerfil = paginaAtualPerfil + 1;
//                        recuperarPerfilAPI();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListaPerfilAPI> call, Throwable t) {
//                Log.i("ERRO API", t.getMessage());
//            }
//        });
//    }
}

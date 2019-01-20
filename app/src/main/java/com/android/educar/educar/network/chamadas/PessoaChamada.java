package com.android.educar.educar.network.chamadas;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.mb.FuncionarioMB;
import com.android.educar.educar.mb.PessoaFisicaMB;
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

//    private PessoaFisicaMB pessoaFisicaMB;
//    private FuncionarioMB funcionarioMB;

    private FuncionarioChamada funcionarioChamada;


    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public PessoaChamada(Context context) {
        this.context = context;
        funcionarioChamada = new FuncionarioChamada(context);
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
                if (response.isSuccessful()) {
                    salvarPessoaFisicaRecuperadaAPI(response.body());
                    try {
                        funcionarioChamada.recuperarFuncionarioPessoaFisicaAPI(response.body().get(0).getId());
                        recuperarUsuarioPessoaFisica(response.body().get(0).getId());
                        recuperarPerfilUsuario(response.body().get(0).getId());
                        Log.i("RESPONSE", "PESSOAFISICA RECUPERADA LOGIN");
                    } catch (IndexOutOfBoundsException e) {
                        Log.i("ERRO", "CPF NAO ENCONTRADO");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PessoaFisica>> call, Throwable t) {
                Log.i("ERRO RETROFIT", "" + t.getMessage());
            }
        });
    }


    public void salvarPessoaFisicaRecuperadaAPI(List<PessoaFisica> objects) {
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
                    Log.i("RESPONSE", "USUARIO RECUPERADO");
                    try {
                        recuperarPerfilUsuario(response.body().get(0).getPerfil());
                    } catch (IndexOutOfBoundsException e) {
//                        Toast.makeText(context, "CPF Invalido!", Toast.LENGTH_LONG).show();
                    }

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
//            Toast.makeText(context, "Solicite Adminstador", Toast.LENGTH_LONG).show();
            Log.e("ERRO_API", e.getMessage());
        }
    }

    public void recuperarPerfilUsuario(long id) {
        Call<List<Perfil>> perfilCall = apiService.getPerfilEndPoint().getPerfil(id);
        perfilCall.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (response.isSuccessful()) {
                    salvarPerfil(response.body());
                    Log.i("RESPONSE", "PERFIL RECUPERADO");
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

    public void recuperarPessoaFisicaAluno(long pessoaFisica) {
        Call<PessoaFisica> pessoaFisicaCall = apiService.getPessoaFisicaEndPoint().getPessoaFisica(pessoaFisica);
        pessoaFisicaCall.enqueue(new Callback<PessoaFisica>() {
            @Override
            public void onResponse(Call<PessoaFisica> call, Response<PessoaFisica> response) {
                if (response.isSuccessful()) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    Log.i("RESPONSE", "PESSOAFISICA RECUPERADAS");
                }
            }

            @Override
            public void onFailure(Call<PessoaFisica> call, Throwable t) {

            }
        });
    }
}

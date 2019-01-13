package com.android.educar.educar.network.chamadas;

import android.content.Context;
import android.util.Log;

import com.android.educar.educar.dao.RealmObjectsDAO;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.service.APIService;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;
import com.android.educar.educar.utils.Preferences;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioChamada {

    private APIService apiService;
    private Context context;
    private Preferences preferences;
    private RealmObjectsDAO realmObjectsDAO;

    public FuncionarioChamada(Context context) {
        apiService = new APIService("");
        this.context = context;
        preferences = new Preferences(context);
        realmObjectsDAO = new RealmObjectsDAO(context);
    }

    public void recuperarFuncionarioPessoaFisicaAPI(long pessoafisica) {
        Call<List<Funcionario>> funcionarioCall = apiService.getFuncionarioEndPoint().getFuncionarioPessoaFisica(pessoafisica);
        funcionarioCall.enqueue(new Callback<List<Funcionario>>() {
            @Override
            public void onResponse(Call<List<Funcionario>> call, Response<List<Funcionario>> response) {
                if (response.isSuccessful()) {
                    salvarFuncionario(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Funcionario>> call, Throwable t) {
                Log.i("ERRO API", t.getMessage());
            }
        });
    }

    public void salvarFuncionario(List<Funcionario> funcionarios) {
        realmObjectsDAO.salvarRealm(funcionarios.get(0));
        preferences.saveBoolean("funcionario_encontrado", true);
        preferences.saveLong("id_funcionario", funcionarios.get(0).getId());
        preferences.saveLong("funcionario_cargo", funcionarios.get(0).getCargo());
    }

//    public void recuperarFuncionariosAPI() {
//        final Call<ListaFuncionariosAPI> listaProfessoresAPICall = apiService.getFuncionarioEndPoint().funcionarios(paginaAtual);
//        listaProfessoresAPICall.enqueue(new Callback<ListaFuncionariosAPI>() {
//            @Override
//            public void onResponse(Call<ListaFuncionariosAPI> call, Response<ListaFuncionariosAPI> response) {
//                if (response.isSuccessful()) {
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(response.body().getResults());
//                    realm.commitTransaction();
//
//                    if (response.body().getNext() != null) {
//                        paginaAtual = paginaAtual + 1;
//                        recuperarFuncionariosAPI();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListaFuncionariosAPI> call, Throwable t) {
//                Log.i("ERRO API", t.getMessage());
//            }
//        });
//    }
}

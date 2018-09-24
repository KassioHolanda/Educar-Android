package com.android.educar.educar.chamadas;

import android.content.Context;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaFuncionariosAPI;
import com.android.educar.educar.service.ListaProfessoresAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioChamada {

    private APIService apiService;
    private List<Funcionario> funcionarioList;

    public FuncionarioChamada() {
        apiService = new APIService("");
        funcionarioList = new ArrayList<>();
    }

    public void professoresApi() {
        Call<ListaFuncionariosAPI> listaProfessoresAPICall = apiService.getFuncionarioEndPoint().funcionarios();
        listaProfessoresAPICall.enqueue(new Callback<ListaFuncionariosAPI>() {
            @Override
            public void onResponse(Call<ListaFuncionariosAPI> call, Response<ListaFuncionariosAPI> response) {
                if (response.isSuccessful()) {
                    funcionarioList = response.body().getResults();

                }
            }

            @Override
            public void onFailure(Call<ListaFuncionariosAPI> call, Throwable t) {

            }
        });

    }
}

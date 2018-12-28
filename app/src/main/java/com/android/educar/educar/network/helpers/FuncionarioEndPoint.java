package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface FuncionarioEndPoint {
    @GET("funcionario/")
    Call<ListaFuncionariosAPI> funcionarios();

    @GET("funcionario/{id}")
    Call<Funcionario> getFuncionario(@Path("id") long id);
}

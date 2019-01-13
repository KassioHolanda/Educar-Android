package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FuncionarioEndPoint {
    @GET("funcionario/")
    Call<ListaFuncionariosAPI> funcionarios();

    @GET("funcionario/id={id}/")
    Call<Funcionario> getFuncionario(@Path("id") long id);

    @GET("funcionario/pessoafisica={pessoafisica}/")
    Call<List<Funcionario>> getFuncionarioPessoaFisica(@Path("pessoafisica") long pessoafisica);

    @GET("funcionario/")
    Call<ListaFuncionariosAPI> funcionarios(@Query("page") int page);
}

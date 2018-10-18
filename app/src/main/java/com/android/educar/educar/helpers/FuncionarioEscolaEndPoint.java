package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.service.ListaFuncionarioEscolaAPI;
import com.android.educar.educar.service.ListaFuncionariosAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FuncionarioEscolaEndPoint {
    @GET("funcionarioescola/")
    Call<ListaFuncionarioEscolaAPI> funcionariosEscola();

    @GET("funcionarioescola/{id}")
    Call<FuncionarioEscola> getFuncionarioEscola(@Path("id") long id);
}

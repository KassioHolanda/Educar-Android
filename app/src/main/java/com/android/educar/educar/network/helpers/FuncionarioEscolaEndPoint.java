package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.FuncionarioEscola;
import com.android.educar.educar.network.service.ListaFuncionarioEscolaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FuncionarioEscolaEndPoint {
    @GET("funcionarioescola/")
    Call<ListaFuncionarioEscolaAPI> funcionariosEscola();

    @GET("funcionarioescola/{id}")
    Call<FuncionarioEscola> getFuncionarioEscola(@Path("id") Long id);

    @GET("funcionarioescola/")
    Call<ListaFuncionarioEscolaAPI> funcionariosEscola(@Query("page") int page);

    @GET("funcionarioescola/funcionario={funcionario}/")
    Call<List<FuncionarioEscola>> funcionariosEscola(@Path("funcionario") Long funcionario);
}

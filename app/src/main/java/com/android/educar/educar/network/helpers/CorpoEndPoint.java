package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Funcionario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CorpoEndPoint {
    @GET("classescompletas/cpf={cpf}/")
    Call<List<Funcionario>> getFuncionario(@Path("cpf") String cpf);
}


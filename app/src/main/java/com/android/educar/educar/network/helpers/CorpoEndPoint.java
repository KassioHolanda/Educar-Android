package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.network.service.ListaDisciplinaAlunoAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CorpoEndPoint {
    @GET("classescompletas/cpf={cpf}/")
    Call<List<Funcionario>> getFuncionario(@Path("cpf") String cpf);
}


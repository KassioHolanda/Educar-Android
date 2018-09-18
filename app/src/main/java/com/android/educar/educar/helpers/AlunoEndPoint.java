package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.service.ListaAlunosAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlunoEndPoint {

    @GET("alunos/")
    Call<ListaAlunosAPI> alunos();

    @GET("alunos/{pk}")
    Call<Aluno> getAluno(@Path("pk") long pk);

}

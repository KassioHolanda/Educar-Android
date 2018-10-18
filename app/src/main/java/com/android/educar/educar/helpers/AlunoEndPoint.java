package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.service.ListaAlunosAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlunoEndPoint {

    @GET("aluno/")
    Call<ListaAlunosAPI> alunos();

    @GET("aluno/{id}")
    Call<Aluno> getAluno(@Path("id") long id);

}

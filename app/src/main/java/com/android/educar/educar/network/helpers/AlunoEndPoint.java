package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Aluno;
import com.android.educar.educar.network.service.ListaAlunosAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlunoEndPoint {

    @GET("aluno/")
    Call<ListaAlunosAPI> alunos();

    @GET("aluno/id={id}/")
    Call<Aluno> getAluno(@Path("id") Long id);

    @GET("aluno/")
    Call<ListaAlunosAPI> alunos(@Query("page") int page);

    @GET("aluno/matricula={matricula}/")
    Call<List<Aluno>> alunosMatricula(@Path("matricula") Long matricula);
}

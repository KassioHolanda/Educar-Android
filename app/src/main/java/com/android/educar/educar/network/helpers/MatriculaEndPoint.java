package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.network.service.ListaMatriculaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatriculaEndPoint {
    @GET("matricula/")
    Call<ListaMatriculaAPI> matriculas();

    @GET("matricula/id={id}/")
    Call<Matricula> getMatricula(@Path("id") Long id);

    @GET("matriculaSerializadaCompleta/id={id}/")
    Call<Matricula> getAlunoNotaMesEAlunoFrequenciaMes(@Path("id") Long id);

    @GET("matricula/")
    Call<ListaMatriculaAPI> matriculas(@Query("page") int page);

    @GET("matricula/turma={turma}/")
    Call<List<Matricula>> getmatriculas(@Path("turma") Long turma);
}

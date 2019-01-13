package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Turma;
import com.android.educar.educar.network.service.ListaTurmaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TurmaEndPoint {

    @GET("turma/")
    Call<ListaTurmaAPI> turmas();

    @GET("turma/{pk}")
    Call<Turma> getTurma(@Path("id") long id);

    @GET("turma/")
    Call<ListaTurmaAPI> turmas(@Query("page") int page);

//   Dados recuperados para turmas no nivel fundamental e turmas cadastradas apenas
    @GET("turma/sala={sala}/")
    Call<List<Turma>> turmasUnidade(@Path("sala") long sala);
}

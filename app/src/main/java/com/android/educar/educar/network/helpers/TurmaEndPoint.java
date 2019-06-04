package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.network.service.ListaTurmaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TurmaEndPoint {

    @GET("turma/")
    Call<ListaTurmaAPI> turmas();

    @GET("turma/id={id}/")
    Call<Turma> getTurma(@Path("id") Long id);

    @GET("turmaSerializadaCompleta/id={id}/")
    Call<Turma> getTurmaCompleta(@Path("id") Long id);

    @GET("turma/")
    Call<ListaTurmaAPI> turmas(@Query("page") int page);

//   Dados recuperados para turmas no nivel fundamental e turmas cadastradas apenas
    @GET("turma/sala={sala}/")
    Call<List<Turma>> turmasUnidade(@Path("sala") Long sala);
}

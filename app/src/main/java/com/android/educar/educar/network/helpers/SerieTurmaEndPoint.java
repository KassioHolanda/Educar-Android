package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.SerieTurma;
import com.android.educar.educar.network.service.ListaSerieTurmaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SerieTurmaEndPoint {
    @GET("serieturma/")
    Call<ListaSerieTurmaAPI> seriesturma();

    @GET("serieturma/id={id}")
    Call<SerieTurma> getSerieTurma(@Path("id") Long id);

    @GET("serieturma/")
    Call<ListaSerieTurmaAPI> seriesturma(@Query("page")int page);

    @GET("serieturma/turma={turma}/")
    Call<List<SerieTurma>> seriesTurma(@Path("turma")Long turma);
}

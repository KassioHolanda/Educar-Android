package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.SerieDisciplina;
import com.android.educar.educar.network.service.ListaSerieDisciplinaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SerieDisciplinaEndPoint {
    @GET("seriedisciplina/")
    Call<ListaSerieDisciplinaAPI> serieDisciplinas();

    @GET("seriedisciplina/id={id}/")
    Call<SerieDisciplina> getSerieDisciplinas(@Path("id") Long id);

    @GET("seriedisciplina/disciplina={disciplina}/")
    Call<List<SerieDisciplina>> serieDisciplinas(@Path("disciplina") Long disciplina);

    @GET("seriedisciplina/")
    Call<ListaSerieDisciplinaAPI> serieDisciplinas(@Query("page") int page);
}

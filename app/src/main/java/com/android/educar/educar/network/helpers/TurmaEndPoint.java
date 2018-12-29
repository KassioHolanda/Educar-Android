package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Turma;
import com.android.educar.educar.network.service.ListaTurmaAPI;

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
    Call<ListaTurmaAPI> turmas(@Query("page")int page);
}

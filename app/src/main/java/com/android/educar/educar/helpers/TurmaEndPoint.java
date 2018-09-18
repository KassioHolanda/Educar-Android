package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Turma;
import com.android.educar.educar.service.ListaTurmaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TurmaEndPoint {

    @GET("turmas/")
    Call<ListaTurmaAPI> turmas();

    @GET("turmas/{pk}")
    Call<Turma> getTurma(@Path("pk") long pk);

}

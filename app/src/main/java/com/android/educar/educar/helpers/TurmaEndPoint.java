package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Turma;
import com.android.educar.educar.service.ListaTurmaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TurmaEndPoint {

    @GET("turma/")
    Call<ListaTurmaAPI> turmas();

    @GET("turma/{pk}")
    Call<Turma> getTurma(@Path("id") long id);

}

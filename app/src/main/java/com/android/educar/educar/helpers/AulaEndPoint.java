package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Aula;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AulaEndPoint {

    @GET("aulas/")
    Call<Aula> aulas();

    @GET("aulas/{pk}")
    Call<Aula> getAula(@Path("pk") long pk);

}

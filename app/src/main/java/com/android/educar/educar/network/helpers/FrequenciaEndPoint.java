package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Frequencia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FrequenciaEndPoint {

    @GET("frequencias/")
    Call<Frequencia> frequencias();

    @GET("frequencias/{pk}")
    Call<Frequencia> getFrequencia(@Path("pk") long pk);

}

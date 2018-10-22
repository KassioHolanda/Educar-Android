package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Nota;
import com.android.educar.educar.model.Ocorrencia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OcorrenciaEndPoint {
    @GET("ocorrencia/")
    Call<Ocorrencia> ocorrencias();

    @GET("ocorrencia/{id}")
    Call<Ocorrencia> getOcorrencias(@Path("id") long id);

    @POST
    Call<Ocorrencia> postOcorrencia(@Body Ocorrencia ocorrencia);
}


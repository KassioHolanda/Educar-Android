package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Nota;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.network.service.ListaOcorrenciaAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OcorrenciaEndPoint {
    @GET("ocorrencia/")
    Call<ListaOcorrenciaAPI> ocorrencias();

    @GET("ocorrencia/{id}")
    Call<Ocorrencia> getOcorrencias(@Path("id") Long id);

    @POST("ocorrencia/")
    Call<Ocorrencia> postOcorrencia(@Body Ocorrencia ocorrencia);

    @GET("ocorrencia/")
    Call<ListaOcorrenciaAPI> ocorrencias(@Query("page") int page);
}


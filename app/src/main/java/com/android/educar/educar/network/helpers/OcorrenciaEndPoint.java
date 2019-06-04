package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Ocorrencia;
import com.android.educar.educar.network.service.ListaOcorrenciaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OcorrenciaEndPoint {
    @GET("ocorrencia/")
    Call<ListaOcorrenciaAPI> ocorrencias();

    @GET("ocorrencia/id={id}/")
    Call<Ocorrencia> getOcorrencias(@Path("id") Long id);

    @GET("ocorrencia/aluno={aluno}/")
    Call<List<Ocorrencia>> getOcorrenciasAluno(@Path("aluno") Long aluno);

    @POST("ocorrencia/")
    Call<Ocorrencia> postOcorrencia(@Body Ocorrencia ocorrencia);

    @GET("ocorrencia/")
    Call<ListaOcorrenciaAPI> ocorrencias(@Query("page") int page);
}


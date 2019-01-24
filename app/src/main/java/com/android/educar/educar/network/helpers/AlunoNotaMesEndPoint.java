package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.network.service.ListaAlunoFrequenciaMesAPI;
import com.android.educar.educar.network.service.ListaAlunoNotaMesAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlunoNotaMesEndPoint {
    @GET("alunonotames/")
    Call<ListaAlunoNotaMesAPI> alunosNotaMes();

    @GET("alunonotames/{id}/")
    Call<AlunoNotaMes> getAlunoNotaMes(@Path("id") Long id);

    @POST("alunonotames/")
    Call<AlunoNotaMes> postAlunoNotaMes(@Body AlunoNotaMes alunoNotaMes);

    @GET("alunonotames/")
    Call<ListaAlunoNotaMesAPI> alunosNotaMes(@Query("page") int page);
}

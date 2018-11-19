package com.android.educar.educar.helpers;

import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.service.ListaAlunoFrequenciaMesAPI;
import com.android.educar.educar.service.ListaAlunoNotaMesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlunoNotaMesEndPoint {
    @GET("alunonotames/")
    Call<ListaAlunoNotaMesAPI> alunosNotaMes();

    @GET("alunonotames/{id}")
    Call<AlunoNotaMes> getAlunoNotaMes(@Path("id") long id);
}

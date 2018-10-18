package com.android.educar.educar.helpers;

import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.service.ListaAlunoFrequenciaMesAPI;
import com.android.educar.educar.service.ListaDisciplinasAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlunoFrequenciaMesEndPoint {

    @GET("alunofrequenciames/")
    Call<ListaAlunoFrequenciaMesAPI> alunosFrequenciaMes();

    @GET("alunofrequenciames/{id}")
    Call<AlunoFrequenciaMes> getAlunoFrequenciaMes(@Path("id") long id);

}

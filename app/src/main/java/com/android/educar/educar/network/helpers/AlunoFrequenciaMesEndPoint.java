package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.network.service.ListaAlunoFrequenciaMesAPI;
import com.android.educar.educar.network.service.ListaDisciplinasAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlunoFrequenciaMesEndPoint {

    @GET("alunofrequenciames/")
    Call<ListaAlunoFrequenciaMesAPI> alunosFrequenciaMes();

    @GET("alunofrequenciames/{id}")
    Call<AlunoFrequenciaMes> getAlunoFrequenciaMes(@Path("id") long id);

    @GET("alunofrequenciames/matricula={matricula}/")
    Call<List<AlunoFrequenciaMes>> alunoFrequenciaMes(@Path("matricula") long matricula);

    @GET("alunofrequenciames/")
    Call<ListaAlunoFrequenciaMesAPI> alunosFrequenciaMes(@Query("page") int page);

    @PUT("alunofrequenciames/id={id}/")
    Call<AlunoFrequenciaMes> postAlunoFrequenciaMes(@Path("id") long id, @Body AlunoFrequenciaMes alunoFrequenciaMes);

}

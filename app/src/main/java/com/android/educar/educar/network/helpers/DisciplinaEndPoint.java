package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.network.service.ListaAlunosAPI;
import com.android.educar.educar.network.service.ListaDisciplinasAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DisciplinaEndPoint {

    @GET("disciplina/")
    Call<ListaDisciplinasAPI> disciplinas();

    @GET("disciplina/id={id}/")
    Call<Disciplina> getDisciplina(@Path("id") Long id);

    @GET("disciplina/")
    Call<ListaDisciplinasAPI> disciplinas(@Query("page") int page);



}

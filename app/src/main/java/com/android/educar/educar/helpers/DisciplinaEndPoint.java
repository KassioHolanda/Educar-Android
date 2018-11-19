package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaDisciplinasAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DisciplinaEndPoint {

    @GET("disciplina/")
    Call<ListaDisciplinasAPI> disciplinas();

    @GET("disciplina/{id}")
    Call<Disciplina> getDisciplina(@Path("id") long id);

}

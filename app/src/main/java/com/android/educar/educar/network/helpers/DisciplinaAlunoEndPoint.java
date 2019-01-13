package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.ListaDisciplinaAlunoAPI;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DisciplinaAlunoEndPoint {
    @GET("disciplinaaluno/")
    Call<ListaDisciplinaAlunoAPI> disciplinasAluno();

    @GET("disciplinaaluno/id={id}/")
    Call<DisciplinaAluno> getDisciplinaAluno(@Path("id") long id);

    @GET("disciplinaaluno/")
    Call<ListaDisciplinaAlunoAPI> disciplinasAluno(@Query("page") int page);

    @PUT("disciplinaaluno/id={id}/")
    Call<DisciplinaAluno> atualizarDisciplinaAluno(@Path("id") long id, @Body DisciplinaAluno disciplinaAluno);
}

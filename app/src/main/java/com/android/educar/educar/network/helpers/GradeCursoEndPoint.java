package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;
import com.android.educar.educar.network.service.ListaGradeCursoAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GradeCursoEndPoint {
    @GET("gradecurso/")
    Call<ListaGradeCursoAPI> gradeCursos();

    @GET("gradecurso/id={id}/")
    Call<GradeCurso> getGradeCruso(@Path("id") long id);

    @GET("gradecurso/")
    Call<ListaGradeCursoAPI> gradeCursos(@Query("page") int page);

    @GET("gradecurso/professor={professor}/")
    Call<List<GradeCurso>> getGradeCrusoTurmaProfessor(@Path("professor") long professor);
}

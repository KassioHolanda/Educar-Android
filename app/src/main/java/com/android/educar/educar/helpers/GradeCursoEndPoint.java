package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.service.ListaFuncionariosAPI;
import com.android.educar.educar.service.ListaGradeCursoAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GradeCursoEndPoint {
    @GET("gradecurso/")
    Call<ListaGradeCursoAPI> gradeCursos();

    @GET("gradecurso/{id}")
    Call<GradeCurso> getGradeCruso(@Path("id") long id);
}

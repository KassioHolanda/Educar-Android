package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Professor;
import com.android.educar.educar.network.service.ListaProfessoresAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfessorEndPoint {

    @GET("professores/")
    Call<ListaProfessoresAPI> professores();

    @GET("professores/{pk}")
    Call<Professor> getProfessor(@Path("id") long id);
}

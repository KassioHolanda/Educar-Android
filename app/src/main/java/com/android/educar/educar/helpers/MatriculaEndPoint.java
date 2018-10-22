package com.android.educar.educar.helpers;

import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.service.ListaLocalEscolaAPI;
import com.android.educar.educar.service.ListaMatriculaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MatriculaEndPoint {
    @GET("matricula/")
    Call<ListaMatriculaAPI> matriculas();

    @GET("matricula/{id}")
    Call<Matricula> getMatricula(@Path("id") long id);
}

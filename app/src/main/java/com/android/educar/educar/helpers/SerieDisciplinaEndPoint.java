package com.android.educar.educar.helpers;

import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.service.ListaSerieDisciplinaAPI;
import com.android.educar.educar.service.ListaTurmaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SerieDisciplinaEndPoint {
    @GET("seriedisciplina/")
    Call<ListaSerieDisciplinaAPI> serieDisciplinas();

    @GET("turma/{pk}")
    Call<SerieDisciplina> getSerieDisciplinas(@Path("id") long id);
}

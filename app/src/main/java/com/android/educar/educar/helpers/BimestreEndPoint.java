package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.service.ListaBimestreAPI;
import com.android.educar.educar.service.ListaDisciplinasAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BimestreEndPoint {
    @GET("bimestre/")
    Call<ListaBimestreAPI> bimestres();

    @GET("bimestre/{id}")
    Call<Bimestre> getBimestre(@Path("id") long id);
}

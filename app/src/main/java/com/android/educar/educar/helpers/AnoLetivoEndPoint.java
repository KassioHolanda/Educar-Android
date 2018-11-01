package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AnoLetivo;
import com.android.educar.educar.service.ListaAlunosAPI;
import com.android.educar.educar.service.ListaAnoLetivoAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnoLetivoEndPoint {

    @GET("anoletivo/")
    Call<ListaAnoLetivoAPI> anosLetivos();

    @GET("anoletivo/{id}")
    Call<AnoLetivo> getAnoLetivo(@Path("id") long id);

}

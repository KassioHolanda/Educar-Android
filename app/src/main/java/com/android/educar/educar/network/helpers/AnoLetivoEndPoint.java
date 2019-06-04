package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.AnoLetivo;
import com.android.educar.educar.network.service.ListaAnoLetivoAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnoLetivoEndPoint {

    @GET("anoletivo/")
    Call<ListaAnoLetivoAPI> anosLetivos();

    @GET("anoletivo/{id}")
    Call<AnoLetivo> getAnoLetivo(@Path("id") Long id);

    @GET("anoletivo/")
    Call<ListaAnoLetivoAPI> anosLetivos(@Query("page") int page);

}

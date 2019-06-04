package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Bimestre;
import com.android.educar.educar.network.service.ListaBimestreAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BimestreEndPoint {
    @GET("bimestre/")
    Call<ListaBimestreAPI> bimestres();

    @GET("bimestre/{id}")
    Call<Bimestre> getBimestre(@Path("id") Long id);

    @GET("bimestre/")
    Call<ListaBimestreAPI> bimestres(@Query("page") int page);
}

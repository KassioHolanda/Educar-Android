package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Serie;
import com.android.educar.educar.network.service.ListaSerieAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SerieEndPoint {
    @GET("serie/")
    Call<ListaSerieAPI> series();

    @GET("serie/id={id}/")
    Call<Serie> getSerie(@Path("id") Long id);

    @GET("serie/")
    Call<ListaSerieAPI> series(@Query("page")int page);
}

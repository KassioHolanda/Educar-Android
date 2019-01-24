package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UnidadeEndPoint {

    @GET("unidade/")
    Call<ListaUnidadesAPI> unidades();

    @GET("unidade/id={id}/")
    Call<Unidade> getUnidade(@Path("id") Long id);

    @GET("unidade/")
    Call<ListaUnidadesAPI> unidades(@Query("page") int page);
}

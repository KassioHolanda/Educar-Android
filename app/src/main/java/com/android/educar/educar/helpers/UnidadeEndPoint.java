package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.ListaUnidadesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UnidadeEndPoint {

    @GET("unidade/")
    Call<ListaUnidadesAPI> unidades();

    @GET("unidade/{pk}")
    Call<Unidade> getUnidade(@Path("pk") long pk);

}

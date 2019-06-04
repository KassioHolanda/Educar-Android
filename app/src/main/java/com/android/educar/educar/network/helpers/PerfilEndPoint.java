package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Perfil;
import com.android.educar.educar.network.service.ListaPerfilAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PerfilEndPoint {
    @GET("perfil/")
    Call<ListaPerfilAPI> perfis();

    @GET("perfil/id={id}/")
    Call<List<Perfil>> getPerfil(@Path("id") Long id);

    @GET("perfil/")
    Call<ListaPerfilAPI> perfisPaginacao(@Query("page") int page);
}

package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.ListaPerfilAPI;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PerfilEndPoint {
    @GET("perfil/")
    Call<ListaPerfilAPI> perfis();

    @GET("perfil/{id}")
    Call<Perfil> getPerfil(@Path("id") long id);

    @GET("perfil/")
    Call<ListaPerfilAPI> perfisPaginacao(@Query("page") int page);
}

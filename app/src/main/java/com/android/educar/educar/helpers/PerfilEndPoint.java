package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Perfil;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.ListaPerfilAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PerfilEndPoint {
    @GET("perfil/")
    Call<ListaPerfilAPI> perfis();

    @GET("perfil/{id}")
    Call<Perfil> getPerfil(@Path("id") long id);
}

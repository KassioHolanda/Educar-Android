package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.model.Usuario;
import com.android.educar.educar.service.ListaUnidadesAPI;
import com.android.educar.educar.service.ListaUsuariosAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioEndPoint {
    @GET("usuario/")
    Call<ListaUsuariosAPI> usuarios();

    @GET("usuario/{id}")
    Call<Usuario> getUsuario(@Path("id") long id);

}

package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.Usuario;
import com.android.educar.educar.network.service.ListaUsuariosAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioEndPoint {
    @GET("usuario/")
    Call<ListaUsuariosAPI> usuarios();

    @GET("usuario/{id}/")
    Call<Usuario> getUsuario(@Path("id") Long id);

    @GET("usuario/pessoafisica={pessoafisica}/")
    Call<List<Usuario>> getUsuarioPessoaFisica(@Path("pessoafisica") Long pessoafisica);

    @GET("usuario/")
    Call<ListaUsuariosAPI> usuariosPaginacao(@Query("page") int page);

}

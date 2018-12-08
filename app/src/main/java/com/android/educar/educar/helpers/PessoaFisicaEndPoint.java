package com.android.educar.educar.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.service.ListaFuncionariosAPI;
import com.android.educar.educar.service.ListaPessoaFisicaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PessoaFisicaEndPoint {
    @GET("pessoafisica/?page={numeroPagina}")
    Call<ListaPessoaFisicaAPI> pessoasFisicas(@Path("numeroPagina") int numeroPagina);

    @GET("pessoafisica/{id}")
    Call<PessoaFisica> getPessoaFisica(@Path("id") long id);


}

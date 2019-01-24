package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.network.service.ListaFuncionariosAPI;
import com.android.educar.educar.network.service.ListaPessoaFisicaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PessoaFisicaEndPoint {
    @GET("pessoafisica/")
    Call<ListaPessoaFisicaAPI> pessoasFisicas();

    @GET("pessoafisica/")
    Call<ListaPessoaFisicaAPI> pessoasFisicasComPaginacao(@Query("page") int page);

    @GET("pessoafisica/id={id}/")
    Call<PessoaFisica> getPessoaFisica(@Path("id") Long id);

    @GET("pessoafisica/cpf={cpf}/")
    Call<List<PessoaFisica>> getPessoaFisicaCpf(@Path("cpf") String cpf);
}

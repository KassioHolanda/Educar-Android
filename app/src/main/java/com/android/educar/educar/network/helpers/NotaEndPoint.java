package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Nota;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotaEndPoint {

    @GET("notas/")
    Call<Nota> notas();

    @GET("notas/{pk}")
    Call<Nota> getNota(@Path("id") long id);

}

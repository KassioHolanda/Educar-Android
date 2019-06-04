package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.modelalterado.LocalEscola;
import com.android.educar.educar.network.service.ListaLocalEscolaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocalEscolaEndPoint {
    @GET("localescola/")
    Call<ListaLocalEscolaAPI> locaisEscola();

    @GET("localescola/{pk}")
    Call<LocalEscola> getLocalEscola(@Path("id") Long id);

    @GET("localescola/")
    Call<ListaLocalEscolaAPI> locaisEscola(@Query("page") int page);

    @GET("localescola/unidade={unidade}/")
    Call<List<LocalEscola>> locaisEscolaUnidade(@Path("unidade") Long unidade);
}

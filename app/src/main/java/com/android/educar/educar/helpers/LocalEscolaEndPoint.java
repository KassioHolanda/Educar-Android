package com.android.educar.educar.helpers;

import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Nota;
import com.android.educar.educar.service.ListaGradeCursoAPI;
import com.android.educar.educar.service.ListaLocalEscolaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocalEscolaEndPoint {
    @GET("localescola/")
    Call<ListaLocalEscolaAPI> locaisEscola();

    @GET("localescola/{pk}")
    Call<LocalEscola> getLocalEscola(@Path("id") long id);
}

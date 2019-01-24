package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.network.service.ListaSerieDisciplinaAPI;
import com.android.educar.educar.network.service.ListaTipoOcorrenciaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TipoOcorrenciaEndPoint {
    @GET("tipoocorrencia/")
    Call<ListaTipoOcorrenciaAPI> tiposOcorrencia();

    @GET("tipoocorrencia/{pk}")
    Call<TipoOcorrencia> getTipoOcorrencia(@Path("id") Long id);

    @GET("tipoocorrencia/")
    Call<ListaTipoOcorrenciaAPI> tiposOcorrencia(@Query("page") int page);
}

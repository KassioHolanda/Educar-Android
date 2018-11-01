package com.android.educar.educar.helpers;

import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.service.ListaSerieDisciplinaAPI;
import com.android.educar.educar.service.ListaTipoOcorrenciaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TipoOcorrenciaEndPoint {
    @GET("tipoocorrencia/")
    Call<ListaTipoOcorrenciaAPI> tiposOcorrencia();

    @GET("tipoocorrencia/{pk}")
    Call<TipoOcorrencia> getTipoOcorrencia(@Path("id") long id);
}

package com.android.educar.educar.helpers;

import com.android.educar.educar.model.SituacaoTurmaMes;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.ListaSituacaoTurmaMesAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SituacaoTurmaMesEndPoint {
    @GET("situacaoturmames/")
    Call<ListaSituacaoTurmaMesAPI> situacoes();

    @GET("situacaoturmames/{id}")
    Call<SituacaoTurmaMes> getSituacaoTurmaMes(@Path("id") long id);

    @POST("situacaoturmames/")
    Call<SituacaoTurmaMes> postSituacaoTurmaMes(@Body SituacaoTurmaMes situacaoTurmaMes);
}

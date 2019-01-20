package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.SituacaoTurmaMes;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.network.service.ListaSituacaoTurmaMesAPI;
import com.android.educar.educar.network.service.ListaUnidadesAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SituacaoTurmaMesEndPoint {
    @GET("situacaoturmames/")
    Call<ListaSituacaoTurmaMesAPI> situacoes();

    @GET("situacaoturmames/{id}")
    Call<SituacaoTurmaMes> getSituacaoTurmaMes(@Path("id") long id);

    @POST("situacaoturmames/")
    Call<SituacaoTurmaMes> postSituacaoTurmaMes(@Body SituacaoTurmaMes situacaoTurmaMes);

    @GET("situacaoturmames/")
    Call<ListaSituacaoTurmaMesAPI> situacoes(@Query("page") int page);

    @GET("situacaoturmames/turma={turma}/")
    Call<List<SituacaoTurmaMes>> situacaoTurmaMes(@Path("turma") long turma);
}

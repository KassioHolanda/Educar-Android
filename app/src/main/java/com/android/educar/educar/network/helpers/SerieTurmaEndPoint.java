package com.android.educar.educar.network.helpers;

import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.model.SituacaoTurmaMes;
import com.android.educar.educar.network.service.ListaSerieTurmaAPI;
import com.android.educar.educar.network.service.ListaSituacaoTurmaMesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SerieTurmaEndPoint {
    @GET("serieturma/")
    Call<ListaSerieTurmaAPI> seriesturma();

    @GET("serieturma/{id}")
    Call<SerieTurma> getSerieTurma(@Path("id") long id);

    @GET("serieturma/")
    Call<ListaSerieTurmaAPI> seriesturma(@Query("page")int page);
}

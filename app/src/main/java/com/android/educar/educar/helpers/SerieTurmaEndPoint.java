package com.android.educar.educar.helpers;

import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.model.SituacaoTurmaMes;
import com.android.educar.educar.service.ListaSerieTurmaAPI;
import com.android.educar.educar.service.ListaSituacaoTurmaMesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SerieTurmaEndPoint {
    @GET("serieturma/")
    Call<ListaSerieTurmaAPI> seriesturma();

    @GET("serieturma/{id}")
    Call<SerieTurma> getSerieTurma(@Path("id") long id);
}

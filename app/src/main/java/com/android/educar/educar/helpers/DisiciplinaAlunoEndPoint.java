package com.android.educar.educar.helpers;

import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.ListaDisciplinaAlunoAPI;
import com.android.educar.educar.service.ListaUnidadesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DisiciplinaAlunoEndPoint {
    @GET("disciplinaaluno/")
    Call<ListaDisciplinaAlunoAPI> disciplinasAluno();

    @GET("disicplinaaluno/{id}")
    Call<DisciplinaAluno> getDisciplinaAluno(@Path("id") long id);

}

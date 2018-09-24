package com.android.educar.educar.service;

import com.android.educar.educar.helpers.AlunoEndPoint;
import com.android.educar.educar.helpers.AulaEndPoint;
import com.android.educar.educar.helpers.DisciplinaEndPoint;
import com.android.educar.educar.helpers.FrequenciaEndPoint;
import com.android.educar.educar.helpers.FuncionarioEndPoint;
import com.android.educar.educar.helpers.ProfessorEndPoint;
import com.android.educar.educar.helpers.TurmaEndPoint;
import com.android.educar.educar.helpers.UnidadeEndPoint;
import com.android.educar.educar.model.Professor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    public static String TAG = APIService.class.getSimpleName();

    public static final String BASE_URL = "http://10.20.30.162:8000/";
    //    public static final String BASE_URL = "http://192.168.0.103:8000/";
    private Retrofit retrofit;
    private Interceptor interceptor;
    private AlunoEndPoint alunoEndPoint;
    private AulaEndPoint aulaEndPoint;
    private DisciplinaEndPoint disciplinaEndPoint;
    private FrequenciaEndPoint frequenciaEndPoint;
    private ProfessorEndPoint professorEndPoint;
    private TurmaEndPoint turmaEndPoint;
    private UnidadeEndPoint unidadeEndPoint;
    private FuncionarioEndPoint funcionarioEndPoint;

    public APIService(String token) {

        this.interceptor = new InterceptadorMuralAPI("token " + token);

        OkHttpClient.Builder builderCliente = new OkHttpClient.Builder();
        builderCliente.interceptors().add(this.interceptor);

        OkHttpClient httpClient = builderCliente.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder.baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
//               .client(httpClient)
                .build();

        alunoEndPoint = retrofit.create(AlunoEndPoint.class);
        aulaEndPoint = retrofit.create(AulaEndPoint.class);
        disciplinaEndPoint = retrofit.create(DisciplinaEndPoint.class);
        frequenciaEndPoint = retrofit.create(FrequenciaEndPoint.class);
        professorEndPoint = retrofit.create(ProfessorEndPoint.class);
        turmaEndPoint = retrofit.create(TurmaEndPoint.class);
        unidadeEndPoint = retrofit.create(UnidadeEndPoint.class);

        funcionarioEndPoint = retrofit.create(FuncionarioEndPoint.class);
    }

    public AlunoEndPoint getAlunoEndPoint() {
        return alunoEndPoint;
    }

    public AulaEndPoint getAulaEndPoint() {
        return aulaEndPoint;
    }

    public DisciplinaEndPoint getDisciplinaEndPoint() {
        return disciplinaEndPoint;
    }

    public FrequenciaEndPoint getFrequenciaEndPoint() {
        return frequenciaEndPoint;
    }

    public ProfessorEndPoint getProfessorEndPoint() {
        return professorEndPoint;
    }

    public TurmaEndPoint getTurmaEndPoint() {
        return turmaEndPoint;
    }

    public UnidadeEndPoint getUnidadeEndPoint() {
        return unidadeEndPoint;
    }

    public FuncionarioEndPoint getFuncionarioEndPoint() {
        return funcionarioEndPoint;
    }
}

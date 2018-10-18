package com.android.educar.educar.service;

import android.opengl.Matrix;

import com.android.educar.educar.helpers.AlunoEndPoint;
import com.android.educar.educar.helpers.AlunoFrequenciaMesEndPoint;
import com.android.educar.educar.helpers.DisciplinaEndPoint;
import com.android.educar.educar.helpers.FrequenciaEndPoint;
import com.android.educar.educar.helpers.FuncionarioEndPoint;
import com.android.educar.educar.helpers.FuncionarioEscolaEndPoint;
import com.android.educar.educar.helpers.GradeCursoEndPoint;
import com.android.educar.educar.helpers.LocalEscolaEndPoint;
import com.android.educar.educar.helpers.MatriculaEndPoint;
import com.android.educar.educar.helpers.PessoaFisicaEndPoint;
import com.android.educar.educar.helpers.ProfessorEndPoint;
import com.android.educar.educar.helpers.SerieDisciplinaEndPoint;
import com.android.educar.educar.helpers.TurmaEndPoint;
import com.android.educar.educar.helpers.UnidadeEndPoint;
import com.android.educar.educar.model.AlunoFrequenciaMes;
import com.android.educar.educar.model.GradeCurso;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    public static String TAG = APIService.class.getSimpleName();

    public static final String BASE_URL = "http://10.20.30.205:8000/";
    //    public static final String BASE_URL = "http://192.168.0.107:8000/";
    private Retrofit retrofit;
    private Interceptor interceptor;
    private AlunoEndPoint alunoEndPoint;
    private DisciplinaEndPoint disciplinaEndPoint;
    private FrequenciaEndPoint frequenciaEndPoint;
    private ProfessorEndPoint professorEndPoint;
    private TurmaEndPoint turmaEndPoint;
    private UnidadeEndPoint unidadeEndPoint;
    private FuncionarioEndPoint funcionarioEndPoint;
    private PessoaFisicaEndPoint pessoaFisicaEndPoint;
    private GradeCursoEndPoint gradeCursoEndPoint;
    private FuncionarioEscolaEndPoint funcionarioEscolaEndPoint;
    private LocalEscolaEndPoint localEscolaEndPoint;
    private SerieDisciplinaEndPoint serieDisciplinaEndPoint;
    private MatriculaEndPoint matriculaEndPoint;
    private AlunoFrequenciaMesEndPoint alunoFrequenciaMesEndPoint;

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
        disciplinaEndPoint = retrofit.create(DisciplinaEndPoint.class);
        frequenciaEndPoint = retrofit.create(FrequenciaEndPoint.class);
        professorEndPoint = retrofit.create(ProfessorEndPoint.class);
        turmaEndPoint = retrofit.create(TurmaEndPoint.class);
        unidadeEndPoint = retrofit.create(UnidadeEndPoint.class);
        funcionarioEndPoint = retrofit.create(FuncionarioEndPoint.class);
        pessoaFisicaEndPoint = retrofit.create(PessoaFisicaEndPoint.class);
        gradeCursoEndPoint = retrofit.create(GradeCursoEndPoint.class);
        funcionarioEscolaEndPoint = retrofit.create(FuncionarioEscolaEndPoint.class);
        localEscolaEndPoint = retrofit.create(LocalEscolaEndPoint.class);
        serieDisciplinaEndPoint = retrofit.create(SerieDisciplinaEndPoint.class);

        matriculaEndPoint = retrofit.create(MatriculaEndPoint.class);
        alunoFrequenciaMesEndPoint = retrofit.create(AlunoFrequenciaMesEndPoint.class);
    }

    public AlunoEndPoint getAlunoEndPoint() {
        return alunoEndPoint;
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

    public PessoaFisicaEndPoint getPessoaFisicaEndPoint() {
        return pessoaFisicaEndPoint;
    }

    public GradeCursoEndPoint getGradeCursoEndPoint() {
        return gradeCursoEndPoint;
    }

    public FuncionarioEscolaEndPoint getFuncionarioEscolaEndPoint() {
        return funcionarioEscolaEndPoint;
    }

    public LocalEscolaEndPoint getLocalEscolaEndPoint() {
        return localEscolaEndPoint;
    }

    public SerieDisciplinaEndPoint getSerieDisciplinaEndPoint() {
        return serieDisciplinaEndPoint;
    }

    public MatriculaEndPoint getMatriculaEndPoint() {
        return matriculaEndPoint;
    }
}

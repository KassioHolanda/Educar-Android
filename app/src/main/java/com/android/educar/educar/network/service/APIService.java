package com.android.educar.educar.network.service;

import com.android.educar.educar.network.helpers.AlunoEndPoint;
import com.android.educar.educar.network.helpers.AlunoFrequenciaMesEndPoint;
import com.android.educar.educar.network.helpers.AlunoNotaMesEndPoint;
import com.android.educar.educar.network.helpers.AnoLetivoEndPoint;
import com.android.educar.educar.network.helpers.BimestreEndPoint;
import com.android.educar.educar.network.helpers.DisciplinaEndPoint;
import com.android.educar.educar.network.helpers.DisciplinaAlunoEndPoint;
import com.android.educar.educar.network.helpers.FuncionarioEndPoint;
import com.android.educar.educar.network.helpers.FuncionarioEscolaEndPoint;
import com.android.educar.educar.network.helpers.GradeCursoEndPoint;
import com.android.educar.educar.network.helpers.LocalEscolaEndPoint;
import com.android.educar.educar.network.helpers.MatriculaEndPoint;
import com.android.educar.educar.network.helpers.OcorrenciaEndPoint;
import com.android.educar.educar.network.helpers.PerfilEndPoint;
import com.android.educar.educar.network.helpers.PessoaFisicaEndPoint;
import com.android.educar.educar.network.helpers.SerieDisciplinaEndPoint;
import com.android.educar.educar.network.helpers.SerieEndPoint;
import com.android.educar.educar.network.helpers.SerieTurmaEndPoint;
import com.android.educar.educar.network.helpers.SituacaoTurmaMesEndPoint;
import com.android.educar.educar.network.helpers.TipoOcorrenciaEndPoint;
import com.android.educar.educar.network.helpers.TurmaEndPoint;
import com.android.educar.educar.network.helpers.UnidadeEndPoint;
import com.android.educar.educar.network.helpers.UsuarioEndPoint;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    public static String TAG = APIService.class.getSimpleName();


    public static final String BASE_URL = "http://10.20.30.205:8000/";
//    public static final String BASE_URL = "http:/192.168.0.110:8000/";
    private Retrofit retrofit;

    private Interceptor interceptor;
    private AlunoEndPoint alunoEndPoint;
    private DisciplinaEndPoint disciplinaEndPoint;
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
    private OcorrenciaEndPoint ocorrenciaEndPoint;
    private TipoOcorrenciaEndPoint tipoOcorrenciaEndPoint;
    private AnoLetivoEndPoint anoLetivoEndPoint;

    private DisciplinaAlunoEndPoint disciplinaAlunoEndPoint;
    private PerfilEndPoint perfilEndPoint;
    private SituacaoTurmaMesEndPoint situacaoTurmaMesEndPoint;
    private UsuarioEndPoint usuarioEndPoint;
    private AlunoNotaMesEndPoint alunoNotaMesEndPoint;

    private BimestreEndPoint bimestreEndPoint;
    private SerieTurmaEndPoint serieTurmaEndPoint;
    private SerieEndPoint serieEndPoint;

    public APIService(String token) {

        this.interceptor = new InterceptadorMuralAPI("token " + token);

        OkHttpClient.Builder builderCliente = new OkHttpClient.Builder();
        builderCliente.interceptors().add(this.interceptor);

//        OkHttpClient httpClient = builderCliente.build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder.baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        alunoEndPoint = retrofit.create(AlunoEndPoint.class);
        disciplinaEndPoint = retrofit.create(DisciplinaEndPoint.class);
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

        ocorrenciaEndPoint = retrofit.create(OcorrenciaEndPoint.class);
        tipoOcorrenciaEndPoint = retrofit.create(TipoOcorrenciaEndPoint.class);

        anoLetivoEndPoint = retrofit.create(AnoLetivoEndPoint.class);

        disciplinaAlunoEndPoint = retrofit.create(DisciplinaAlunoEndPoint.class);
        perfilEndPoint = retrofit.create(PerfilEndPoint.class);
        situacaoTurmaMesEndPoint = retrofit.create(SituacaoTurmaMesEndPoint.class);
        usuarioEndPoint = retrofit.create(UsuarioEndPoint.class);
        alunoNotaMesEndPoint = retrofit.create(AlunoNotaMesEndPoint.class);

        bimestreEndPoint = retrofit.create(BimestreEndPoint.class);
        serieTurmaEndPoint = retrofit.create(SerieTurmaEndPoint.class);
        serieEndPoint = retrofit.create(SerieEndPoint.class);
    }

    public AlunoEndPoint getAlunoEndPoint() {
        return alunoEndPoint;
    }

    public DisciplinaEndPoint getDisciplinaEndPoint() {
        return disciplinaEndPoint;
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

    public AlunoFrequenciaMesEndPoint getAlunoFrequenciaMesEndPoint() {
        return alunoFrequenciaMesEndPoint;
    }

    public OcorrenciaEndPoint getOcorrenciaEndPoint() {
        return ocorrenciaEndPoint;
    }

    public TipoOcorrenciaEndPoint getTipoOcorrenciaEndPoint() {
        return tipoOcorrenciaEndPoint;
    }

    public AnoLetivoEndPoint getAnoLetivoEndPoint() {
        return anoLetivoEndPoint;
    }

    public DisciplinaAlunoEndPoint getDisciplinaAlunoEndPoint() {
        return disciplinaAlunoEndPoint;
    }

    public PerfilEndPoint getPerfilEndPoint() {
        return perfilEndPoint;
    }

    public SituacaoTurmaMesEndPoint getSituacaoTurmaMesEndPoint() {
        return situacaoTurmaMesEndPoint;
    }

    public UsuarioEndPoint getUsuarioEndPoint() {
        return usuarioEndPoint;
    }

    public AlunoNotaMesEndPoint getAlunoNotaMesEndPoint() {
        return alunoNotaMesEndPoint;
    }

    public BimestreEndPoint getBimestreEndPoint() {
        return bimestreEndPoint;
    }

    public SerieTurmaEndPoint getSerieTurmaEndPoint() {
        return serieTurmaEndPoint;
    }

    public SerieEndPoint getSerieEndPoint() {
        return serieEndPoint;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}

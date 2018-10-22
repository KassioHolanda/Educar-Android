package com.android.educar.educar.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.NotaAdapter;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotaFragment extends Fragment {

    private Preferences preferences;
    private APIService apiService;
    private UtilsFunctions utilsFunctions;
    private RecyclerView notasAluno;

    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;

    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;

    private LinearLayout layoutUnidade;
    private LinearLayout layoutTurma;
    private LinearLayout layoutDisciplina;

    private Realm realm;
    private List<PessoaFisica> pessoaFisicas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota, container, false);
        binding(view);
        setupInit();
        configRealm();
        recuperarDadosRealm();
        atualizarDadosTela();
        consultarBimestre();
        onClickItem();
        recuperarAlunosRealm();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        atualizarAdapterFrequencia(turmaAlunoDAO.selecionarTurmaAluno(preferences.getSavedLong("id_turma")));
    }

    public void configRealm() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    public void binding(View view) {
        notasAluno = view.findViewById(R.id.alunos_nota_frequencia_recycler_id);
        unidadeSelecionadaAula = view.findViewById(R.id.unidadenota_id);
        turmaSelecionadaAula = view.findViewById(R.id.turmanota_id);
        disciplinaSelecionadaAula = view.findViewById(R.id.disciplinanota_id);

        layoutUnidade = view.findViewById(R.id.layout_unidade_nota_id);
        layoutDisciplina = view.findViewById(R.id.layout_disciplina_nota_id);
        layoutTurma = view.findViewById(R.id.layout_turma_nota_id);
    }

    public void recuperarAlunosRealm() {
        List<Aluno> alunos = new ArrayList<>();
        pessoaFisicas = new ArrayList<>();

        RealmResults<Matricula> matriculas = realm.where(Matricula.class).findAll();

        for (int i = 0; i < matriculas.size(); i++) {
            Aluno aluno = realm.where(Aluno.class).equalTo("id", matriculas.get(i).getAluno()).findFirst();
            matriculas.get(i).getAluno();

            if (aluno != null) {
                alunos.add(aluno);
            }
        }

        for (int i = 0; i < 10; i++) {
            PessoaFisica pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", alunos.get(i).getPessoaFisica()).findFirst();
            alunos.get(i).getPessoaFisica();

            if (pessoaFisica != null) {
                pessoaFisicas.add(pessoaFisica);
            }
        }

        atualizarAdapterFrequencia(pessoaFisicas);
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        apiService = new APIService("");
        utilsFunctions = new UtilsFunctions();

//        classDAO = new ClassDAO(getContext());

//        bimestres = classDAO.bimestres();
//        numeroBimestre = new HashMap<>();
//
//        numeroBimestre.put(1, Messages.PRIMEIRO_SEMESTRE);
//        numeroBimestre.put(2, Messages.SEGUNDO_SEMESTRE);
//        numeroBimestre.put(3, Messages.TERCEIRO_SEMESTRE);
//        numeroBimestre.put(4, Messages.QUARTO_SEMESTRE);


//        unidadeSelecionada = unidadeDAO.selecionarUnidade(preferences.getSavedLong("id_unidade"));
//        turmaSelecionada = turmaDAO.selecionarTurma(preferences.getSavedLong("id_turma"));
//        disciplinaSelecionada = disciplinaDAO.selecionarDiscipina(preferences.getSavedLong("id_disciplina"));
    }

    public void onClickItem() {
        layoutTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + turmaSelecionada.getDescricao(), Toast.LENGTH_SHORT).show();
            }
        });

        layoutDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(getView(), "" + disciplinaSelecionada.getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        layoutUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(getView(), "" + unidadeSelecionada.getNomeUnidade(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void atualizarAdapterFrequencia(List<PessoaFisica> pessoaFisicas) {
        NotaAdapter notaFragment = new NotaAdapter(getContext(), pessoaFisicas);
        notasAluno.setAdapter(notaFragment);
        notasAluno.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void consultarBimestre() {
//        List<Bimestre> bimestres = classDAO.bimestres();
//        if (bimestres.size() == 0) {
//            Bimestre bimestre1 = new Bimestre();
//            bimestre1.setDescricao(Messages.PRIMEIRO_SEMESTRE);
//            bimestre1.setAnoLetivo(2018);
//            classDAO.adicionarBimestre(bimestre1);
//            preferences.saveString("bimestre", bimestre1.getDescricao());
//            preferences.saveLong("id_bimestre", bimestre1.getPk());
//        } else {
//            Bimestre bimestre1 = bimestres.get(0);
//            preferences.saveString("bimestre", bimestre1.getDescricao());
//            preferences.saveLong("id_bimestre", bimestre1.getPk());
//        }
    }


    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getAbreviacao());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
    }

    public void recuperarDadosRealm() {
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong("id_unidade")).findFirst();
        turmaSelecionada = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplinaSelecionada = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }
}

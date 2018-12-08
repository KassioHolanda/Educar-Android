package com.android.educar.educar.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.FrequenciaAdapter;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrequenciaFragment extends Fragment {

    private Preferences preferences;
    private APIService apiService;
    private UtilsFunctions utilsFunctions;
    private RecyclerView alunosFrequencia;
    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;
    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;
    private Button salvarFrequencia;

    private LinearLayout unidadeFrequecia;
    private LinearLayout turmaFrequencia;
    private LinearLayout disciplinaFrequencia;

    private List<PessoaFisica> pessoaFisicas;

    private Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frequencia, container, false);
        bindind(view);
        setupInit();
        configRealm();
        onClickItem();
        recuperarDadosRealm();
        atualizarDadosTela();
        recuperarAlunosRealm();
        return view;
    }

    public void bindind(View view) {
        alunosFrequencia = view.findViewById(R.id.alunos_frequencia_recycler_id);
        unidadeSelecionadaAula = view.findViewById(R.id.unidadeselecionada_aula_id);
        disciplinaSelecionadaAula = view.findViewById(R.id.disciplinaselecionada_aula_id);
        turmaSelecionadaAula = view.findViewById(R.id.turmaselecionada_aula_id);
        salvarFrequencia = view.findViewById(R.id.button_salvarfrequencia_id);
        unidadeFrequecia = view.findViewById(R.id.frequencia_unidade_id);
        turmaFrequencia = view.findViewById(R.id.frequencia_turma_id);
        disciplinaFrequencia = view.findViewById(R.id.frequencia_disciplina_id);
    }

    public void configRealm() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        utilsFunctions = new UtilsFunctions();
//        atualizarAdapterFrequencia(turmaAlunoDAO.selecionarTurmaAluno(preferences.getSavedLong("id_turma")));
    }

    public void recuperarAlunosRealm() {
        List<Aluno> alunos = new ArrayList<>();
        pessoaFisicas = new ArrayList<>();

        RealmResults<Matricula> matriculas = realm.where(Matricula.class).equalTo("turma", preferences.getSavedLong("id_turma")).findAll();

        for (int i = 0; i < matriculas.size(); i++) {
            Aluno aluno = realm.where(Aluno.class).equalTo("id", matriculas.get(i).getAluno()).findFirst();
            if (aluno != null) {
                alunos.add(aluno);
            }
        }

        for (int i = 0; i < alunos.size(); i++) {
            PessoaFisica pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", alunos.get(i).getPessoaFisica()).findFirst();
            if (pessoaFisica != null) {
                pessoaFisicas.add(pessoaFisica);
            }
        }

        atualizarAdapterFrequencia(pessoaFisicas);
    }

    public void onClickItem() {
        salvarFrequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Frequência Registrada!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        unidadeFrequecia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + unidadeSelecionada.getAbreviacao(), Snackbar.LENGTH_SHORT).show();
            }
        });

        disciplinaFrequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + disciplinaSelecionada.getDescricao(), Snackbar.LENGTH_SHORT).show();
            }
        });

        turmaFrequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + turmaSelecionada.getDescricao(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void atualizarAdapterFrequencia(List<PessoaFisica> pessoaFisicas) {
        FrequenciaAdapter frequenciaAdapter = new FrequenciaAdapter(getContext(), pessoaFisicas);
        alunosFrequencia.setAdapter(frequenciaAdapter);
        alunosFrequencia.setLayoutManager(new LinearLayoutManager(getContext()));
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

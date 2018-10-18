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
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.List;

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
//    private ClassDAO classDAO;
    private Button salvarFrequencia;

    private LinearLayout unidadeFrequecia;
    private LinearLayout turmaFrequencia;
    private LinearLayout disciplinaFrequencia;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frequencia, container, false);
        bindind(view);
        setupInit();
        atualizarDadosTela();
        onClickItem();
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

    public void setupInit() {

//        turmaAlunoDAO = new TurmaAlunoDAO(getContext());
//        unidadeDAO = new UnidadeDAO(getContext());
//        disciplinaDAO = new DisciplinaDAO(getContext());
//        turmaDAO = new TurmaDAO(getContext());

        preferences = new Preferences(getContext());
        apiService = new APIService("");
        utilsFunctions = new UtilsFunctions();
//        classDAO = new ClassDAO(getContext());
//        atualizarAdapterFrequencia(turmaAlunoDAO.selecionarTurmaAluno(preferences.getSavedLong("id_turma")));

//        unidadeSelecionada = unidadeDAO.selecionarUnidade(preferences.getSavedLong("id_unidade"));
//        disciplinaSelecionada = disciplinaDAO.selecionarDiscipina(preferences.getSavedLong("id_disciplina"));
//        turmaSelecionada = turmaDAO.selecionarTurma(preferences.getSavedLong("id_turma"));
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
//                Snackbar.make(getView(), "" + unidadeSelecionada.getNomeUnidade(), Snackbar.LENGTH_SHORT).show();
            }
        });

        disciplinaFrequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(getView(), "" + disciplinaSelecionada.getNome(), Snackbar.LENGTH_SHORT).show();
            }
        });

        turmaFrequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + turmaSelecionada.getDescricao(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void atualizarAdapterFrequencia(List<Aluno> alunos) {
        FrequenciaAdapter frequenciaAdapter = new FrequenciaAdapter(getContext(), alunos);
        alunosFrequencia.setAdapter(frequenciaAdapter);
        alunosFrequencia.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void atualizarDadosTela() {
//        unidadeSelecionadaAula.setText(unidadeSelecionada.getNomeUnidade());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
//        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getNome());
    }
}

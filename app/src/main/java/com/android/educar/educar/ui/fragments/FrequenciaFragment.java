package com.android.educar.educar.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.FrequenciaAdapterLista;
import com.android.educar.educar.mb.FrequenciaMB;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Funcionario;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.LocalEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FrequenciaFragment extends Fragment {

    private Preferences preferences;
    private ListView alunosFrequencia;
    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;
    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;
    //    private Button salvarFrequencia;
    private TextView dataFrequencia;

    private LinearLayout unidadeFrequecia;
    private LinearLayout turmaFrequencia;
    private LinearLayout disciplinaFrequencia;

    private List<PessoaFisica> pessoaFisicas;

    private FrequenciaMB frequenciaMB;

    private Realm realm;
    private TextView bimestreFragmentFrequencia;

    private Funcionario funcionario;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frequencia, container, false);
        bindind(view);
        configRealm();
        setupInit();
        recuperarUnidade();
        recuperarTurma();
        recuperarDisciplina();
//        onClickItem();
        atualizarDadosTela();
        recuperarAlunosRealm();
        return view;
    }

    public void recuperarUnidade() {
        for (int i = 0; i < funcionario.getFuncionarioEscolas().size(); i++) {
            if (funcionario.getFuncionarioEscolas().get(i).getUnidade().getId() == preferences.getSavedLong(Messages.ID_UNIDADE) && funcionario.getFuncionarioEscolas().get(i).getAtivo()) {
                this.unidadeSelecionada = funcionario.getFuncionarioEscolas().get(i).getUnidade();
            }
        }
    }

    public void recuperarTurma() {
        for (int i = 0; i < unidadeSelecionada.getLocalEscolas().size(); i++) {
            for (int j = 0; j < unidadeSelecionada.getLocalEscolas().get(i).getTurmas().size(); j++) {
                if (unidadeSelecionada.getLocalEscolas().get(i).getTurmas().get(j).getId() == preferences.getSavedLong(Messages.ID_TURMA)) {
                    this.turmaSelecionada = (unidadeSelecionada.getLocalEscolas().get(i).getTurmas().get(j));
                }
            }
        }
    }

    public void recuperarDisciplina() {
        for (int i = 0; i < turmaSelecionada.getGradeCursos().size(); i++) {
            try {
                if (turmaSelecionada.getGradeCursos().get(i).getSeriedisciplina().getDisciplina().getId() == preferences.getSavedLong("id_disciplina")) {
                    disciplinaSelecionada = turmaSelecionada.getGradeCursos().get(i).getDisciplina();
                } else if (turmaSelecionada.getGradeCursos().get(i).getSeriedisciplina().getSerie().getId() == preferences.getSavedLong("id_serie")) {
//                    serieSelecionada = turmaSelecionada.getGradeCursos().get(i).getSeriedisciplina().getSerie();
                }
            } catch (NullPointerException e) {

            }
            if (turmaSelecionada.getGradeCursos().get(i).getDisciplina().getId() == preferences.getSavedLong("id_disciplina")) {
                disciplinaSelecionada = turmaSelecionada.getGradeCursos().get(i).getDisciplina();
            }
        }
    }

    public void bindind(View view) {
        alunosFrequencia = view.findViewById(R.id.alunos_frequencia_recycler_id);
        unidadeSelecionadaAula = view.findViewById(R.id.unidadeselecionada_aula_id);
        disciplinaSelecionadaAula = view.findViewById(R.id.disciplinaselecionada_aula_id);
        turmaSelecionadaAula = view.findViewById(R.id.turmaselecionada_aula_id);
//        salvarFrequencia = view.findViewById(R.id.button_salvarfrequencia_id);
//        unidadeFrequecia = view.findViewById(R.id.frequencia_unidade_id);
//        turmaFrequencia = view.findViewById(R.id.frequencia_turma_id);
//        disciplinaFrequencia = view.findViewById(R.id.frequencia_disciplina_id);
        bimestreFragmentFrequencia = view.findViewById(R.id.bimestre_fragment_frequencia_id);
        dataFrequencia = view.findViewById(R.id.data_frequencia_id);
    }

    public void configRealm() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        frequenciaMB = new FrequenciaMB(getContext());
        pessoaFisicas = new ArrayList<>();
        funcionario = realm.copyFromRealm(realm.where(Funcionario.class).findFirst());
    }

    public void recuperarAlunosRealm() {
        Turma turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst());
//        for (FuncionarioEscola funcionarioEscola : this.funcionario.getFuncionarioEscolas()) {
//            for (LocalEscola localEscola : funcionarioEscola.getUnidade().getLocalEscolas()) {
//                for (Turma turma : localEscola.getTurmas()) {
//                    for (Matricula matricula : turma.getMatriculas()) {
//                        pessoaFisicas.add(matricula.getAluno().getPessoaFisica());
//                    }
//                }
//            }
//        }

        for (Matricula matricula : turma.getMatriculas()) {
            this.pessoaFisicas.add(matricula.getAluno().getPessoaFisica());
        }

        Collections.sort(pessoaFisicas);
        atualizarAdapterFrequencia(pessoaFisicas);
    }

    public void onClickItem() {
//        salvarFrequencia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                frequenciaMB.salvarFrequencia();
//                Toast.makeText(getContext(), "Frequência do dia " + new Date().getDate() + " Registrada!", Toast.LENGTH_LONG).show();
//                getActivity().finish();
//            }
//        });

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
        FrequenciaAdapterLista frequenciaAdapterLista = new FrequenciaAdapterLista(getContext(), pessoaFisicas);
        alunosFrequencia.setAdapter(frequenciaAdapterLista);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getAbreviacao());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
//        bimestreFragmentFrequencia.setText(realm.where(Bimestre.class).equalTo("id", preferences.getSavedLong("id_bimestre")).findFirst().getDescricao());
        dataFrequencia.setText("" + UtilsFunctions.apenasData().format(new Date()));
    }
}

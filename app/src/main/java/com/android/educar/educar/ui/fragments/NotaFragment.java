package com.android.educar.educar.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.NotaAdapterLista;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotaFragment extends Fragment {

    private Preferences preferences;
    private UtilsFunctions utilsFunctions;
    private ListView notasAluno;

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
    private SerieTurma serieTurma;

    private TextView bimestreFragmentNota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota, container, false);
        binding(view);
        setupInit();
        configRealm();
//        recuperarDadosRealm();
//        atualizarDadosTela();
//        onClickItem();
//        recuperarAlunosRealm();
        return view;
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
        bimestreFragmentNota = view.findViewById(R.id.bimestre_fragment_nota_id);
    }

    public void recuperarAlunosRealm() {
        List<Aluno> alunos = new ArrayList<>();
        pessoaFisicas = new ArrayList<>();
        RealmResults<Matricula> matriculas = realm.where(Matricula.class).equalTo("turma", preferences.getSavedLong("id_turma")).findAll();

        for (Matricula matricula : matriculas) {
//            Aluno aluno = realm.where(Aluno.class).equalTo("id", matricula.getAluno()).findFirst();
//            if (aluno != null) {
//                alunos.add(aluno);
//            }
        }

        for (Aluno aluno : alunos) {
//            PessoaFisica pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", aluno.getPessoaFisica()).findFirst();
//            if (pessoaFisica != null) {
//                pessoaFisicas.add(pessoaFisica);
//            }
        }

        Collections.sort(pessoaFisicas);
        atualizarAdapterFrequencia(pessoaFisicas);
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        utilsFunctions = new UtilsFunctions();
        serieTurma = new SerieTurma();
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
                Snackbar.make(getView(), "" + disciplinaSelecionada.getDescricao(), Toast.LENGTH_SHORT).show();
            }
        });

        layoutUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + unidadeSelecionada.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void atualizarAdapterFrequencia(List<PessoaFisica> pessoaFisicas) {
        NotaAdapterLista notaFragment = new NotaAdapterLista(pessoaFisicas, getContext());
        notasAluno.setAdapter(notaFragment);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getNome());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
        bimestreFragmentNota.setText(realm.where(Bimestre.class).equalTo("id", preferences.getSavedLong("id_bimestre")).findFirst().getDescricao());
    }

    public void recuperarDadosRealm() {
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong("id_unidade")).findFirst();
        turmaSelecionada = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplinaSelecionada = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }
}

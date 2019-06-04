package com.android.educar.educar.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.NotaAdapterLista;
import com.android.educar.educar.mb.BimestreMB;
import com.android.educar.educar.model.modelalterado.Bimestre;
import com.android.educar.educar.model.modelalterado.Disciplina;
import com.android.educar.educar.model.modelalterado.Funcionario;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.model.modelalterado.PessoaFisica;
import com.android.educar.educar.model.modelalterado.SerieTurma;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.model.modelalterado.Unidade;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;

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

    private Realm realm;
    private List<PessoaFisica> pessoaFisicas;
    private SerieTurma serieTurma;

    private TextView bimestreFragmentNota;
    private Funcionario funcionario;
    private Bimestre bimestre;
    private BimestreMB bimestreMB;
    private List<Matricula> matriculas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota, container, false);
        binding(view);
        configRealm();
        setupInit();
        atualizarDadosTela();
//        onClickItem();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recuperarAlunosRealm();
    }

    public void configRealm() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    public void recuperarAlunosRealm() {
        Turma turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst());


        matriculas = turma.getMatriculas();
        Collections.sort(matriculas);
        atualizarAdapterFrequencia(matriculas);

    }


    public void binding(View view) {
        notasAluno = view.findViewById(R.id.alunos_nota_frequencia_recycler_id);
        unidadeSelecionadaAula = view.findViewById(R.id.unidadenota_id);
        turmaSelecionadaAula = view.findViewById(R.id.turmanota_id);
        disciplinaSelecionadaAula = view.findViewById(R.id.disciplinanota_id);
        bimestreFragmentNota = view.findViewById(R.id.bimestre_fragment_nota_id);
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        utilsFunctions = new UtilsFunctions();
        serieTurma = new SerieTurma();
        funcionario = realm.copyFromRealm(realm.where(Funcionario.class).findFirst());
        bimestreMB = new BimestreMB(getContext());
        bimestre = realm.copyFromRealm(realm.where(Bimestre.class).equalTo("id", bimestreMB.getBimestreAtual()).findFirst());
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong("id_unidade")).findFirst();
        turmaSelecionada = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplinaSelecionada = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }


    public void atualizarAdapterFrequencia(List<Matricula> matriculas) {
        NotaAdapterLista notaFragment = new NotaAdapterLista(matriculas, getContext());
        notasAluno.setAdapter(notaFragment);
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getNome());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
        bimestreFragmentNota.setText(realm.where(Bimestre.class).equalTo("id", preferences.getSavedLong("id_bimestre")).findFirst().getDescricao());
    }
}

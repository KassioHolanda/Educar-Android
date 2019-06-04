package com.android.educar.educar.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.BimestreMB;
import com.android.educar.educar.model.modelalterado.AnoLetivo;
import com.android.educar.educar.model.modelalterado.Bimestre;
import com.android.educar.educar.model.modelalterado.Aluno;
import com.android.educar.educar.model.modelalterado.Disciplina;
import com.android.educar.educar.model.modelalterado.Funcionario;
import com.android.educar.educar.model.modelalterado.FuncionarioEscola;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.model.modelalterado.Ocorrencia;
import com.android.educar.educar.model.modelalterado.PessoaFisica;
import com.android.educar.educar.model.modelalterado.TipoOcorrencia;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.model.modelalterado.Unidade;
import com.android.educar.educar.ui.activities.NotificacoesAlunoAcitivity;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class OcorrenciaFragment extends Fragment {

    private ListView pessoas;
    private Preferences preferences;
    private UtilsFunctions utilsFunctions;

    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;

    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;
    private TextView bimestreFragmentOcorrencia;

    private Realm realm;
    private Aluno aluno;
    private Matricula matricula;

    private Funcionario funcionario;
    private BimestreMB bimestreMB;
    private Bimestre bimestre;

    private List<Matricula> matriculas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ocorrencia, container, false);
        binding(view);
        configRealm();
        setupInit();
        onClickItem();
//        recuperarDadosRealm();
        return view;
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        utilsFunctions = new UtilsFunctions();
        registerForContextMenu(pessoas);
        funcionario = realm.copyFromRealm(realm.where(Funcionario.class).findFirst());
        bimestreMB = new BimestreMB(getContext());
        bimestre = realm.copyFromRealm(realm.where(Bimestre.class).equalTo("id", bimestreMB.getBimestreAtual()).findFirst());
    }

    @Override
    public void onResume() {
        super.onResume();
        recuperarDadosRealm();
        atualizarDadosTela();
        recuperarAlunosRealm();
    }

    public void configRealm() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    public void binding(View view) {
        pessoas = view.findViewById(R.id.alunos_list_id);
        unidadeSelecionadaAula = view.findViewById(R.id.unidade_selecionada_aula_ocorrencia_id);
        turmaSelecionadaAula = view.findViewById(R.id.turma_selecionada_aula_ocorrencia_id);
        disciplinaSelecionadaAula = view.findViewById(R.id.diciplina_selecionada_aula_ocorrencia_id);
        bimestreFragmentOcorrencia = view.findViewById(R.id.bimestre_fragment_ocorrencia_id);
    }

    private void atualizarLista(List<Matricula> results) {
        ArrayAdapter<Matricula> alunoArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
        pessoas.setAdapter(alunoArrayAdapter);
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final MenuItem insert = menu.add("Inserir");
        final MenuItem detalhar = menu.add("Mostrar Todas");

        detalhar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                mostrarNotificacoes();
                startActivity(new Intent(getContext(), NotificacoesAlunoAcitivity.class));
                return false;
            }
        });

        insert.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                novaOcorrencia();
                return false;
            }
        });
    }

    public void recuperarAlunosRealm() {
        Turma turma = realm.copyFromRealm(realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst());

        matriculas = turma.getMatriculas();
        Collections.sort(matriculas);
        atualizarLista(matriculas);
    }

    public void onClickItem() {

        pessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                matricula = (Matricula) pessoas.getItemAtPosition(position);
                preferences.saveLong("id_matricula_aluno", matricula.getId());
                recuperarRegistrosAluno(matricula);
                view.showContextMenu();
            }
        });

    }

    public void novaOcorrencia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View viewDialog = getLayoutInflater().inflate(R.layout.dialog_nova_ocorrencia, null);

        final EditText ocorrenciaText = viewDialog.findViewById(R.id.ocorrencia_id);
        final Spinner tiposOcorrencia = viewDialog.findViewById(R.id.tipos_ocorrencia_id);
        final EditText aluno = viewDialog.findViewById(R.id.aluno_ocorrencia_id);

        Matricula matricula = realm.where(Matricula.class).equalTo("id", preferences.getSavedLong("id_matricula_aluno")).findFirst();

        aluno.setText(matricula.getAluno().getPessoaFisica().getNome());
        aluno.setEnabled(false);

        List<TipoOcorrencia> ocorrencias = realm.copyFromRealm(realm.where(TipoOcorrencia.class).findAll());
        Collections.sort(ocorrencias);

        ArrayAdapter<TipoOcorrencia> tipoOcorrenciaArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, ocorrencias);
        tiposOcorrencia.setAdapter(tipoOcorrenciaArrayAdapter);

        builder.setView(viewDialog).setTitle("Registrar Notificação!")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TipoOcorrencia tipoOcorrencia = (TipoOcorrencia) tiposOcorrencia.getSelectedItem();

                        salvarOcorrenciaRealm(tipoOcorrencia, ocorrenciaText.getText().toString());

                        Snackbar.make(getView(), "A Notificação foi Registrada para o Aluno " + matricula.getAluno().getPessoaFisica().getNome() + "!", Snackbar.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancelar", null).show();

    }

    public void salvarOcorrenciaRealm(TipoOcorrencia tipoOcorrencia, String descricao) {

        realm.beginTransaction();

        Ocorrencia ocorrencia1 = new Ocorrencia();
        ocorrencia1.setId(Long.valueOf(realm.where(Ocorrencia.class).findAll().size() + 1));
        ocorrencia1.setDatahora(UtilsFunctions.formatoDataPadrao().format(new Date()));
        ocorrencia1.setDatahoracadastro(UtilsFunctions.formatoDataPadrao().format(new Date()));
//        ocorrencia1.setFuncionarioEscola(realm.where(FuncionarioEscola.class)
//                .equalTo("funcionario", preferences.getSavedLong("id_funcionario"))
//                .equalTo("unidade", preferences.getSavedLong("id_unidade")).findFirst().getId());
        ocorrencia1.setDescricao(tipoOcorrencia.getDescricao());
        ocorrencia1.setMatriculaAluno(matricula.getId());
        ocorrencia1.setTipoOcorrencia(tipoOcorrencia.getId());
        ocorrencia1.setAluno(matricula.getAluno().getId());
        ocorrencia1.setAnoLetivo(preferences.getSavedLong("id_anoletivo"));
        ocorrencia1.setFuncionarioEscola(recuperarFuncionarioEscola().getId());
        ocorrencia1.setFuncionario(funcionario.getId());
        ocorrencia1.setUnidade(unidadeSelecionada.getId());
        ocorrencia1.setEnviadoSms(false);
        ocorrencia1.setDataEnvioSms(null);
        ocorrencia1.setResumoSms(tipoOcorrencia.getDescricao());
        ocorrencia1.setObservacao(descricao);
        ocorrencia1.setNumeroTelefone(0);
        ocorrencia1.setNovo(true);
        matricula.getOcorrencias().add(ocorrencia1);
        realm.copyToRealmOrUpdate(ocorrencia1);
        realm.copyToRealmOrUpdate(matricula);
        realm.commitTransaction();
    }

    public FuncionarioEscola recuperarFuncionarioEscola() {
        for (FuncionarioEscola funcionarioEscola : funcionario.getFuncionarioEscolas()) {
            if (funcionarioEscola.getUnidade().getId() == preferences.getSavedLong("id_unidade")) {
                return funcionarioEscola;
            }
        }
        return null;
    }

    public void recuperarRegistrosAluno(Matricula matricula) {
        Turma turma = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        AnoLetivo anoLetivo = realm.where(AnoLetivo.class).equalTo("id", turma.getAnoLetivo().getId()).findFirst();

        preferences.saveLong("id_anoletivo", anoLetivo.getId());
        preferences.saveLong("id_matricula", matricula.getId());
    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getNome());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getDescricao());
        bimestreFragmentOcorrencia.setText(realm.where(Bimestre.class).equalTo("id", preferences.getSavedLong("id_bimestre")).findFirst().getDescricao());
    }

    public void recuperarDadosRealm() {
        unidadeSelecionada = realm.where(Unidade.class).equalTo("id", preferences.getSavedLong("id_unidade")).findFirst();
        turmaSelecionada = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplinaSelecionada = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }
}

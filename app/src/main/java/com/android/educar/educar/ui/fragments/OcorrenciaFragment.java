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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.OcorrenciaListaAdapter;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AnoLetivo;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.FuncionarioEscola;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.ui.activities.NotificacoesAlunoAcitivity;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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

    private LinearLayout cardUnidade;
    private LinearLayout cardTurma;
    private LinearLayout cardDisciplina;

    private TextView bimestreFragmentOcorrencia;

    private Realm realm;

    private PessoaFisica pessoaFisica;
    private Aluno aluno;
    private Matricula matricula;

    private Aluno aluno2;
    private Matricula matricula2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ocorrencia, container, false);
        binding(view);
        setupInit();
        configRealm();
        onClickItem();
        recuperarDadosRealm();
        atualizarDadosTela();
        recuperarAlunosRealm();
        return view;
    }

    public void setupInit() {
        preferences = new Preferences(getContext());
        utilsFunctions = new UtilsFunctions();
        registerForContextMenu(pessoas);
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

        cardDisciplina = view.findViewById(R.id.ocorrenciadcard_isciplina_id);
        cardTurma = view.findViewById(R.id.ocorrenciacard_turma_id);
        cardUnidade = view.findViewById(R.id.ocorrenciacard_unidade_id);
        bimestreFragmentOcorrencia = view.findViewById(R.id.bimestre_fragment_ocorrencia_id);
    }

    private void atualizarLista(List<PessoaFisica> results) {
        ArrayAdapter<PessoaFisica> alunoArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
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
        List<Aluno> alunos = new ArrayList<>();
        List<PessoaFisica> pessoaFisicas = new ArrayList<>();

        RealmResults<Matricula> matriculas = realm.where(Matricula.class).equalTo("turma", preferences.getSavedLong("id_turma")).findAll();

        for (Matricula matricula : matriculas) {
            Aluno aluno = realm.where(Aluno.class).equalTo("id", matricula.getAluno()).findFirst();
            if (aluno != null) {
                alunos.add(aluno);
            }
        }

        for (Aluno aluno : alunos) {
            PessoaFisica pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", aluno.getPessoaFisica()).findFirst();
            if (pessoaFisica != null) {
                pessoaFisicas.add(pessoaFisica);
            }
        }

        atualizarLista(pessoaFisicas);
    }

    public void onClickItem() {

        pessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PessoaFisica pessoaFisica = (PessoaFisica) pessoas.getItemAtPosition(position);
                Long id2 = pessoaFisica.getId();
                preferences.saveLong("id_pessoafisica_aluno", id2);

                aluno2 = realm.where(Aluno.class).equalTo("pessoaFisica", id2).findFirst();
                matricula2 = realm.where(Matricula.class).equalTo("aluno", aluno2.getId()).findFirst();

                recuperarRegistrosAluno(pessoaFisica.getId());
                view.showContextMenu();
            }
        });

        cardDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + disciplinaSelecionada.getDescricao(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cardUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + unidadeSelecionada.getNome(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cardTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + turmaSelecionada.getDescricao(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void novaOcorrencia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View viewDialog = getLayoutInflater().inflate(R.layout.dialog_nova_ocorrencia, null);

        final EditText ocorrenciaText = viewDialog.findViewById(R.id.ocorrencia_id);
        final Spinner tiposOcorrencia = viewDialog.findViewById(R.id.tipos_ocorrencia_id);
        final EditText aluno = viewDialog.findViewById(R.id.aluno_ocorrencia_id);

        aluno.setText(realm.where(PessoaFisica.class).equalTo("id", preferences.getSavedLong("id_pessoafisica_aluno")).findFirst().getNome());
        aluno.setEnabled(false);

        ArrayAdapter<TipoOcorrencia> tipoOcorrenciaArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, realm.where(TipoOcorrencia.class).findAll());
        tiposOcorrencia.setAdapter(tipoOcorrenciaArrayAdapter);

        builder.setView(viewDialog).setTitle("Registrar Notificação!")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TipoOcorrencia tipoOcorrencia = (TipoOcorrencia) tiposOcorrencia.getSelectedItem();

                        salvarOcorrenciaRealm(tipoOcorrencia, ocorrenciaText.getText().toString());

                        Toast.makeText(getContext(), "A Notificação foi Registrada para o Aluno "
                                + realm.where(PessoaFisica.class).equalTo("id", preferences.getSavedLong("id_pessoafisica_aluno")).findFirst().getNome()
                                + "!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancelar", null).show();

    }

    public void salvarOcorrenciaRealm(TipoOcorrencia tipoOcorrencia, String descricao) {
        Ocorrencia ocorrencia1 = new Ocorrencia();
        ocorrencia1.setDatahora(UtilsFunctions.formatoDataPadrao().format(new Date()));
        ocorrencia1.setDatahoracadastro(UtilsFunctions.formatoDataPadrao().format(new Date()));
        ocorrencia1.setFuncionarioEscola(realm.where(FuncionarioEscola.class)
                .equalTo("funcionario", preferences.getSavedLong("id_funcionario"))
                .equalTo("unidade", preferences.getSavedLong("id_unidade")).findFirst().getId());
        ocorrencia1.setDescricao(tipoOcorrencia.getDescricao());
        ocorrencia1.setMatriculaAluno(matricula2.getId());
        ocorrencia1.setTipoOcorrencia(tipoOcorrencia.getId());
        ocorrencia1.setAluno(preferences.getSavedLong("id_aluno"));
        ocorrencia1.setAnoLetivo(preferences.getSavedLong("id_anoletivo"));
        ocorrencia1.setFuncionarioEscola(preferences.getSavedLong("id_funcionario"));
        ocorrencia1.setUnidade(preferences.getSavedLong("id_unidade"));
        ocorrencia1.setEnviadoSms(false);
        ocorrencia1.setDataEnvioSms(null);
        ocorrencia1.setResumoSms(tipoOcorrencia.getDescricao());
        ocorrencia1.setObservacao(descricao);
        ocorrencia1.setNumeroTelefone(0);
        ocorrencia1.setNovo(true);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ocorrencia1);
        realm.commitTransaction();
    }

    public void recuperarRegistrosAluno(long idPessoaFisica) {
        Aluno aluno = realm.where(Aluno.class).equalTo("pessoaFisica", idPessoaFisica).findFirst();
        Matricula matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();
        Turma turma = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        AnoLetivo anoLetivo = realm.where(AnoLetivo.class).equalTo("id", turma.getAnoLetivo()).findFirst();

        preferences.saveLong("id_anoletivo", anoLetivo.getId());
        preferences.saveLong("id_aluno", aluno.getId());
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

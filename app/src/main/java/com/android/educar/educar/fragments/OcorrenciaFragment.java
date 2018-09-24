package com.android.educar.educar.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.app.DetalheAlunoActivity;
import com.android.educar.educar.dao.ClassDAO;
import com.android.educar.educar.dao.DisciplinaDAO;
import com.android.educar.educar.dao.OcorrenciaDAO;
import com.android.educar.educar.dao.TurmaAlunoDAO;
import com.android.educar.educar.dao.TurmaDAO;
import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.utils.CarregarDados;
import com.android.educar.educar.utils.Messages;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.List;

public class OcorrenciaFragment extends Fragment {

    private ListView alunos;
    private Preferences preferences;
    private UtilsFunctions utilsFunctions;

    private TextView unidadeSelecionadaAula;
    private TextView turmaSelecionadaAula;
    private TextView disciplinaSelecionadaAula;

    private Unidade unidadeSelecionada;
    private Disciplina disciplinaSelecionada;
    private Turma turmaSelecionada;

    private ClassDAO classDAO;

    private LinearLayout cardUnidade;
    private LinearLayout cardTurma;
    private LinearLayout cardDisciplina;

    private TurmaAlunoDAO turmaAlunoDAO;
    private UnidadeDAO unidadeDAO;
    private DisciplinaDAO disciplinaDAO;
    private TurmaDAO turmaDAO;
    private OcorrenciaDAO ocorrenciaDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ocorrencia, container, false);
        binding(view);
        setupInit();
        onClickItem();
        atualizarDadosTela();
        return view;
    }

    public void setupInit() {

        turmaAlunoDAO = new TurmaAlunoDAO(getContext());
        unidadeDAO = new UnidadeDAO(getContext());
        disciplinaDAO = new DisciplinaDAO(getContext());
        turmaDAO = new TurmaDAO(getContext());
        ocorrenciaDAO = new OcorrenciaDAO(getContext());

        preferences = new Preferences(getContext());
        utilsFunctions = new UtilsFunctions();

        classDAO = new ClassDAO(getContext());

//        CarregarDados carregarDados = new CarregarDados(getContext());
//        carregarDados.turmaAluno();

        atualizarLista(turmaAlunoDAO.selecionarTurmaAluno(preferences.getSavedLong("id_turma")));

        unidadeSelecionada = unidadeDAO.selecionarUnidade(preferences.getSavedLong("id_unidade"));
        turmaSelecionada = turmaDAO.selecionarTurma(preferences.getSavedLong("id_turma"));
        disciplinaSelecionada = disciplinaDAO.selecionarDiscipina(preferences.getSavedLong("id_disciplina"));

        registerForContextMenu(alunos);
    }

    public void binding(View view) {
        alunos = view.findViewById(R.id.alunos_list_id);
        unidadeSelecionadaAula = view.findViewById(R.id.unidade_selecionada_aula_ocorrencia_id);
        turmaSelecionadaAula = view.findViewById(R.id.turma_selecionada_aula_ocorrencia_id);
        disciplinaSelecionadaAula = view.findViewById(R.id.diciplina_selecionada_aula_ocorrencia_id);

        cardDisciplina = view.findViewById(R.id.ocorrenciadcard_isciplina_id);
        cardTurma = view.findViewById(R.id.ocorrenciacard_turma_id);
        cardUnidade = view.findViewById(R.id.ocorrenciacard_unidade_id);
    }

    private void atualizarLista(List<Aluno> results) {
        ArrayAdapter<Aluno> alunoArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
        alunos.setAdapter(alunoArrayAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final MenuItem detalhar = menu.add("Detalhar");

        detalhar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getContext(), DetalheAlunoActivity.class));
                return false;
            }
        });
    }

    public void onClickItem() {
        alunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) alunos.getItemAtPosition(position);
                preferences.saveLong("id_aluno", aluno.getPk());
                novaOcorrencia();
            }
        });

        cardDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + disciplinaSelecionada.getNome(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        cardUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "" + unidadeSelecionada.getNomeUnidade(), Snackbar.LENGTH_LONG)
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

        final EditText ocorrencia = viewDialog.findViewById(R.id.ocorrencia_id);

        builder.setView(viewDialog).setTitle("Registrar Ocorrência!")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Ocorrencia ocorrencia1 = new Ocorrencia();
                        ocorrencia1.setAluno(preferences.getSavedLong("id_aluno"));
                        ocorrencia1.setDescricao(ocorrencia.getText().toString());
                        ocorrenciaDAO.adicionarOcorrencia(ocorrencia1);
                        Toast.makeText(getContext(), "Ocorrência Salva!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancelar", null).show();

    }

    public void atualizarDadosTela() {
        unidadeSelecionadaAula.setText(unidadeSelecionada.getNomeUnidade());
        turmaSelecionadaAula.setText(turmaSelecionada.getDescricao());
        disciplinaSelecionadaAula.setText(disciplinaSelecionada.getNome());
    }
}

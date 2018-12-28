package com.android.educar.educar.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.android.educar.educar.R;
//import com.android.educar.educar.dao.AlunoDAO;
//import com.android.educar.educar.dao.ClassDAO;
//import com.android.educar.educar.dao.DisciplinaDAO;
//import com.android.educar.educar.dao.UnidadeDAO;
import com.android.educar.educar.utils.Preferences;


public class DetalheAlunoActivity extends AppCompatActivity {

  private EditText nome;
  private EditText id;
  private EditText unidade;
  private EditText turma;
  private Preferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalhes_aluno);
    binding();
    setupInit();
    atualizarDados();
  }

  public void binding() {
    nome = findViewById(R.id.nome_aluno_detalhe_id);
    turma = findViewById(R.id.turma_detalhe_id);
    unidade = findViewById(R.id.unidade_detalhe_id);
    id = findViewById(R.id.id_aluno_detalhe_id);
  }

  public void setupInit() {
    preferences = new Preferences(getApplicationContext());
  }

  public void atualizarDados() {
//        nome.setText(alunoDAO.selecionarAluno(preferences.getSavedLong("id_aluno")).getNomeAluno());
    id.setText("" + preferences.getSavedLong("id_aluno"));
//        turma.setText(disciplinaDAO.selecionarDiscipina(preferences.getSavedLong("id_disciplina")).getNome());
//        unidade.setText(unidadeDAO.selecionarUnidade(preferences.getSavedLong("id_unidade")).getNomeUnidade());
  }
}
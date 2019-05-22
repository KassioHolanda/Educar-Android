package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.model.modelalterado.Aluno;
import com.android.educar.educar.model.modelalterado.Disciplina;
import com.android.educar.educar.model.modelalterado.Frequencia;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.model.modelalterado.PessoaFisica;
import com.android.educar.educar.model.modelalterado.Turma;
import com.android.educar.educar.utils.Preferences;

import java.util.List;

import io.realm.Realm;

public class FrequenciaMensalActivity extends AppCompatActivity {

    private Realm realm;
    private TextView alunoDetalhe;
    private TextView turmaDetalhe;
    private TextView disciplinaDetalhe;
    private PessoaFisica pessoaFisica;
    private Matricula matricula;
    private Aluno aluno;
    private Preferences preferences;
    private Turma turma;
    private Disciplina disciplina;
    private ListView listaDeFaltas;

    public void binding() {
        alunoDetalhe = findViewById(R.id.aluno2_notificacoes_id);
        turmaDetalhe = findViewById(R.id.turma2_notificacoes_aluno_id);
        disciplinaDetalhe = findViewById(R.id.disciplina2_notificacao_aluno_id);
//        calendarView = findViewById(R.id.calendarView);
        listaDeFaltas = findViewById(R.id.lista_de_faltas_id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencia_mensal);
        configRealm();
        binding();
//        inicalizarCalendario();
    }

    public void consultandoDatasPresenca() {
        Frequencia frequencia = realm.where(Frequencia.class).findFirst();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupInit();
        recuperarDadosRealm();
        atualizarDadosTela();
        consultandoDatasPresenca();
        atualizandoListaDePresenca();
    }

    public void setupInit() {
        preferences = new Preferences(this);
    }

    public void configRealm() {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public void recuperarDadosRealm() {
        matricula = realm.where(Matricula.class).equalTo("id", preferences.getSavedLong("id_matricula_lista")).findFirst();
        turma = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplina = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }

    public void atualizarDadosTela() {
        turmaDetalhe.setText(turma.getDescricao());
        alunoDetalhe.setText(matricula.getAluno().getPessoaFisica().getNome());
        disciplinaDetalhe.setText(disciplina.getDescricao());
    }

    public void atualizandoListaDePresenca() {
        List<Frequencia> frequenciasLista = realm.where(Frequencia.class).equalTo("presenca", false).equalTo("matricula", matricula.getId()).findAll();
        ArrayAdapter<Frequencia> frequencias = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, frequenciasLista);
        listaDeFaltas.setAdapter(frequencias);

        listaDeFaltas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alterarPresenca(frequenciasLista.get(i));
            }
        });
    }

    public void alterarPresenca(Frequencia frequencia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View viewDialog = inflater.inflate(R.layout.dialog_alterar_presenca_aluno, null);

        final EditText aluno = viewDialog.findViewById(R.id.aluno_presenca_mensal_id);
        final EditText data = viewDialog.findViewById(R.id.data_presenca_mensal_id);
        final CheckBox preseca = viewDialog.findViewById(R.id.presenca_id_mensal);

        aluno.setText(realm.copyFromRealm(realm.where(Matricula.class).equalTo("id", frequencia.getMatricula()).findFirst()).getAluno().getPessoaFisica().getNome());
        aluno.setEnabled(false);
        data.setText(frequencia.getDate());
        data.setEnabled(false);
        preseca.setChecked(frequencia.isPresenca());

        preseca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preseca.isChecked()) {
                    realm.beginTransaction();
                    frequencia.setPresenca(true);
                    realm.copyToRealmOrUpdate(frequencia);
                    realm.commitTransaction();
                }
            }
        });

        builder.setView(viewDialog).setTitle("Alterar Presença")
                .setCancelable(false)
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }

                }).setNegativeButton("Cancelar", null).show();
    }
}

package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.model.TipoOcorrencia;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.network.chamadas.PessoaChamada;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;
import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

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
        pessoaFisica = realm.where(PessoaFisica.class).equalTo("id", preferences.getSavedLong("id_pessoafisica_aluno")).findFirst();
        aluno = realm.where(Aluno.class).equalTo("pessoaFisica", pessoaFisica.getId()).findFirst();
        matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();
        turma = realm.where(Turma.class).equalTo("id", preferences.getSavedLong("id_turma")).findFirst();
        disciplina = realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst();
    }

    public void atualizarDadosTela() {
        turmaDetalhe.setText(turma.getDescricao());
        alunoDetalhe.setText(pessoaFisica.getNome());
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

        aluno.setText(realm.where(PessoaFisica.class).equalTo("id", frequencia.getPessoafisica()).findFirst().getNome());
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

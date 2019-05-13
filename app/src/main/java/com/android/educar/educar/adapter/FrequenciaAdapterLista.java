package com.android.educar.educar.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class FrequenciaAdapterLista extends BaseAdapter {

    private Frequencia frequencia;
    private List<PessoaFisica> pessoaFisicas;
    private Context context;
    private TextView nomeAlunoFrequencia;
    private final List<PessoaFisica> selecionados;
    private Realm realm;
    private Preferences preferences;
    private Frequencia frequencia2;

    public FrequenciaAdapterLista(Context context, List<PessoaFisica> pessoaFisicas) {
        this.pessoaFisicas = pessoaFisicas;
        this.context = context;
        selecionados = new ArrayList<>();
        configRealm();
        preferences = new Preferences(context);
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return pessoaFisicas.size();
    }

    @Override
    public Object getItem(int i) {
        return pessoaFisicas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout linearLayout;
        View row = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            row = inflater.inflate(R.layout.alunos_lista_frequencia, viewGroup, false);
        } else {
            row = view;
        }

        Aluno aluno = realm.where(Aluno.class).equalTo("pessoaFisica", pessoaFisicas.get(i).getId()).findFirst();
        final Matricula matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();

        nomeAlunoFrequencia = row.findViewById(R.id.nomealuno_frequencia_id);
        CheckBox chk = row.findViewById(R.id.presenca_id);
        TextView idAluno = row.findViewById(R.id.idalunonota_id);

        int ordem = i;
        ordem = ordem + 1;
        idAluno.setText("" + ordem);

        final PessoaFisica pessoaFisicaSelecionada = pessoaFisicas.get(i);

        frequencia2 = recuperarFrequencia(pessoaFisicaSelecionada);
        if (frequencia2 != null) {
            if (frequencia2.isPresenca()) {
                selecionados.add(pessoaFisicaSelecionada);
            }
        }

        nomeAlunoFrequencia.setText(pessoaFisicaSelecionada.getNome());
        chk.setTag(pessoaFisicaSelecionada);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                PessoaFisica pessoaFisica1 = (PessoaFisica) checkBox.getTag();
                if (checkBox.isChecked()) {
                    atualizarPresenca(pessoaFisicaSelecionada, matricula, true);
                    Toast.makeText(context, "O Aluno " + pessoaFisica1.getNome() + " está Presente.", Toast.LENGTH_SHORT).show();
                    if (!selecionados.contains(pessoaFisica1)) {
                        selecionados.add(pessoaFisica1);
                    }

                } else {
                    atualizarPresenca(pessoaFisicaSelecionada, matricula, false);
                    Toast.makeText(context, "O Aluno " + pessoaFisica1.getNome() + " não está Presente.", Toast.LENGTH_SHORT).show();
                    if (selecionados.contains(pessoaFisica1)) {
                        selecionados.remove(pessoaFisica1);
                    }
                }
            }
        });

        if (selecionados.contains(pessoaFisicaSelecionada)) {
            chk.setChecked(true);
        } else {
            chk.setChecked(false);
        }
        return row;
    }

    public void atualizarPresenca(PessoaFisica pessoaFisica, Matricula matricula, boolean presenca) {

        Frequencia frequenciaConsulta = recuperarFrequencia(pessoaFisica);

        if (frequenciaConsulta != null) {
            realm.beginTransaction();
            frequenciaConsulta.setPresenca(presenca);
            if (!frequenciaConsulta.isNovo()) {
                frequenciaConsulta.setAlterado(true);
            }
            realm.copyToRealmOrUpdate(frequenciaConsulta);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            frequencia = new Frequencia();
            frequencia.setId(Long.valueOf(realm.where(Frequencia.class).findAll().size() + 1));
            frequencia.setMatricula(matricula.getId());
            frequencia.setTurma(preferences.getSavedLong("id_turma"));
            frequencia.setUnidade(preferences.getSavedLong("id_unidade"));
            frequencia.setDisciplina(preferences.getSavedLong("id_disciplina"));
            frequencia.setPresenca(presenca);
            frequencia.setDate(UtilsFunctions.apenasData().format(new Date()));
            frequencia.setPessoafisica(pessoaFisica.getId());
            frequencia.setNovo(true);
            realm.copyToRealmOrUpdate(frequencia);
            realm.commitTransaction();
        }
    }

    public Frequencia recuperarFrequencia(PessoaFisica pessoaFisica) {
        Frequencia frequencia2 = realm.where(Frequencia.class)
                .equalTo("disciplina", preferences.getSavedLong("id_disciplina"))
                .equalTo("pessoaFisica", pessoaFisica.getId())
                .equalTo("date", UtilsFunctions.apenasData().format(new Date()))
                .equalTo("turma", preferences.getSavedLong("id_turma"))
                .equalTo("unidade", preferences.getSavedLong("id_unidade")).findFirst();
        return frequencia2;
    }
}

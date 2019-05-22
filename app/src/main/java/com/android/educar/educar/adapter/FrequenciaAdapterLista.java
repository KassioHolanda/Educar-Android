package com.android.educar.educar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.modelalterado.Frequencia;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;


public class FrequenciaAdapterLista extends BaseAdapter {

    private Frequencia frequencia;
    private List<Matricula> matriculas;
    private Context context;
    private TextView nomeAlunoFrequencia;
    private final List<Matricula> selecionados;
    private Realm realm;
    private Preferences preferences;
    private Frequencia frequencia2;

    public FrequenciaAdapterLista(Context context, List<Matricula> matriculas) {
        this.matriculas = matriculas;
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
        return matriculas.size();
    }

    @Override
    public Object getItem(int i) {
        return matriculas.get(i);
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

        nomeAlunoFrequencia = row.findViewById(R.id.nomealuno_frequencia_id);
        CheckBox chk = row.findViewById(R.id.presenca_id);
        TextView idAluno = row.findViewById(R.id.idalunonota_id);

        int ordem = i;
        ordem = ordem + 1;
        idAluno.setText("" + ordem);

        final Matricula matriculaSelecionada = matriculas.get(i);

        frequencia2 = recuperarFrequencia(matriculaSelecionada);
        if (frequencia2 != null) {
            if (frequencia2.isPresenca()) {
                selecionados.add(matriculaSelecionada);
            }
        }

        nomeAlunoFrequencia.setText(matriculaSelecionada.getAluno().getPessoaFisica().getNome());
        chk.setTag(matriculaSelecionada);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                Matricula matricula1 = (Matricula) checkBox.getTag();
                if (checkBox.isChecked()) {
                    atualizarPresenca(matricula1, true);
                    Toast.makeText(context, "O Aluno " + matricula1.getAluno().getPessoaFisica().getNome() + " está Presente.", Toast.LENGTH_SHORT).show();
                    if (!selecionados.contains(matricula1)) {
                        selecionados.add(matricula1);
                    }

                } else {
                    atualizarPresenca(matricula1, false);
                    Toast.makeText(context, "O Aluno " + matricula1.getAluno().getPessoaFisica().getNome() + " não está Presente.", Toast.LENGTH_SHORT).show();
                    if (selecionados.contains(matricula1)) {
                        selecionados.remove(matricula1);
                    }
                }
            }
        });

        if (selecionados.contains(matriculaSelecionada)) {
            chk.setChecked(true);
        } else {
            chk.setChecked(false);
        }
        return row;
    }

    public void atualizarPresenca(Matricula matricula, boolean presenca) {

        Frequencia frequenciaConsulta = recuperarFrequencia(matricula);

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
            frequencia.setNovo(true);
            realm.copyToRealmOrUpdate(frequencia);
            realm.commitTransaction();
        }
    }

    public Frequencia recuperarFrequencia(Matricula matricula) {
        Frequencia frequencia2 = realm.where(Frequencia.class)
                .equalTo("disciplina", preferences.getSavedLong("id_disciplina"))
                .equalTo("matricula", matricula.getId())
                .equalTo("date", UtilsFunctions.apenasData().format(new Date()))
                .equalTo("turma", preferences.getSavedLong("id_turma"))
                .equalTo("unidade", preferences.getSavedLong("id_unidade")).findFirst();
        if (frequencia2 != null)
            return realm.copyFromRealm(frequencia2);
        return null;
    }
}

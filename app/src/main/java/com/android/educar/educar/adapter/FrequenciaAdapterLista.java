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
import com.android.educar.educar.model.PessoaFisica;

import java.util.ArrayList;
import java.util.List;


public class FrequenciaAdapterLista extends BaseAdapter {

    private int resource;
    private List<PessoaFisica> pessoaFisicas;
    private Context context;
    private TextView nomeAlunoFrequencia;
    private final List<PessoaFisica> selecionados;

    public FrequenciaAdapterLista(Context context, List<PessoaFisica> pessoaFisicas) {
        this.resource = resource;
        this.pessoaFisicas = pessoaFisicas;
        this.context = context;
        selecionados = new ArrayList<>();
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

        nomeAlunoFrequencia = row.findViewById(R.id.nomealuno_frequencia_id);
        CheckBox chk = row.findViewById(R.id.presenca_id);
        TextView idAluno = row.findViewById(R.id.idalunonota_id);

        int ordem = i;
        ordem = ordem+1;
        idAluno.setText(""+ordem);

        PessoaFisica pessoaFisicaSelecionada = pessoaFisicas.get(i);
        nomeAlunoFrequencia.setText(pessoaFisicaSelecionada.getNome());
        chk.setTag(pessoaFisicaSelecionada);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                PessoaFisica pessoaFisica1 = (PessoaFisica) checkBox.getTag();
                if (checkBox.isChecked()) {
                    if (!selecionados.contains(pessoaFisica1)) {
                        selecionados.add(pessoaFisica1);
                    }

                } else {
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
}

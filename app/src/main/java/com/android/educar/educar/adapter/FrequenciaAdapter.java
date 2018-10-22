package com.android.educar.educar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.PessoaFisica;

import java.util.ArrayList;
import java.util.List;

public class FrequenciaAdapter extends RecyclerView.Adapter<FrequenciaAdapter.ViewHolder> {

    private final Context context;
    private final List<PessoaFisica> pessoaFisicas;
    private final List<Frequencia> frequencias;
    private FrequenciaAdapter.ViewHolder holder;
    private Frequencia frequencia;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nomeAluno;
        protected TextView idAluno;
        protected CheckBox presenca;

        public ViewHolder(View view) {
            super(view);
            nomeAluno = view.findViewById(R.id.nomealuno_frequencia_id);
            idAluno = view.findViewById(R.id.idalunonota_id);
            presenca = view.findViewById(R.id.presenca_id);
        }
    }

    public FrequenciaAdapter(Context context, List<PessoaFisica> pessoaFisicas) {
        this.context = context;
        this.pessoaFisicas = pessoaFisicas;
        frequencias = new ArrayList<>();
    }

    @NonNull
    @Override
    public FrequenciaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.alunos_lista_frequencia, parent, false);
        FrequenciaAdapter.ViewHolder viewHolder = new FrequenciaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FrequenciaAdapter.ViewHolder holder, int position) {
        this.holder = holder;
        final Frequencia frequencia = new Frequencia();
        frequencia.setPresente(holder.presenca.isChecked());
        frequencia.setAula(1);
//        frequencia.setAluno(alunos.get(position).getPk());


        holder.nomeAluno.setText(pessoaFisicas.get(position).getNome());
//        holder.idAluno.setText("" + alunos.get(position).getPk());
        holder.presenca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                frequencia.setPresente(holder.presenca.isChecked());
                Toast.makeText(context, "Presença " + holder.presenca.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pessoaFisicas.size();
    }

}

package com.android.educar.educar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Aluno;

import java.util.List;

public class OcorrenciaAdapter extends RecyclerView.Adapter<OcorrenciaAdapter.ViewHolder> {

    private final Context context;
    private final List<Aluno> alunos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nomeAluno;

        public ViewHolder(View view) {
            super(view);
            nomeAluno = view.findViewById(R.id.nomealuno_id);
        }
    }

    public OcorrenciaAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public OcorrenciaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.alunos_lista_ocorrencia, parent, false);
        OcorrenciaAdapter.ViewHolder viewHolder = new OcorrenciaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OcorrenciaAdapter.ViewHolder holder, int position) {
        holder.nomeAluno.setText(alunos.get(position).getNomeAluno());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

}

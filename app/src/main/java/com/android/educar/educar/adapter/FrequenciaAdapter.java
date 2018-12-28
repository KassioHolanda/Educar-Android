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
import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class FrequenciaAdapter extends RecyclerView.Adapter<FrequenciaAdapter.ViewHolder> {

    private final Context context;
    private final List<PessoaFisica> pessoaFisicas;
    private FrequenciaAdapter.ViewHolder holder;
    private Frequencia frequencia;
    private List<Long> idsAluno;
    private Preferences preferences;
    private Realm realm;
    private RealmObjectsBO realmObjectsBO;
    private List<Frequencia> frequencias;

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

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public FrequenciaAdapter(Context context, List<PessoaFisica> pessoaFisicas) {
        this.context = context;
        this.pessoaFisicas = pessoaFisicas;
        idsAluno = new ArrayList<>();
        configRealm();
        realmObjectsBO = new RealmObjectsBO(context);
        preferences = new Preferences(context);
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
    public void onBindViewHolder(@NonNull final FrequenciaAdapter.ViewHolder holder, final int position) {
        this.holder = holder;
        final Frequencia frequencia = new Frequencia();

        Aluno aluno = realm.where(Aluno.class).equalTo("pessoaFisica", pessoaFisicas.get(position).getId()).findFirst();
        final Matricula matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();

        holder.nomeAluno.setText(pessoaFisicas.get(position).getNome());
        holder.idAluno.setText("" + pessoaFisicas.get(position).getId());

        holder.presenca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                frequencia.setMatricula(matricula.getId());
                frequencia.setNovo(true);
                frequencia.setPresenca(holder.presenca.isChecked());
                frequencias.add(frequencia);
//                realmObjectsBO.salvarObjetoRealm(frequencia);

                Toast.makeText(context, "Presença " + holder.presenca.isChecked() + " - Aluno: " + holder.nomeAluno, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pessoaFisicas.size();
    }

}

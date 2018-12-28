package com.android.educar.educar.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.ui.activities.DetalheAlunoActivity;
import com.android.educar.educar.ui.activities.NotaFragmentActivity;
import com.android.educar.educar.mb.NotaMB;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.utils.Preferences;

import java.util.List;

import io.realm.Realm;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.ViewHolder> {

    private final Context context;
    private final List<PessoaFisica> pessoaFisicas;
    private Preferences preferences;
    private NotaMB notaMB;
    private Realm realm;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nomeAluno;
        protected CardView addNota;
        protected TextView idAluno;
        protected TextView notaAluno;

        public ViewHolder(View view) {
            super(view);
            nomeAluno = view.findViewById(R.id.nomealuno_nota_id);
            addNota = view.findViewById(R.id.add_nota_id);
            idAluno = view.findViewById(R.id.idaluno_nota_id);
            notaAluno = view.findViewById(R.id.nota_aluno_id);
        }
    }

    public NotaAdapter(Context context, List<PessoaFisica> pessoaFisicas) {
        this.context = context;
        this.pessoaFisicas = pessoaFisicas;
        this.notaMB = new NotaMB(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public NotaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.alunos_lista_nota, parent, false);
        NotaAdapter.ViewHolder viewHolder = new NotaAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final NotaAdapter.ViewHolder holder, final int position) {

        holder.nomeAluno.setText(pessoaFisicas.get(position).getNome());
        holder.idAluno.setText("" + position);
        try {
            holder.notaAluno.setText("" + preferences.getSavedFloat("id_nota_bimestre"));
        } catch (NullPointerException e) {
            holder.notaAluno.setText("X");
        }

        holder.addNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = new Preferences(context);
                preferences.saveLong("id_pessoafisica", pessoaFisicas.get(position).getId());
                Aluno aluno = realm.where(Aluno.class).equalTo("pessoaFisica", pessoaFisicas.get(position).getId()).findFirst();
                Matricula matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();
                preferences.saveLong("id_matricula", matricula.getId());
                adicionarNota();
            }
        });

        holder.addNota.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.startActivity(new Intent(context, DetalheAlunoActivity.class));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pessoaFisicas.size();
    }


    public void adicionarNota() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        final View viewDialog = inflater.inflate(R.layout.dialog_nova_nota, null);
        final EditText aluno = viewDialog.findViewById(R.id.nome_aluno_nota_id);
        final EditText disciplina = viewDialog.findViewById(R.id.disciplina_nota_id);
        final EditText bimestre = viewDialog.findViewById(R.id.semestre_nota_id);
        final EditText nota = viewDialog.findViewById(R.id.nota_id);

        bimestre.setEnabled(false);
        disciplina.setEnabled(false);
        aluno.setEnabled(false);

        final String nomeAlunoNota = realm.where(PessoaFisica.class).equalTo("id", preferences.getSavedLong("id_pessoafisica")).findFirst().getNome();

        aluno.setText(nomeAlunoNota);
        disciplina.setText(realm.where(Disciplina.class).equalTo("id", preferences.getSavedLong("id_disciplina")).findFirst().getDescricao());
        long idBimestre = notaMB.verificarBimestreAtual();
        bimestre.setText(realm.where(Bimestre.class).equalTo("id", idBimestre).findFirst().getDescricao());

        builder.setView(viewDialog).setTitle("Inserir Nota!")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nota.getText() == null || nota.getText().length() == 0) {
                            alertaInformacao();
                        } else if (Float.valueOf(nota.getText().toString()) > 10) {
                            alertaInformacao();
                        } else {
                            notaMB.salvarAlunoNotaMes(nota.getText().toString());
                            Toast.makeText(context, "Nota Inserida ao aluno " + nomeAlunoNota + "!", Toast.LENGTH_SHORT).show();
                            preferences.saveFloat("id_nota_bimestre", Float.parseFloat(nota.getText().toString()));
                            atualizarFragment();
                        }
                    }
                }).setNegativeButton("Cancelar", null).show();
    }

    public void alertaInformacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Informação!");
        builder.setMessage("A nota Inserida é Inválida!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adicionarNota();
            }

        }).show();
    }

    public void atualizarFragment() {
        context.startActivity(new Intent(context, NotaFragmentActivity.class));
//        FragmentManager fragment = ((FragmentActivity) context).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
//
//        Fragment fragment1 = new NotaFragment();
//
//        fragmentTransaction.add(R.id.id_layout_nota, fragment1);
//        fragmentTransaction.commit();
    }
}


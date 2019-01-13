package com.android.educar.educar.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.NotaMB;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.PessoaFisica;
import com.android.educar.educar.ui.activities.NotaFragmentActivity;
import com.android.educar.educar.utils.Preferences;

import java.util.List;

import io.realm.Realm;

public class NotaAdapterLista extends BaseAdapter {

    private List<PessoaFisica> pessoaFisicaList;
    private Context context;
    private Realm realm;
    private NotaMB notaMB;
    protected TextView nomeAluno;
    protected CardView addNota;
    protected TextView idAluno;
    protected TextView notaAluno;
    private Preferences preferences;


    public NotaAdapterLista(List<PessoaFisica> pessoaFisicaList, Context context) {
        this.pessoaFisicaList = pessoaFisicaList;
        this.context = context;
        notaMB = new NotaMB(context);
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return pessoaFisicaList.size();
    }

    @Override
    public Object getItem(int i) {
        return pessoaFisicaList.get(i);
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
            row = inflater.inflate(R.layout.alunos_lista_nota, viewGroup, false);
        } else {
            row = view;
        }

        nomeAluno = row.findViewById(R.id.nomealuno_nota_id);
        addNota = row.findViewById(R.id.add_nota_id);
        idAluno = row.findViewById(R.id.idaluno_nota_id);
        notaAluno = row.findViewById(R.id.nota_aluno_id);

        int ordem = i;
        final int position = i;
        ordem = ordem + 1;

        idAluno.setText("" + ordem);
        nomeAluno.setText(pessoaFisicaList.get(i).getNome());

        addNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = new Preferences(context);
                preferences.saveLong("id_pessoafisica", pessoaFisicaList.get(position).getId());
                Aluno aluno = realm.where(Aluno.class).equalTo("pessoaFisica", pessoaFisicaList.get(position).getId()).findFirst();
                Matricula matricula = realm.where(Matricula.class).equalTo("aluno", aluno.getId()).findFirst();
                preferences.saveLong("id_matricula", matricula.getId());
                adicionarNota();
            }
        });

        return row;
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
                            Snackbar.make(viewDialog, "Nota Inserida ao aluno " + nomeAlunoNota + "!", Snackbar.LENGTH_SHORT).show();
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

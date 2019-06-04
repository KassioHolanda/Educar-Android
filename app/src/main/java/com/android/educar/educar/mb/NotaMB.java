package com.android.educar.educar.mb;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.educar.educar.model.modelalterado.AlunoNotaMes;
import com.android.educar.educar.model.modelalterado.Disciplina;
import com.android.educar.educar.model.modelalterado.DisciplinaAluno;
import com.android.educar.educar.model.modelalterado.Matricula;
import com.android.educar.educar.model.modelalterado.SerieDisciplina;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;

public class NotaMB {

    private Realm realm;
    private Context context;
    private Preferences preferences;
    private DisciplinaAluno disciplinaAluno;
    private SerieDisciplina serieDisciplina;
    private BimestreMB bimestreMB;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public NotaMB(Context context) {
        preferences = new Preferences(context);
        this.context = context;
        this.disciplinaAluno = new DisciplinaAluno();
        this.serieDisciplina = new SerieDisciplina();
        bimestreMB = new BimestreMB(context);
        configRealm();
    }

    public void alertarErro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alerta!");
        builder.setMessage("Ocorreu um Erro, solicite Administrador!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public void salvarAlunoNotaMes(String descricao, Matricula matricula) {
        verificarStatusAtualDisciplinaAluno(matricula);
        verificarSerieDisciplina(matricula);

        if (disciplinaAluno == null || serieDisciplina == null) {
//            Toast.makeText(context, "Ocorreu um Erro, solicite Administrador", Toast.LENGTH_LONG).show();
            alertarErro();
        } else {
            if (verificarStatusParaAdicionarNota()) {
//                Matricula matricula = realm.where(Matricula.class).equalTo("id", preferences.getSavedLong("id_matricula")).findFirst();

                AlunoNotaMes alunoNotaMes = new AlunoNotaMes();
//                alunoNotaMes.setUnidade(preferences.getSavedLong("id_unidade"));
                alunoNotaMes.setDatahora(UtilsFunctions.formatoDataPadrao().format(new Date()));
                alunoNotaMes.setDisciplina(disciplinaAluno.getSerieDisciplina().getDisciplina());
                alunoNotaMes.setMatricula(matricula.getId());
                alunoNotaMes.setNota(Float.parseFloat(descricao));
                alunoNotaMes.setUsuario(preferences.getSavedLong("id_usuario"));
//                alunoNotaMes.setBimestre(bimestreMB.getBimestreAtual());
//                alunoNotaMes.setAnoLetivo(matricula.getAnoLetivo());
                alunoNotaMes.setTipoLancamentoNota("LANCADO_APP");
                alunoNotaMes.setInseridoFechamento(false);
                alunoNotaMes.setDisciplinaAluno(disciplinaAluno.getId());
                alunoNotaMes.setNovo(true);
                alunoNotaMes.setAlterado(false);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(alunoNotaMes);
                realm.commitTransaction();
//                atualizarDadosDisciplinaAluno(Float.parseFloat(descricao));
                Toast.makeText(context, "Nota Inserida com Sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Não é mais Permitido Inserir Nota", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void atualizarAlunoNotaMes(String descricao, Matricula matriculaId) {
        try {
            AlunoNotaMes alunoNotaMes = recuperarNotaMatricula(matriculaId);
            realm.beginTransaction();
            alunoNotaMes.setAlterado(true);
            alunoNotaMes.setNota(Float.parseFloat(descricao));
            realm.copyToRealmOrUpdate(alunoNotaMes);
            realm.copyToRealmOrUpdate(matriculaId);
            realm.commitTransaction();
            Toast.makeText(context, "Nota do aluno Atualizada com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            alertarErro();
            Toast.makeText(context, "Ocorreu um Erro, solicite Administrador", Toast.LENGTH_LONG).show();
        }

    }

    public void atualizarDadosDisciplinaAluno(Float nota) {
        realm.beginTransaction();
        disciplinaAluno.setAlterado(true);
        if (disciplinaAluno.getMesesFechadosNota() == 0) {
            disciplinaAluno.setMesesFechadosNota(1);
        }

        disciplinaAluno.setNotaAcumulada(nota + disciplinaAluno.getNotaAcumulada());
        disciplinaAluno.setMediaAculumada(disciplinaAluno.getNotaAcumulada() / disciplinaAluno.getMesesFechadosNota());

        if (disciplinaAluno.getMediaAculumada() >= 6) {
            disciplinaAluno.setStatusAtual("APROVADO");
        } else {
            disciplinaAluno.setStatusAtual("REPROVADO");
        }

        realm.commitTransaction();
    }

    public boolean verificarStatusParaAdicionarNota() {
//        if (this.disciplinaAluno.getStatusDisciplinaAluno().equals("EM_ANDAMENTO")) {
        return true;
//        } else {
//            return false;
//        }
    }


    public DisciplinaAluno verificarStatusAtualDisciplinaAluno(Matricula matricula) {
        DisciplinaAluno disciplinaAluno = realm.copyFromRealm(realm.where(DisciplinaAluno.class)
                .equalTo("matricula", preferences.getSavedLong("id_matricula"))
                .equalTo("serieDisciplina.id", verificarSerieDisciplina(matricula).getId())
                .findFirst());

        this.disciplinaAluno = disciplinaAluno;
        return disciplinaAluno;
    }

    public SerieDisciplina verificarSerieDisciplina(Matricula matricula) {

        SerieDisciplina ro = realm.where(SerieDisciplina.class)
                .equalTo("serie.id", matricula.getSerie().getId())
                .equalTo("disciplina.id", preferences.getSavedLong("id_disciplina"))
                .findFirst();

        serieDisciplina = realm.copyFromRealm(ro);

        return serieDisciplina;
    }

    public AlunoNotaMes recuperarNotaMatricula(Matricula matriculaAluno) {

        for (DisciplinaAluno disciplina : matriculaAluno.getDisciplinaAlunos()) {
            if (disciplina.getSerieDisciplina().getId() == verificarSerieDisciplina(matriculaAluno).getId()) {
                disciplinaAluno = disciplina;
                for (AlunoNotaMes alunoNotaMes : matriculaAluno.getAlunosNotaMes()) {
                    if (alunoNotaMes.getBimestre().getId().equals(bimestreMB.getBimestreAtual()) && alunoNotaMes.getDisciplinaAluno().equals(disciplina.getId())) {
                        return alunoNotaMes;
                    }
                }
            }
        }
        return null;
    }
}

package com.android.educar.educar.mb;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.util.Date;

import io.realm.Realm;

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

    public void salvarAlunoNotaMes(String descricao) {
        verificarStatusAtualDisciplinaAluno();
        verificarSerieDisciplina();

        if (disciplinaAluno == null || serieDisciplina == null) {
//            Toast.makeText(context, "Ocorreu um Erro, solicite Administrador", Toast.LENGTH_LONG).show();
            alertarErro();
        } else {
            if (verificarStatusParaAdicionarNota()) {
                Matricula matricula = realm.where(Matricula.class).equalTo("id", preferences.getSavedLong("id_matricula")).findFirst();

                AlunoNotaMes alunoNotaMes = new AlunoNotaMes();
                alunoNotaMes.setUnidade(preferences.getSavedLong("id_unidade"));
                alunoNotaMes.setDatahora(UtilsFunctions.formatoDataPadrao().format(new Date()));
                alunoNotaMes.setDisciplina(preferences.getSavedLong("id_disciplina"));
                alunoNotaMes.setMatricula(matricula.getId());
                alunoNotaMes.setNota(Float.parseFloat(descricao));
                alunoNotaMes.setUsuario(preferences.getSavedLong("id_usuario"));
                alunoNotaMes.setBimestre(bimestreMB.getBimestreAtual());
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

    public void atualizarAlunoNotaMes(String descricao, long matriculaId) {
        try {
            AlunoNotaMes alunoNotaMes = recuperarNotaMatricula(matriculaId);
            realm.beginTransaction();
            alunoNotaMes.setAlterado(true);
            alunoNotaMes.setNota(Float.parseFloat(descricao));
            realm.copyToRealmOrUpdate(alunoNotaMes);
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


    public void verificarStatusAtualDisciplinaAluno() {
        Long id = verificarSerieDisciplina().getId();
        Long ma = preferences.getSavedLong("id_matricula");

        DisciplinaAluno disciplinaAluno = realm.where(DisciplinaAluno.class)
                .equalTo("matricula", preferences.getSavedLong("id_matricula"))
                .equalTo("serieDisciplina", verificarSerieDisciplina().getId())
                .findFirst();

        this.disciplinaAluno = disciplinaAluno;
    }

    public SerieDisciplina verificarSerieDisciplina() {

        serieDisciplina = realm.where(SerieDisciplina.class)
                .equalTo("serie", preferences.getSavedLong("id_serie"))
                .equalTo("disciplina", preferences.getSavedLong("id_disciplina"))
                .findFirst();


        return serieDisciplina;
    }

    public AlunoNotaMes recuperarNotaMatricula(Long matriculaAluno) {
        DisciplinaAluno disciplinaAluno = realm.where(DisciplinaAluno.class)
                .equalTo("matricula", matriculaAluno)
                .equalTo("serieDisciplina", verificarSerieDisciplina().getId())
                .findFirst();

        if (disciplinaAluno != null) {

            AlunoNotaMes alunoNotaMes = realm.where(AlunoNotaMes.class)
                    .equalTo("matricula", matriculaAluno)
                    .equalTo("bimestre", bimestreMB.getBimestreAtual())
                    .equalTo("disciplinaAluno", disciplinaAluno.getId())
                    .findFirst();

            return alunoNotaMes;

        } else {

            return null;
        }
    }

}

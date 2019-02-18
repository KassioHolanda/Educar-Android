package com.android.educar.educar.mb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.DisciplinaAluno;
import com.android.educar.educar.model.Matricula;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.SerieDisciplina;
import com.android.educar.educar.model.SituacaoTurmaMes;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.UtilsFunctions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

public class NotaMB {

    private Realm realm;
    private RealmObjectsBO realmObjectsBO;
    private Context context;
    private Preferences preferences;
    private DisciplinaAluno disciplinaAluno;
    private SerieDisciplina serieDisciplina;
    private Long idBimestreAtual;
    private Long ultimoIdBimestre;

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public NotaMB(Context context) {
        preferences = new Preferences(context);
        this.context = context;
        this.realmObjectsBO = new RealmObjectsBO(context);
        this.disciplinaAluno = new DisciplinaAluno();
        this.serieDisciplina = new SerieDisciplina();
        this.idBimestreAtual = Long.valueOf(0);
        this.ultimoIdBimestre = Long.valueOf(0);
        configRealm();
        verificarBimestreAtual();
    }

    public void salvarAlunoNotaMes(String descricao) {
        verificarStatusAtualDisciplinaAluno();
        verificarSerieDisciplina();

        if (disciplinaAluno == null || serieDisciplina == null) {
            Toast.makeText(context, "Ocorreu um Erro, solicite Administrador", Toast.LENGTH_LONG).show();
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
                alunoNotaMes.setBimestre(verificarBimestreAtual());
                alunoNotaMes.setAnoLetivo(matricula.getAnoLetivo());
                alunoNotaMes.setTipoLancamentoNota("LANCADO_APP");
                alunoNotaMes.setInseridoFechamento(false);
                alunoNotaMes.setDisciplinaAluno(disciplinaAluno.getId());
                alunoNotaMes.setNovo(true);
                alunoNotaMes.setAlterado(false);
                realmObjectsBO.salvarObjetoRealm(alunoNotaMes);
//                atualizarDadosDisciplinaAluno(Float.parseFloat(descricao));
            } else {
                Toast.makeText(context, "A Nota Ja foi Informada", Toast.LENGTH_LONG).show();
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
        } catch (NullPointerException e) {
            Log.i("ERRO", "Matricula NULL");
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

    public Long verificarBimestreAtual() {
        RealmResults<SituacaoTurmaMes> situacaoTurmaMes = realm.where(SituacaoTurmaMes.class)
                .equalTo("turma", preferences.getSavedLong("id_turma")).findAll();

        if (situacaoTurmaMes.size() == 0) {
            criarNovaSituacaoTurmaMes();

        } else {
//            for (int i = 0; i < situacaoTurmaMes.size(); i++) {
            if (situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getStatus().equals("ABERTO")) {
                this.idBimestreAtual = situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getBimestre();
            } else if (situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getBimestre() == 5 && situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getStatus().equals("FECHADO")) {
                preferences.saveLong("id_bimestre", 5);
//                preferences.saveBoolean("bimestre_5_fechado", true);
                return Long.valueOf(5);
            } else if (situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getBimestre() < 5 && situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getStatus().equals("FECHADO")) {
                this.ultimoIdBimestre = situacaoTurmaMes.get(situacaoTurmaMes.size() - 1).getBimestre();
                criarNovaSituacaoTurmaMes();
            }
//            }
        }

        if (idBimestreAtual == 0)
            return criarNovaSituacaoTurmaMes();

        preferences.saveLong("id_bimestre", idBimestreAtual);
        return idBimestreAtual;
    }

    public Long criarNovaSituacaoTurmaMes() {

        Long idBimestre = Long.valueOf(2);

        if (this.ultimoIdBimestre != 0) {
            idBimestre = ultimoIdBimestre + 1;
        }

        SituacaoTurmaMes situacaoTurmaMes1 = new SituacaoTurmaMes(UtilsFunctions.formatoDataPadrao().format(new Date()), "ABERTO",
                preferences.getSavedLong("id_turma"), 0, 0, idBimestre);
        situacaoTurmaMes1.setNovo(true);

        realmObjectsBO.salvarObjetoRealm(situacaoTurmaMes1);
        preferences.saveLong("id_bimestre", idBimestreAtual);
        idBimestreAtual = situacaoTurmaMes1.getBimestre();

        return idBimestreAtual;
    }


    public void verificarStatusAtualDisciplinaAluno() {
        DisciplinaAluno disciplinaAluno = realm.where(DisciplinaAluno.class)
                .equalTo("matricula", preferences.getSavedLong("id_matricula"))
                .equalTo("serieDisciplina", verificarSerieDisciplina().getId())
                .findFirst();

        this.disciplinaAluno = disciplinaAluno;
    }

    public SerieDisciplina verificarSerieDisciplina() {
        SerieDisciplina serieDisciplina = realm.where(SerieDisciplina.class)
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
                    .equalTo("bimestre", verificarBimestreAtual())
                    .equalTo("disciplinaAluno", disciplinaAluno.getId())
                    .findFirst();

            return alunoNotaMes;

        } else {
            return null;
        }
    }
}

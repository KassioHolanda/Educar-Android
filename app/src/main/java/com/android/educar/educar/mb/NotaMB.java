package com.android.educar.educar.mb;

import android.content.Context;
import android.widget.Toast;

import com.android.educar.educar.bo.RealmObjectsBO;
import com.android.educar.educar.model.AlunoNotaMes;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.DisciplinaAluno;
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

public class NotaMB {

    private Realm realm;
    private RealmObjectsBO realmObjectsBO;
    private Context context;
    private Preferences preferences;
    private DisciplinaAluno disciplinaAluno;
    private SerieDisciplina serieDisciplina;
    private long idBimestreAtual;
    private long ultimoIdBimestre;

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
        this.idBimestreAtual = 0;
        this.ultimoIdBimestre = 0;
        configRealm();
    }

    public void salvarAlunoNotaMes(String descricao) {

        verificarStatusAtualDisciplinaAluno();
        verificarSerieDisciplina();

        if (verificarStatusParaAdicionarNota()) {
            AlunoNotaMes alunoNotaMes = new AlunoNotaMes();
            alunoNotaMes.setId(realm.where(AlunoNotaMes.class).findAll().size() + 1);
            alunoNotaMes.setUnidade(preferences.getSavedLong("id_unidade"));
            alunoNotaMes.setDatahora(UtilsFunctions.formatoDataPadrao().format(new Date()));
            alunoNotaMes.setDisciplina(preferences.getSavedLong("id_disciplina"));
            alunoNotaMes.setMatricula(preferences.getSavedLong("id_matricula"));
            alunoNotaMes.setNota(Float.parseFloat(descricao));
            alunoNotaMes.setUsuario(preferences.getSavedLong("id_usuario"));
            alunoNotaMes.setBimestre(idBimestreAtual);

            alunoNotaMes.setAnoLetivo(3);
            alunoNotaMes.setTipoLancamentoNota("LANCADO_APP");
            alunoNotaMes.setInseridoFechamento(false);
            alunoNotaMes.setDisciplinaAluno(disciplinaAluno.getId());

            alunoNotaMes.setNovo(true);

            realmObjectsBO.salvarObjetoRealm(alunoNotaMes);
        } else {
            Toast.makeText(context, "Nota ja Informada", Toast.LENGTH_LONG).show();
        }
    }

    public boolean verificarStatusParaAdicionarNota() {
//        if (verificarStatusAtualDisciplinaAluno().getStatusAtual().equals("EM_ANDAMENTO")) {
        return true;
//        } else {
//            return false;
//        }
    }

    public long verificarBimestreAtual() {
        RealmResults<SituacaoTurmaMes> situacaoTurmaMes = realm.where(SituacaoTurmaMes.class)
                .equalTo("turma", preferences.getSavedLong("id_turma")).findAll();

        if (situacaoTurmaMes.size() == 0) {
            criarNovaSituacaoTurmaMes();

        } else {

            for (int i = 0; i < situacaoTurmaMes.size(); i++) {
                if (situacaoTurmaMes.get(i).getStatus().equals("ABERTO")) {
                    this.idBimestreAtual = situacaoTurmaMes.get(i).getBimestre();
                } else if (situacaoTurmaMes.get(i).getBimestre() == 5 && situacaoTurmaMes.get(i).getStatus().equals("FECHADO")) {
                    criarNovaSituacaoTurmaMes();
                } else if (situacaoTurmaMes.get(i).getBimestre() < 5 && situacaoTurmaMes.get(i).getStatus().equals("FECHADO")) {
                    this.ultimoIdBimestre = situacaoTurmaMes.get(i).getBimestre();
                    criarNovaSituacaoTurmaMes();
                }
            }
        }

        if (idBimestreAtual == 0)
            criarNovaSituacaoTurmaMes();

        preferences.saveLong("id_bimestre", idBimestreAtual);
        return idBimestreAtual;
    }

    public void criarNovaSituacaoTurmaMes() {

        long idBimestre = 2;

        if (this.ultimoIdBimestre != 0) {
            idBimestre = ultimoIdBimestre + 1;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SituacaoTurmaMes situacaoTurmaMes1 = new SituacaoTurmaMes(simpleDateFormat.format(new Date()), "ABERTO",
                preferences.getSavedLong("id_turma"), 0, 0, idBimestre);
        situacaoTurmaMes1.setNovo(true);

        realmObjectsBO.salvarObjetoRealm(situacaoTurmaMes1);
        idBimestreAtual = situacaoTurmaMes1.getBimestre();
    }


    public DisciplinaAluno verificarStatusAtualDisciplinaAluno() {

        RealmResults<DisciplinaAluno> disciplinaAlunos = realm.where(DisciplinaAluno.class).findAll();

        long id = preferences.getSavedLong("id_matricula");
        long idS = verificarSerieDisciplina().getId();

        DisciplinaAluno disciplinaAluno = realm.where(DisciplinaAluno.class)
                .equalTo("matricula", preferences.getSavedLong("id_matricula"))
                .equalTo("serieDisciplina", verificarSerieDisciplina().getId())
                .findFirst();

        this.disciplinaAluno = disciplinaAluno;
        return disciplinaAluno;

    }

    public SerieDisciplina verificarSerieDisciplina() {
        SerieDisciplina serieDisciplina = realm.where(SerieDisciplina.class)
                .equalTo("serie", preferences.getSavedLong("id_serie"))
                .equalTo("disciplina", preferences.getSavedLong("id_disciplina"))
                .findFirst();

        return serieDisciplina;
    }

}

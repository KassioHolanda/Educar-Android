package com.android.educar.educar.model.modelalterado;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LocalEscola extends RealmObject {

    @PrimaryKey
    private Long id;
    private String descricao;
    private RealmList<Turma> turmas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public RealmList<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(RealmList<Turma> turmas) {
        this.turmas = turmas;
    }
}

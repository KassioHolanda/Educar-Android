package com.android.educar.educar.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Disciplina {

    @SerializedName("pk")
    private long pk;
    @SerializedName("descricao")
    private String desceicao;
    private long codigo;

    public Disciplina() {
    }

    public Disciplina(String desceicao, long codigo) {
        this.desceicao = desceicao;
        this.codigo = codigo;
    }

    public String getDesceicao() {
        return desceicao;
    }

    public void setDesceicao(String desceicao) {
        this.desceicao = desceicao;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
}

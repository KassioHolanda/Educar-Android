package com.android.educar.educar.model.modelalterado;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FechamentoUnidade extends RealmObject {

    @PrimaryKey
    private Long id;
    @SerializedName("dataalteracao")
    private String dataAlteracao;
    @SerializedName("datacadastro")
    private String dataCadastro;
    @SerializedName("anoletivo")
    private Long anoLetivo;
    private Long unidade;
    @SerializedName("usuariocadastro")
    private Long usuarioCadastro;
    private boolean fechado;
    private boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Long getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Long anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Long getUnidade() {
        return unidade;
    }

    public void setUnidade(Long unidade) {
        this.unidade = unidade;
    }

    public Long getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Long usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

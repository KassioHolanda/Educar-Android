package com.android.educar.educar.model;

public class Bimestre {

    private boolean aberto;
    private int anoLetivo;
    private String descricao;
    private long pk;

    public Bimestre(int anoLetivo, String descricao) {
        this.aberto = true;
        this.anoLetivo = anoLetivo;
        this.descricao = descricao;
    }

    public Bimestre() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
}

package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.model.Unidade;

import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO extends SQLiteOpenHelper {
    private Context context;

    public UnidadeDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Unidade> unidades() {
        List<Unidade> listaUnidades = new ArrayList<>();

        String query = "SELECT * FROM tb_unidade";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Unidade unidade = new Unidade();
                unidade.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                unidade.setNomeUnidade(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

                listaUnidades.add(unidade);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaUnidades;
    }

    public void atualizarUnidade(Unidade unidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", unidade.getNomeUnidade());

        db.update("tb_unidade", contentValues, "pk = ?", new String[]{String.valueOf(unidade.getPk())});
    }

    public Unidade selecionarUnidade(long unidadePk) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_unidade", new String[]{"pk", "nome"},
                "pk=?", new String[]{String.valueOf(unidadePk)},
                null, null, null, null);

        Unidade unidade = new Unidade();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                unidade.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                unidade.setNomeUnidade(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return unidade;
    }

    public void addUnidade(Unidade unidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nome", unidade.getNomeUnidade());
            db.insert("tb_unidade", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ocorreu um erro, Solicitar Administrador!", Toast.LENGTH_LONG).show();
        }
    }

    public void removeUnidade(long unidadePk) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tb_unidade", "pk = ?", new String[]{String.valueOf(unidadePk)});
        db.close();
    }

}

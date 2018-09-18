package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Turma;

import java.util.ArrayList;
import java.util.List;

public class TurmaDAO extends SQLiteOpenHelper {
    private Context context;

    public TurmaDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Turma> turmas() {
        List<Turma> turmas = new ArrayList<>();
        String query = "SELECT * FROM tb_turma";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Turma turma = new Turma();
                turma.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                turma.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                turma.setTurno(cursor.getString(cursor.getColumnIndexOrThrow("turno")));
                turma.setUnidade(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("unidade_id"))));

                turmas.add(turma);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return turmas;
    }

    public Turma selecionarTurma(long turma) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_turma", new String[]{"pk", "descricao", "turno", "unidade_id"},
                "pk=?", new String[]{String.valueOf(turma)},
                null, null, null, null);

        Turma turma1 = new Turma();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                turma1.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                turma1.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                turma1.setTurno(cursor.getString(cursor.getColumnIndexOrThrow("turno")));
                turma1.setUnidade(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("unidade_id"))));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return turma1;
    }

    public void addTurma(Turma turma) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("descricao", turma.getDescricao());
            contentValues.put("turno", turma.getTurno());
            contentValues.put("unidade_id", turma.getUnidade());

            db.insert("tb_turma", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {
        }
    }
}

package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO extends SQLiteOpenHelper {
    private Context context;

    public DisciplinaDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Disciplina> disciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String query = "SELECT * FROM tb_disciplina";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                disciplina.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                disciplina.setProfessor(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("professor_id"))));

                disciplinas.add(disciplina);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return disciplinas;
    }

    public void addDisiciplina(Disciplina disciplina) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nome", disciplina.getNome());
            contentValues.put("professor_id", disciplina.getProfessor());
            db.insert("tb_disciplina", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {

        }
    }

    public Disciplina selecionarDiscipina(long disciplina) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_disciplina", new String[]{"pk", "nome", "professor_id"},
                "pk = ?", new String[]{String.valueOf(disciplina)},
                null, null, null, null);

        Disciplina disciplina1 = new Disciplina();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {

                disciplina1.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                disciplina1.setProfessor(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("professor_id"))));
                disciplina1.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return disciplina1;
    }

    public List<Disciplina> selecionarDisciplinasProfessor(long professor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_disciplina", new String[]{"pk", "nome", "professor_id"},
                "professor_id = ?", new String[]{String.valueOf(professor)},
                null, null, null, null);

        List<Disciplina> disciplinas = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setPk(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                disciplina.setNome(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("nome"))));
                disciplina.setProfessor(cursor.getLong(Integer.valueOf(cursor.getColumnIndexOrThrow("professor_id"))));

                disciplinas.add(disciplina);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return disciplinas;
    }
}

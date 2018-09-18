package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper {


    public AlunoDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Aluno selecionarAluno(long aluno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_aluno", new String[]{"pk", "nome", "quantidade_falta"},
                "pk=?", new String[]{String.valueOf(aluno)},
                null, null, null, null);

        Aluno aluno1 = new Aluno();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {

                aluno1.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                aluno1.setNomeAluno(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                aluno1.setQuantidadeFalta(cursor.getInt(cursor.getColumnIndexOrThrow("quantidade_falta")));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return aluno1;
    }

    public List<Aluno> alunos() {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT * FROM tb_aluno";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Aluno aluno = new Aluno();
                aluno.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                aluno.setNomeAluno(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                aluno.setQuantidadeFalta(cursor.getInt(cursor.getColumnIndexOrThrow("quantidade_falta")));

                alunos.add(aluno);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return alunos;
    }

    public void addAluno(Aluno aluno) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nome", aluno.getNomeAluno());
            contentValues.put("quantidade_falta", aluno.getQuantidadeFalta());
            db.insert("tb_aluno", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {

        }
    }

}

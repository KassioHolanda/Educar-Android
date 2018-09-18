package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO extends SQLiteOpenHelper {
    private Context context;

    public ProfessorDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addProfessor(Professor professor) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cpf", professor.getCpf());
            contentValues.put("nome", professor.getNome());
            contentValues.put("senha", professor.getSenha());
            db.insert("tb_professor", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {

        }
    }

    public void removeProfessor(Professor professor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tb_professor", "pk = ?", new String[]{String.valueOf(professor.getPk())});
        db.close();
    }

    public Professor selecionarProfessor(long professor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_professor", new String[]{"pk", "cpf", "nome", "senha"},
                "pk=?", new String[]{String.valueOf(professor)},
                null, null, null, null);

        Professor professor1 = new Professor();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                professor1.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                professor1.setCpf(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("cpf"))));
                professor1.setNome(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("nome"))));
                professor1.setSenha(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("senha"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return professor1;
    }


    public void atualizarProfessor(Professor professor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cpf", professor.getCpf());
        contentValues.put("nome", professor.getNome());
        contentValues.put("senha", professor.getSenha());
        Log.i("SENHAPROFESSOR", "Senha " + professor.getSenha());
        db.update("tb_professor", contentValues, "pk = ?", new String[]{String.valueOf(professor.getPk())});
    }

    public List<Professor> professores() {
        List<Professor> listaProfessores = new ArrayList<>();
        String query = "SELECT * FROM tb_professor";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Professor professor = new Professor();
                professor.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                professor.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                professor.setCpf(cursor.getString(cursor.getColumnIndexOrThrow("cpf")));

                listaProfessores.add(professor);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaProfessores;
    }
}

package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Ocorrencia;

public class OcorrenciaDAO extends SQLiteOpenHelper {

    private Context context;

    public OcorrenciaDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Ocorrencia selecionarOcorrenciaAluno(long aluno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_ocorrencia", new String[]{"pk", "aluno_id", "descricao"},
                "aluno_id = ?", new String[]{String.valueOf(aluno)},
                null, null, null, null);

        Ocorrencia ocorrencia = new Ocorrencia();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                ocorrencia.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                ocorrencia.setDescricao(cursor.getString((cursor.getColumnIndexOrThrow("descricao"))));
                ocorrencia.setAluno(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("ativo"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ocorrencia;
    }

    public Ocorrencia selecionarFrequenciaAluno(long aluno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_frequencia", new String[]{"pk", "aluno_id", "descricao"},
                "aluno_id = ?", new String[]{String.valueOf(aluno)},
                null, null, null, null);

        Ocorrencia ocorrencia = new Ocorrencia();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                ocorrencia.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                ocorrencia.setDescricao(cursor.getString((cursor.getColumnIndexOrThrow("descricao"))));
                ocorrencia.setAluno(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("ativo"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ocorrencia;
    }

    public void adicionarOcorrencia(Ocorrencia ocorrencia) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("aluno_id", ocorrencia.getAluno());
            contentValues.put("descricao", ocorrencia.getDescricao());
            sqLiteDatabase.insert("tb_ocorrencia", null, contentValues);
            Log.i("SALVAR_BD", "Ocorrencia com sucesso!");
            sqLiteDatabase.close();
        } catch (Exception e) {

        }
    }
}

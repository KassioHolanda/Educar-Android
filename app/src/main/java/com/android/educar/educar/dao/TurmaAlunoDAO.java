package com.android.educar.educar.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.TurmaAluno;

import java.util.ArrayList;
import java.util.List;

public class TurmaAlunoDAO extends SQLiteOpenHelper {
    private Context context;
    private AlunoDAO alunoDAO;

    public TurmaAlunoDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
        alunoDAO = new AlunoDAO(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Aluno> selecionarTurmaAluno(long turma) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_turmaaluno", new String[]{"pk", "aluno_id", "turma_id"},
                "turma_id=?", new String[]{String.valueOf(turma)},
                null, null, null, null);

        List<TurmaAluno> list = new ArrayList<>();
        TurmaAluno turmaAluno = new TurmaAluno();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                turmaAluno.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                turmaAluno.setTurma(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("turma_id"))));
                turmaAluno.setAluno(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("aluno_id"))));
                list.add(turmaAluno);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recuperarAlunos(list);
    }

    public List<Aluno> recuperarAlunos(List<TurmaAluno> turmaAlunos) {

        List<Aluno> alunos = new ArrayList<>();
        List<Aluno> alunos1 = new ArrayList<>();
        alunos1 = alunoDAO.alunos();

        for (int j = 0; j < turmaAlunos.size(); j++) {
            Log.i("INFO turma", "" + turmaAlunos.get(j).getAluno());
            for (int i = 0; i < alunos1.size(); i++) {
                Log.i("INFO aluno", "" + alunos1.get(i).getPk());
                if (turmaAlunos.get(j).getAluno() == alunos1.get(i).getPk()) {
                    alunos.add(alunos1.get(j));
                }
            }
        }
        return alunos;
    }
}

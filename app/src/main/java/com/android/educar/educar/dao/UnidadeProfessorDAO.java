package com.android.educar.educar.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.educar.educar.model.Professor;
import com.android.educar.educar.model.UnidadeProfessor;

import java.util.ArrayList;
import java.util.List;

public class UnidadeProfessorDAO  extends SQLiteOpenHelper{

    private Context context;
    private ProfessorDAO professorDAO;

    public UnidadeProfessorDAO(Context context) {
        super(context, ClassDAO.BD_CLIENT, null, ClassDAO.VERSAO_BD);
        this.context = context;
        professorDAO = new ProfessorDAO(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Professor> selecionarUnidadeProfessor(long professor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_unidadeprofessor", new String[]{"pk", "professor_id", "unidade_id"},
                "professor_id=?", new String[]{String.valueOf(professor)},
                null, null, null, null);

        List<UnidadeProfessor> list = new ArrayList<>();
        UnidadeProfessor unidadeProfessor = new UnidadeProfessor();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                unidadeProfessor.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                unidadeProfessor.setProfessor(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("turma_id"))));
                unidadeProfessor.setUnidade(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("aluno_id"))));
                list.add(unidadeProfessor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return recuperarUnidade(list);
    }

    public List<Professor> recuperarUnidade(List<UnidadeProfessor> unidadeProfessors) {

        List<Professor> professors = new ArrayList<>();
        List<Professor> professorsBD = professorDAO.professores();

        for (int j = 0; j < unidadeProfessors.size(); j++) {
            Log.i("INFO turma", "" + unidadeProfessors.get(j).getProfessor());
            for (int i = 0; i < professorsBD.size(); i++) {
                Log.i("INFO aluno", "" + professorsBD.get(i).getPk());
                if (unidadeProfessors.get(j).getProfessor() == professorsBD.get(i).getPk()) {
                    professors.add(professorsBD.get(j));
                }
            }
        }

        return professors;
    }
}

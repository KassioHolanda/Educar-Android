package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.educar.educar.model.Aluno;
import com.android.educar.educar.model.Aula;
import com.android.educar.educar.model.Bimestre;
import com.android.educar.educar.model.Disciplina;
import com.android.educar.educar.model.Frequencia;
import com.android.educar.educar.model.Nota;
import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.model.Professor;
import com.android.educar.educar.model.Turma;
import com.android.educar.educar.model.TurmaAluno;
import com.android.educar.educar.model.Unidade;
import com.android.educar.educar.model.UnidadeProfessor;

import java.util.ArrayList;
import java.util.List;

public class ClassDAO extends SQLiteOpenHelper {

    public static final int VERSAO_BD = 5;
    public static final String BD_CLIENT = "educar_bd";
    private Context context;

    public ClassDAO(Context context) {
        super(context, BD_CLIENT, null, VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_professor (pk INTEGER PRIMARY KEY AUTOINCREMENT, cpf TEXT, senha text,nome TEXT)");
        db.execSQL("CREATE TABLE tb_unidade (pk INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT)");
        db.execSQL("CREATE TABLE tb_turma (pk INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, turno TEXT, unidade_id int not null references tb_unidade)");
        db.execSQL("CREATE TABLE tb_disciplina (pk INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, professor_id int not null references tb_professor)");
        db.execSQL("CREATE TABLE tb_aluno (pk INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, quantidade_falta int)");
        db.execSQL("CREATE TABLE tb_aula (pk INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, turma_id int not null references tb_turma, data timestamp)");
        db.execSQL("CREATE TABLE tb_frequencia (pk INTEGER PRIMARY KEY AUTOINCREMENT, aluno_id int not null references tb_aluno, aula_id int not null references tb_aula, presente boolean not null)");
        db.execSQL("CREATE TABLE tb_bimestre (pk INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, ativo boolean not null, ano int not null)");
        db.execSQL("CREATE TABLE tb_nota (pk INTEGER PRIMARY KEY AUTOINCREMENT, aluno_id int not null references tb_aluno, disciplina_id int not null references tb_disciplina, nota TEXT, bimestre_id int not null references tb_bimestre)");
        db.execSQL("CREATE TABLE tb_turmaaluno (pk INTEGER PRIMARY KEY AUTOINCREMENT, aluno_id int not null references tb_aluno, turma_id int not null references tb_turma)");
        db.execSQL("CREATE TABLE tb_unidadeprofessor (pk INTEGER PRIMARY KEY AUTOINCREMENT, professor_id int not null references tb_professor, unidade_id int not null references tb_unidade)");
        db.execSQL("CREATE TABLE tb_ocorrencia (pk INTEGER PRIMARY KEY AUTOINCREMENT, aluno_id int not null references tb_aluno, descricao TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public List<Professor> selecionarUnidadeProfessor(long professor) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_unidadeprofessor", new String[]{"pk", "professor_id", "unidade_id"},
//                "professor_id=?", new String[]{String.valueOf(professor)},
//                null, null, null, null);
//
//        List<UnidadeProfessor> list = new ArrayList<>();
//        UnidadeProfessor unidadeProfessor = new UnidadeProfessor();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                unidadeProfessor.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                unidadeProfessor.setProfessor(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("turma_id"))));
//                unidadeProfessor.setUnidade(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("aluno_id"))));
//                list.add(unidadeProfessor);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return recuperarUnidade(list);
//    }
//
//    public List<Professor> recuperarUnidade(List<UnidadeProfessor> unidadeProfessors) {
//
//        List<Professor> professors = new ArrayList<>();
//        List<Professor> professorsBD = this.professores();
//
//        for (int j = 0; j < unidadeProfessors.size(); j++) {
//            Log.i("INFO turma", "" + unidadeProfessors.get(j).getProfessor());
//            for (int i = 0; i < professorsBD.size(); i++) {
//                Log.i("INFO aluno", "" + professorsBD.get(i).getPk());
//                if (unidadeProfessors.get(j).getProfessor() == professorsBD.get(i).getPk()) {
//                    professors.add(professorsBD.get(j));
//                }
//            }
//        }
//
//        return professors;
//    }

    public List<Frequencia> frequencias() {
        List<Frequencia> frequencias = new ArrayList<>();
        String query = "SELECT * FROM tb_frequencia";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Frequencia frequencia = new Frequencia();
                frequencia.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                frequencia.setAluno(cursor.getInt(cursor.getColumnIndexOrThrow("aluno_id")));
                frequencia.setAula(cursor.getInt(cursor.getColumnIndexOrThrow("aula_id")));
                frequencia.setPresente(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("aula_id"))));

                frequencias.add(frequencia);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return frequencias;
    }

    public Nota selecionarNotaAluno(long aluno) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("tb_nota", new String[]{"pk", "aluno_id", "disciplina_id", "nota", "bimestre_id"},
                "aluno_id = ?", new String[]{String.valueOf(aluno)},
                null, null, null, null);

//        List<Nota> notas = new ArrayList<>();
        Nota nota = new Nota();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                nota.setNota(Float.valueOf(cursor.getString((cursor.getColumnIndexOrThrow("nota")))));
                nota.setBimestre(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("bimestre_id"))));
                nota.setDisciplina(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("disciplina_id"))));
                nota.setAluno(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("aluno_id"))));
                nota.setBimestre(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("bimestre_id"))));
//                notas.add(nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nota;
    }


//    public List<Aluno> alunos() {
//        List<Aluno> alunos = new ArrayList<>();
//        String query = "SELECT * FROM tb_aluno";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                Aluno aluno = new Aluno();
//                aluno.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                aluno.setNomeAluno(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//                aluno.setQuantidadeFalta(cursor.getInt(cursor.getColumnIndexOrThrow("quantidade_falta")));
//
//                alunos.add(aluno);
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return alunos;
//    }

//    public List<Disciplina> disciplinas() {
//        List<Disciplina> disciplinas = new ArrayList<>();
//        String query = "SELECT * FROM tb_disciplina";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                Disciplina disciplina = new Disciplina();
//                disciplina.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                disciplina.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//                disciplina.setProfessor(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("professor_id"))));
//
//                disciplinas.add(disciplina);
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return disciplinas;
//    }

    public List<Aula> aulas() {
        List<Aula> aulas = new ArrayList<>();
        String query = "SELECT * FROM tb_aula";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Aula aula = new Aula();
                aula.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                aula.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                aula.setTurma(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("turma_id"))));

                aulas.add(aula);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return aulas;
    }

    public void addAula(Aula aula) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("descricao", aula.getDescricao());
            contentValues.put("turma_id", aula.getTurma());
            contentValues.put("data", String.valueOf(aula.getData()));
            db.insert("tb_aluno", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {

        }
    }

    public void addFrequencia(Frequencia frequencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("aluno_id", frequencia.getAluno());
            contentValues.put("aula_id", frequencia.getAula());
            contentValues.put("presente", frequencia.isPresente());
            db.insert("tb_aluno", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {

        }
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

//    public void addDisiciplina(Disciplina disciplina) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("nome", disciplina.getNome());
//            contentValues.put("professor_id", disciplina.getProfessor());
//            db.insert("tb_disciplina", null, contentValues);
//            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
//            db.close();
//        } catch (Exception e) {
//
//        }
//    }

//    public void addProfessor(Professor professor) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("cpf", professor.getCpf());
//            contentValues.put("nome", professor.getNome());
//            contentValues.put("senha", professor.getSenha());
//            db.insert("tb_professor", null, contentValues);
//            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
//            db.close();
//        } catch (Exception e) {
//
//        }
//    }

//    public void addTurma(Turma turma) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("descricao", turma.getDescricao());
//            contentValues.put("turno", turma.getTurno());
//            contentValues.put("unidade_id", turma.getUnidade());
//
//            db.insert("tb_turma", null, contentValues);
//            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
//            db.close();
//        } catch (Exception e) {
//        }
//
//    }

//    public void removeProfessor(Professor professor) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("tb_professor", "pk = ?", new String[]{String.valueOf(professor.getPk())});
//        db.close();
//    }

    public void removeTurmaAluno() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from tb_turmaaluno";
//        db.delete("tb_professor", "pk = ?", new String[]{String.valueOf(professor.getPk())});
        db.rawQuery(query, null);
        Log.i("DELETE", "Dados removidos");
        db.close();
    }

//    public List<Disciplina> selecionarDisciplinasProfessor(long professor) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_disciplina", new String[]{"pk", "nome", "professor_id"},
//                "professor_id = ?", new String[]{String.valueOf(professor)},
//                null, null, null, null);
//
//        List<Disciplina> disciplinas = new ArrayList<>();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                Disciplina disciplina = new Disciplina();
//                disciplina.setPk(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                disciplina.setNome(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("nome"))));
//                disciplina.setProfessor(cursor.getLong(Integer.valueOf(cursor.getColumnIndexOrThrow("professor_id"))));
//
//                disciplinas.add(disciplina);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return disciplinas;
//    }


//    public Aluno selecionarAluno(long aluno) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_aluno", new String[]{"pk", "nome", "quantidade_falta"},
//                "pk=?", new String[]{String.valueOf(aluno)},
//                null, null, null, null);
//
//        Aluno aluno1 = new Aluno();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//
//                aluno1.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                aluno1.setNomeAluno(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//                aluno1.setQuantidadeFalta(cursor.getInt(cursor.getColumnIndexOrThrow("quantidade_falta")));
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return aluno1;
//    }

//    public Disciplina selecionarDiscipina(long disciplina) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_disciplina", new String[]{"pk", "nome", "professor_id"},
//                "pk = ?", new String[]{String.valueOf(disciplina)},
//                null, null, null, null);
//
//        Disciplina disciplina1 = new Disciplina();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//
//                disciplina1.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                disciplina1.setProfessor(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("professor_id"))));
//                disciplina1.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return disciplina1;
//    }

    public List<Turma> turmasUnidade(long unidade) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_turma", new String[]{"pk", "descricao", "turno", "unidade_id"},
                "unidade_id = ?", new String[]{String.valueOf(unidade)},
                null, null, null, null);

        List<Turma> turmas = new ArrayList<>();
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


//    public Professor selecionarProfessor(long professor) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_professor", new String[]{"pk", "cpf", "nome", "senha"},
//                "pk=?", new String[]{String.valueOf(professor)},
//                null, null, null, null);
//
//        Professor professor1 = new Professor();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                professor1.setPk(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                professor1.setCpf(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("cpf"))));
//                professor1.setNome(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("nome"))));
//                professor1.setSenha(cursor.getString(Integer.valueOf(cursor.getColumnIndexOrThrow("senha"))));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return professor1;
//    }
//
//
//    public void atualizarProfessor(Professor professor) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("cpf", professor.getCpf());
//        contentValues.put("nome", professor.getNome());
//        contentValues.put("senha", professor.getSenha());
//        Log.i("SENHAPROFESSOR", "Senha " + professor.getSenha());
//        db.update("tb_professor", contentValues, "pk = ?", new String[]{String.valueOf(professor.getPk())});
//    }
//
//    public List<Professor> professores() {
//        List<Professor> listaProfessores = new ArrayList<>();
//        String query = "SELECT * FROM tb_professor";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                Professor professor = new Professor();
//                professor.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                professor.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//                professor.setCpf(cursor.getString(cursor.getColumnIndexOrThrow("cpf")));
//
//                listaProfessores.add(professor);
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return listaProfessores;
//    }


//    public List<Turma> turmas() {
//        List<Turma> turmas = new ArrayList<>();
//        String query = "SELECT * FROM tb_turma";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                Turma turma = new Turma();
//                turma.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                turma.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
//                turma.setTurno(cursor.getString(cursor.getColumnIndexOrThrow("turno")));
//                turma.setUnidade(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("unidade_id"))));
//
//                turmas.add(turma);
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return turmas;
//    }

//    public void addUnidade(Unidade unidade) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("nome", unidade.getNomeUnidade());
//            db.insert("tb_unidade", null, contentValues);
//            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
//            db.close();
//        } catch (Exception e) {
//            Toast.makeText(context, "Ocorreu um erro, Solicitar Administrador!", Toast.LENGTH_LONG).show();
//        }
//    }

    public void addTurmaAluno(TurmaAluno turmaAluno) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("aluno_id", turmaAluno.getAluno());
            contentValues.put("turma_id", turmaAluno.getTurma());
            db.insert("tb_turmaaluno", null, contentValues);
            Log.i("SALVAR_BD", "Dados inseridos com sucesso!");
            db.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ocorreu um erro, Solicitar Administrador!", Toast.LENGTH_LONG).show();
        }
    }

//    public List<Aluno> selecionarTurmaAluno(long turma) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_turmaaluno", new String[]{"pk", "aluno_id", "turma_id"},
//                "turma_id=?", new String[]{String.valueOf(turma)},
//                null, null, null, null);
//
//        List<TurmaAluno> list = new ArrayList<>();
//        TurmaAluno turmaAluno = new TurmaAluno();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                turmaAluno.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                turmaAluno.setTurma(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("turma_id"))));
//                turmaAluno.setAluno(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("aluno_id"))));
//                list.add(turmaAluno);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return recuperarAlunos(list);
//    }

//    public List<Aluno> recuperarAlunos(List<TurmaAluno> turmaAlunos) {
//
//        List<Aluno> alunos = new ArrayList<>();
//        for (int j = 0; j < turmaAlunos.size(); j++) {
//            Log.i("INFO turma", "" + turmaAlunos.get(j).getAluno());
//            for (int i = 0; i < this.alunos().size(); i++) {
//                Log.i("INFO aluno", "" + this.alunos().get(i).getPk());
//                if (turmaAlunos.get(j).getAluno() == this.alunos().get(i).getPk()) {
//                    alunos.add(this.alunos().get(j));
//                }
//            }
//        }
//
//        return alunos;
//    }

//    public void removeUnidade(long unidadePk) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("tb_unidade", "pk = ?", new String[]{String.valueOf(unidadePk)});
//        db.close();
//    }

//    public Unidade selecionarUnidade(long unidadePk) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_unidade", new String[]{"pk", "nome"},
//                "pk=?", new String[]{String.valueOf(unidadePk)},
//                null, null, null, null);
//
//        Unidade unidade = new Unidade();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                unidade.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                unidade.setNomeUnidade(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return unidade;
//    }

//    public Turma selecionarTurma(long turma) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("tb_turma", new String[]{"pk", "descricao", "turno", "unidade_id"},
//                "pk=?", new String[]{String.valueOf(turma)},
//                null, null, null, null);
//
//        Turma turma1 = new Turma();
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                turma1.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                turma1.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
//                turma1.setTurno(cursor.getString(cursor.getColumnIndexOrThrow("turno")));
//                turma1.setUnidade(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("unidade_id"))));
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return turma1;
//    }

//    public void atualizarUnidade(Unidade unidade) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("nome", unidade.getNomeUnidade());
//
//        db.update("tb_unidade", contentValues, "pk = ?", new String[]{String.valueOf(unidade.getPk())});
//    }

//    public List<Unidade> unidades() {
//        List<Unidade> listaUnidades = new ArrayList<>();
//
//        String query = "SELECT * FROM tb_unidade";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        if (cursor.isFirst()) {
//            do {
//                Unidade unidade = new Unidade();
//                unidade.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
//                unidade.setNomeUnidade(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
//
//                listaUnidades.add(unidade);
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return listaUnidades;
//    }

    public List<Nota> notas() {
        List<Nota> notas = new ArrayList<>();
        String query = "SELECT * FROM tb_nota;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Nota nota = new Nota();
                nota.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                nota.setNota(Float.valueOf(cursor.getString((cursor.getColumnIndexOrThrow("nota")))));
                nota.setBimestre(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("bimestre_id"))));
                nota.setDisciplina(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("disciplina_id"))));
                nota.setAluno(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("aluno_id"))));
                nota.setBimestre(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("bimestre_id"))));
                notas.add(nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notas;
    }


    public void adicionarNota(Nota nota) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("aluno_id", nota.getAluno());
            contentValues.put("disciplina_id", nota.getDisciplina());
            contentValues.put("nota", nota.getNota());
            contentValues.put("bimestre_id", nota.getBimestre());
            sqLiteDatabase.insert("tb_nota", null, contentValues);
            Log.i("SALVAR_BD", "Notas inseridas com sucesso!");
            sqLiteDatabase.close();
        } catch (Exception e) {

        }
    }

    public void adicionarBimestre(Bimestre bimestre) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("descricao", bimestre.getDescricao());
            contentValues.put("ativo", bimestre.isAberto());
            contentValues.put("ano", bimestre.getAnoLetivo());
            contentValues.put("descricao", bimestre.getDescricao());
            sqLiteDatabase.insert("tb_bimestre", null, contentValues);
            Log.i("SALVAR_BD", "Bimestre salvo com sucesso!");
            sqLiteDatabase.close();
        } catch (Exception e) {

        }
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

    public void atualizarBimestre(Bimestre bimestre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", bimestre.getDescricao());
        contentValues.put("ativo", bimestre.isAberto());
        contentValues.put("ano", bimestre.getAnoLetivo());
        contentValues.put("descricao", bimestre.getDescricao());
        db.update("tb_bimestre", contentValues, "pk = ?", new String[]{String.valueOf(bimestre.getPk())});
    }

    public List<Bimestre> bimestres() {
        List<Bimestre> bimestres = new ArrayList<>();
        String query = "SELECT * FROM tb_bimestre;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {
                Bimestre bimestre = new Bimestre();
                bimestre.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                bimestre.setDescricao(cursor.getString((cursor.getColumnIndexOrThrow("descricao"))));
                bimestre.setAnoLetivo(Integer.valueOf(cursor.getString((cursor.getColumnIndexOrThrow("ano")))));
                bimestre.setAberto(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("ativo"))));
                bimestres.add(bimestre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bimestres;
    }

    public Bimestre selecionarBimetre(long bismestre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_bimestre", new String[]{"pk", "descricao", "ativo", "ano"},
                "pk = ?", new String[]{String.valueOf(bismestre)},
                null, null, null, null);

        Bimestre bimestre = new Bimestre();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {

                bimestre.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                bimestre.setDescricao(cursor.getString((cursor.getColumnIndexOrThrow("descricao"))));
                bimestre.setAnoLetivo(Integer.valueOf(cursor.getString((cursor.getColumnIndexOrThrow("ano")))));
                bimestre.setAberto(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("ativo"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bimestre;
    }

    public Bimestre selecionarBimetrePorNome(String bismestre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tb_bimestre", new String[]{"pk", "descricao", "ativo", "ano"},
                "descricao = ?", new String[]{String.valueOf(bismestre)},
                null, null, null, null);

        Bimestre bimestre = new Bimestre();
        cursor.moveToFirst();
        if (cursor.isFirst()) {
            do {

                bimestre.setPk(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("pk"))));
                bimestre.setDescricao(cursor.getString((cursor.getColumnIndexOrThrow("descricao"))));
                bimestre.setAnoLetivo(Integer.valueOf(cursor.getString((cursor.getColumnIndexOrThrow("ano")))));
                bimestre.setAberto(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("ativo"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bimestre;
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
}

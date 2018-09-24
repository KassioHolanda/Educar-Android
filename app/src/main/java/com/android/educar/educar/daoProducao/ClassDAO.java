package com.android.educar.educar.daoProducao;

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

    public static final int VERSAO_BD = 1;
    public static final String BD_CLIENT = "educar_producao_bd";
    private Context context;

    public ClassDAO(Context context) {
        super(context, BD_CLIENT, null, VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pessoafisica (id INTEGER PRIMARY KEY AUTOINCREMENT, cpf text,datanascimento timestamp, nome text, rg text, nacionalidade text, sexo text, email text, senha text)");
        db.execSQL("CREATE TABLE funcionario (id INTEGER PRIMARY KEY AUTOINCREMENT, pessoafisica_id int not null references pessoafisica(id), cargo_id int not null references cargo(id), cargahoraria text, situacaofuncional text, dataadmissao timestamp, statusfuncionario text)");
        db.execSQL("CREATE TABLE cargo (id INTEGER PRIMARY KEY AUTOINCREMENT, abreviacao text, descricao text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

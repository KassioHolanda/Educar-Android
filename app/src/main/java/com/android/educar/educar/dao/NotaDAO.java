package com.android.educar.educar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.educar.educar.model.Nota;

import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public ClassDAO classDAO;
    private Context context;

    public NotaDAO(Context context) {
        classDAO = new ClassDAO(context);
    }


}

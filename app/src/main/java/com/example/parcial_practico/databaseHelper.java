package com.example.parcial_practico;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databaseHelper extends SQLiteOpenHelper {

    public static final String nombre_tabla1 = "asignatura";
    public static final String nombre_tabla2 = "nota";

    public String consulta = "CREATE TABLE "+nombre_tabla1+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CODIGO TEXT,NOMBRE TEXT)";
    public String consulta2 = "CREATE TABLE "+nombre_tabla2+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ASIGNATURA TEXT,CORTE1 DOUBLE, CORTE2 DOUBLE,CORTE3 DOUBLE, NOTA DOUBLE)";

    public databaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(consulta);
        sqLiteDatabase.execSQL(consulta2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  "+nombre_tabla1);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  "+nombre_tabla2);
        onCreate(sqLiteDatabase);
    }
}

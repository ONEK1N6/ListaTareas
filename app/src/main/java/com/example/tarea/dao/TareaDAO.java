package com.example.tarea.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tarea.config.DatabaseHelper;
import com.example.tarea.model.Tarea;

import java.util.ArrayList;
import java.util.List;

public class TareaDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public TareaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertTarea(Tarea tarea) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITULO, tarea.getTitulo());
        values.put(DatabaseHelper.COLUMN_DESCRIPCION, tarea.getDescripcion());
        values.put(DatabaseHelper.COLUMN_ESTADO, tarea.isEstado() ? 1 : 0);

        return database.insert(DatabaseHelper.TABLE_TAREAS, null, values);
    }

    @SuppressLint("Range")
    public List<Tarea> getAllTareas() {
        List<Tarea> tareas = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_TAREAS,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tarea tarea = new Tarea();
                tarea.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                tarea.setTitulo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITULO)));
                tarea.setDescripcion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPCION)));
                tarea.setEstado(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ESTADO)) == 1);
                tareas.add(tarea);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tareas;
    }

    public int updateTarea(Tarea tarea) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITULO, tarea.getTitulo());
        values.put(DatabaseHelper.COLUMN_DESCRIPCION, tarea.getDescripcion());
        values.put(DatabaseHelper.COLUMN_ESTADO, tarea.isEstado() ? 1 : 0);

        return database.update(DatabaseHelper.TABLE_TAREAS, values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(tarea.getId())});
    }

    public void deleteTarea(int id) {
        database.delete(DatabaseHelper.TABLE_TAREAS,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
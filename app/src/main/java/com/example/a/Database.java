package com.example.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "WORK.DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE WORK(ID INTEGER PRIMARY KEY, TITLE TEXT, DATE TEXT, CONTENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS WORK");
        onCreate(db);

    }

    public void add(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", work.getTitle());
        contentValues.put("DATE", work.getDate());
        contentValues.put("CONTENT", work.getContent());
        db.insert("WORK", null, contentValues);
        db.close();
    }

    public ArrayList<Work> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WORK", null);
        ArrayList<Work> worklist = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String content = cursor.getString(3);
                Work work = new Work();
                work.setId(id);
                work.setTitle(title);
                work.setDate(date);
                work.setContent(content);
                worklist.add(work);
            } while (cursor.moveToNext());

        }
        db.close();
        return worklist;
    }
    public void update(Work work){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", work.getTitle());
        contentValues.put("DATE", work.getDate());
        contentValues.put("CONTENT", work.getContent());
        db.update("WORK", contentValues, "ID = " + work.getId(), null);
    }
    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("WORK", " ID = " + id, null);
        db.close();
    }
}

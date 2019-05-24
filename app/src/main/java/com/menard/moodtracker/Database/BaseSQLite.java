package com.menard.moodtracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class BaseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_MOODFORTHEDAY = "table_moods";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_COLOR = "Color";
    private static final String COLUMN_COMMENT = "Comment";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_MOODFORTHEDAY +
            " (" + COLUMN_DATE + " TEXT PRIMARY KEY NOT NULL, "
            + COLUMN_COLOR + " INTEGER NOT NULL,"
            + COLUMN_COMMENT + " TEXT );";


    BaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //-- Create Table --
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

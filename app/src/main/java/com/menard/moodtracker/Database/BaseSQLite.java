package com.menard.moodtracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.menard.moodtracker.model.MoodForTheDay;

import java.util.ArrayList;
import java.util.List;


public class BaseSQLite extends SQLiteOpenHelper {

    private static final String NAME_DATABASE = "moods.db";
    private static final int VERSION_DATABASE = 1;

    private static final String TABLE_MOODFORTHEDAY = "table_moods";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_COLOR = "Color";
    private static final String COLUMN_COMMENT = "Comment";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_MOODFORTHEDAY +
            " (" + COLUMN_DATE + " TEXT PRIMARY KEY NOT NULL, "
            + COLUMN_COLOR + " INTEGER NOT NULL,"
            + COLUMN_COMMENT + " TEXT );";


    public BaseSQLite(@Nullable Context context) {
        //super(context, name, factory, version);
        super(context, NAME_DATABASE, null, VERSION_DATABASE);
        }


    //-- Create Table --
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Open the database
     */
    private SQLiteDatabase open() {
        return this.getWritableDatabase();
        // faire un retour de la base
    }

    /**
     * Close the database
     */
    public void close() {
        this.close();
    }

    /**
     * Add an object MoodForTheDay to database
     * @param moodForTheDay the object
     */
    public void addMoodDay(MoodForTheDay moodForTheDay) {
        //-- create a ContentValues  ->work like a HashMap
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, moodForTheDay.getDate());
        values.put(COLUMN_COLOR, moodForTheDay.getColor());
        //values.put(COL_COMMENT, MoodForTheDay.getComment());

        open().insert(TABLE_MOODFORTHEDAY, null, values);
        //open().insertWithOnConflict(TABLE_MOODFORTHEDAY, null, values, null);

    }

    /**
     * Update an object
     *
     * @param moodForTheDay the object
     */
    public void updateMoodDay(String date, MoodForTheDay moodForTheDay) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR, moodForTheDay.getColor());
        //values.put(COL_COMMENT, MoodForTheDay.getComment());

        open().update(TABLE_MOODFORTHEDAY, values, COLUMN_DATE + "= \"" + date + "\"", null);
    }


    /**
     * Get an object by his date
     * @param date Date
     * @return object MoodForTheDay
     */
    @NonNull
    public MoodForTheDay getMoodDay(String date) {
        MoodForTheDay moodForTheDay = new MoodForTheDay();
        //mDatabase = mBaseSQLite.getReadableDatabase();

        Cursor cursor = open().rawQuery("SELECT * FROM " + TABLE_MOODFORTHEDAY + " WHERE " +
                COLUMN_DATE + "= \"" + date + "\"", null);
        if (cursor.moveToFirst()) {
            moodForTheDay.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            moodForTheDay.setColor(cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR)));
            moodForTheDay.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)));
            cursor.close();
        }
        return moodForTheDay;
    }

    /**
     * Return an ArrayList of all the MoodForDay sorted by the date
     * @return ArrayList
     */
    @NonNull
    public List<MoodForTheDay> getAllMoodDay() {
        List<MoodForTheDay> mList = new ArrayList<>();
        //mDatabase = mBaseSQLite.getReadableDatabase();

        //-- Select all the object --
        String selectQuery = "SELECT * FROM " + TABLE_MOODFORTHEDAY + " ORDER BY " + COLUMN_DATE + " ASC ";
        Cursor cursor = open().rawQuery(selectQuery, null);

        //-- Adding them to list --
        if (cursor.moveToFirst()) {
            do {
                MoodForTheDay moodForTheDay = new MoodForTheDay();
                moodForTheDay.setDate(cursor.getString(0)); //curso.getColumnIndex
                moodForTheDay.setColor(Integer.parseInt(cursor.getString(1)));
                moodForTheDay.setComment(cursor.getString(2));

                mList.add(moodForTheDay);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public void addComment(String comment, String date) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT, comment);

        open().update(TABLE_MOODFORTHEDAY, values, COLUMN_DATE + "= \"" + date + "\"", null);
    }



}

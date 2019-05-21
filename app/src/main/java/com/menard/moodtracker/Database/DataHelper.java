package com.menard.moodtracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menard.moodtracker.model.MoodForTheDay;

import java.util.ArrayList;
import java.util.List;


public class DataHelper {

    private static final String NAME_DATABASE = "moods.db";
    private static final int VERSION_DATABASE = 1;

    private static final String TABLE_MOODFORTHEDAY = "table_moods";
    /**
     * Key
     */
    private static final String COL_DATE = "Date";
    private static final String COL_COLOR = "Color";
    private static final String COL_COMMENT = "Comment";

    private SQLiteDatabase mDatabase;
    private BaseSQLite mBaseSQLite;

    public DataHelper(Context context) {
        mBaseSQLite = new BaseSQLite(context, NAME_DATABASE, null, VERSION_DATABASE);
    }

    /**
     * Open the database
     */
    public void open() {
        mDatabase = mBaseSQLite.getWritableDatabase();
    }

    /**
     * Close the database
     */
    public void close() {
        mDatabase.close();
    }

    /**
     * Add an object MoodForTheDay to database
     *
     * @param moodForTheDay the object
     * @return insert
     */
    public long addMoodDay(MoodForTheDay moodForTheDay) {
        //-- create a ContentValues  ->work like a HashMap
        ContentValues values = new ContentValues();
        values.put(COL_DATE, moodForTheDay.getDate());
        values.put(COL_COLOR, moodForTheDay.getColor());
        //values.put(COL_COMMENT, MoodForTheDay.getComment());

        open();
        return mDatabase.insert(TABLE_MOODFORTHEDAY, null, values);
    }

    /**
     * Update an object
     *
     * @param moodForTheDay the object
     * @return database updated
     */
    public int updateMoodDay(String date, MoodForTheDay moodForTheDay) {
        ContentValues values = new ContentValues();
        values.put(COL_COLOR, moodForTheDay.getColor());
        //values.put(COL_COMMENT, MoodForTheDay.getComment());

        return mDatabase.update(TABLE_MOODFORTHEDAY, values, COL_DATE + "= \"" + date + "\"", null);
    }

    /**
     * Get an object by his date
     *
     * @param date Date
     * @return object MoodForTheDay
     */
    public MoodForTheDay getMoodDay(String date) {
        MoodForTheDay moodForTheDay = new MoodForTheDay();
        mDatabase = mBaseSQLite.getReadableDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_MOODFORTHEDAY + " WHERE " +
                COL_DATE + "= " + date, null);
        if (cursor.moveToFirst()) {
            moodForTheDay.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
            moodForTheDay.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLOR)));
            moodForTheDay.setComment(cursor.getString(cursor.getColumnIndex(COL_COMMENT)));
            cursor.close();
        }
        return moodForTheDay;
    }

    /**
     * Return an ArrayList of all the MoodForDay sorted by the date
     *
     * @return ArrayList
     */
    public List<MoodForTheDay> getAllMoodDay() {
        List<MoodForTheDay> mList = new ArrayList<>();
        mDatabase = mBaseSQLite.getReadableDatabase();

        //-- Select all the object --
        String selectQuery = "SELECT * FROM " + TABLE_MOODFORTHEDAY + " ORDER BY " + COL_DATE + " ASC ";
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        //-- Adding them to list --
        if (cursor.moveToFirst()) {
            do {
                MoodForTheDay moodForTheDay = new MoodForTheDay();
                moodForTheDay.setDate(cursor.getString(1));
                moodForTheDay.setColor(Integer.parseInt(cursor.getString(2)));
                moodForTheDay.setComment(cursor.getString(3));

                mList.add(moodForTheDay);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public long addComment(String comment, String date) {
        ContentValues values = new ContentValues();
        values.put(COL_COMMENT, comment);

        return mDatabase.update(TABLE_MOODFORTHEDAY, values, COL_DATE + "= \"" + date + "\"", null);
    }

}





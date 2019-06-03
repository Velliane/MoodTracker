package com.menard.moodtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.menard.moodtracker.R;
import com.menard.moodtracker.model.Mood;
import com.menard.moodtracker.model.MoodForTheDay;
import com.menard.moodtracker.view.VerticalViewPager;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.List;


public class BaseSQLite extends SQLiteOpenHelper {

    private static final String NAME_DATABASE = "moods.db";
    private static final int VERSION_DATABASE = 1;

    private static final String TABLE_MOODFORTHEDAY = "table_moods";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_COLOR = "Color";
    private static final String COLUMN_COMMENT = "Comment";
    private static final String COLUMN_PAGE = "Page";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_MOODFORTHEDAY +
            " (" + COLUMN_DATE + " DATE PRIMARY KEY NOT NULL, "
            + COLUMN_COLOR + " INTEGER NOT NULL, "
            + COLUMN_COMMENT + " TEXT, "
            + COLUMN_PAGE + " INTEGER);";


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
    }

    /**
     * Close the database
     */
    public void close() {
        this.close();
    }


    /**
     * Add an object MoodForTheDay to database and update it if already exist
     * @param moodForTheDay the object
     */
    public void addMoodDay(MoodForTheDay moodForTheDay, int position) {
        //-- create a ContentValues  ->work like a HashMap
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, moodForTheDay.getDate());
        values.put(COLUMN_COLOR, Mood.values()[position].getColorRes());
        values.put(COLUMN_COMMENT, moodForTheDay.getComment());
        values.put(COLUMN_PAGE, position);

        open().insertWithOnConflict(TABLE_MOODFORTHEDAY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * Save the current page
     * @param date the date
     * @param position the page
     */
    public void addPage(String date, int position){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAGE, position);

        open().update(TABLE_MOODFORTHEDAY, values, COLUMN_DATE + "= \"" + date + "\"", null);
    }

    /**
     * Get the last page saved
     * @param date the date
     * @return the page
     */
    public int getPage(String date){
        int position = 2;

        Cursor cursor = open().rawQuery("SELECT * FROM " + TABLE_MOODFORTHEDAY + " WHERE " +
                COLUMN_DATE + "= \"" + date + "\"", null);
        if (cursor.moveToFirst()) {
            position = cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE));
            cursor.close();
        }
        return position;
    }

    /**
     * Add comment in the database according to the date
     * @param comment the comment
     * @param date the date
     */
    public void addComment(String comment, String date) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT, comment);

        open().update(TABLE_MOODFORTHEDAY, values, COLUMN_DATE + "= \"" + date + "\"", null);
    }

    /**
     * Save the color of the Mood
     * @param position the page selected
     * @param date the date
     */
    public void addColor (int position, String date) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR, Mood.values()[position].getColorRes());

        open().update(TABLE_MOODFORTHEDAY, values, COLUMN_DATE+ "= \"" + date + "\"", null);
    }

    /**
     * Get the comment saved for the MoodForTheDay
     * @param date the date
     * @return the comment
     */
    public String getComment(String date){
        String comment = null;
        Cursor cursor = open().rawQuery("SELECT * FROM " + TABLE_MOODFORTHEDAY + " WHERE " +
                COLUMN_DATE + "= \"" + date + "\"", null);
        if (cursor.moveToFirst()) {
            comment = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT));
            cursor.close();
        }
        return comment;
    }

    /**
     * Get an object by his date else create new one
     * @param date Date
     * @return object MoodForTheDay
     */
    @NonNull
    public MoodForTheDay getMoodDay(String date) {
        MoodForTheDay moodForTheDay = new MoodForTheDay();

        Cursor cursor = open().rawQuery("SELECT * FROM " + TABLE_MOODFORTHEDAY + " WHERE " +
                COLUMN_DATE + "= \"" + date + "\"", null);
        if (cursor.moveToFirst()) {
            moodForTheDay.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            moodForTheDay.setColor(cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR)));
            moodForTheDay.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)));
            cursor.close();
        }else {
            moodForTheDay = new MoodForTheDay();
            moodForTheDay.setDate(date);
            moodForTheDay.setColor(Mood.valueOf("HAPPY").getColorRes());
            moodForTheDay.setComment(null);
        }
        return moodForTheDay;
    }

    /**
     * Return an ArrayList of all the MoodForDay sorted by the date from yesterday to a week ago
     * @return ArrayList
     */
    @NonNull
    public List<MoodForTheDay> getAllMoodDay() {
        List<MoodForTheDay> mList = new ArrayList<>();

        //-- Select all the object --

        String selectQuery = "SELECT * FROM " + TABLE_MOODFORTHEDAY
                + " WHERE " +COLUMN_DATE +" NOT LIKE " + "\"" + LocalDate.now(ZoneId.systemDefault()).toString() + "\""
                + " ORDER BY " + COLUMN_DATE + " DESC "
                + " LIMIT " + 7;
        Cursor cursor = open().rawQuery(selectQuery, null);

        //-- Adding them to list --
        while (cursor.moveToNext()) {
                MoodForTheDay moodForTheDay = new MoodForTheDay();
                moodForTheDay.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                moodForTheDay.setColor(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR))));
                moodForTheDay.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT)));
                moodForTheDay.setPage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PAGE))));
                mList.add(moodForTheDay);
        }
        cursor.close();
        return mList;
    }

}

package com.menard.moodtracker;

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
    /** Key */
    private static final String COL_ID = "ID";
    private static final String COL_DATE = "Date";
    private static final String COL_COLOR = "Color";
    private static final String COL_COMMENT = "Comment";

    private SQLiteDatabase mDatabase;
    private BaseSQLite mBaseSQLite;

    public DataHelper (Context context){
        mBaseSQLite = new BaseSQLite(context, NAME_DATABASE, null, VERSION_DATABASE );
    }

    /**
     * Open the database
     */
    public void open(){
        mDatabase = mBaseSQLite.getWritableDatabase();
    }

    /**
     * Close the database
     */
    public void close(){
        mDatabase.close();
    }

    /**
     * Add an object MoodForTheDay to database
     * @param moodForTheDay the object
     * @return insert
     */
    public long addMoodDay(MoodForTheDay moodForTheDay){
        //-- create a ContentValues  ->work like a HashMap
        ContentValues values = new ContentValues();
        values.put(COL_ID, moodForTheDay.getId());
        values.put(COL_DATE, moodForTheDay.getDate());
        values.put(COL_COLOR, MoodForTheDay.getColor());
        values.put(COL_COMMENT, MoodForTheDay.getComment());

        return mDatabase.insert(TABLE_MOODFORTHEDAY, null, values);
    }

    /**
     * Update an object
     * @param id the id
     * @param moodForTheDay the object
     * @return database updated
     */
    public int updateMoodDay (int id, MoodForTheDay moodForTheDay){
        ContentValues values = new ContentValues();
        values.put(COL_DATE, moodForTheDay.getDate());
        values.put(COL_COLOR, MoodForTheDay.getColor());
        values.put(COL_COMMENT, MoodForTheDay.getComment());

        return mDatabase.update(TABLE_MOODFORTHEDAY, values, COL_ID + " = "+id, null);
    }

    /**
     * Get an object by his id
     * @param id id
     * @return object MoodForTheDay
     */
    public MoodForTheDay getMoodDay(int id){
        MoodForTheDay moodForTheDay = new MoodForTheDay();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM "+ TABLE_MOODFORTHEDAY + "WHERE "+
                COL_ID+ "=" + id, null);
        if (cursor.moveToFirst()){
            moodForTheDay.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            moodForTheDay.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
            moodForTheDay.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLOR)));
            moodForTheDay.setComment(cursor.getString(cursor.getColumnIndex(COL_COMMENT)));
            cursor.close();
        }
        return moodForTheDay;
    }

    /**
     * Return an ArrayList of all the MoodForDay
     * @return ArrayList
     */
    public List<MoodForTheDay> getAllMoods(){
        List<MoodForTheDay> mList = new ArrayList<>();

        //-- Select all the object --
        String selectQuery = "SELECT * FROM "+ TABLE_MOODFORTHEDAY;
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        //-- Adding them to list --
        if(cursor.moveToFirst()){
            do {
                MoodForTheDay moodForTheDay = new MoodForTheDay();
                moodForTheDay.setId(Integer.parseInt(cursor.getString(0)));
                moodForTheDay.setDate(cursor.getString(1));
                moodForTheDay.setColor(Integer.parseInt(cursor.getString(2)));
                moodForTheDay.setComment(cursor.getString(3));

                mList.add(moodForTheDay);
            }while (cursor.moveToNext());
        }

        return mList;
    }











    //-- WITH REALM --

    ///**
     //* Add the object MoodForTheDay to the realm database
     //* @param date
     //* @param color
     //* @param comment
     //*/
    //public void addMoodForTheDay(String date, int color, String comment){
        //mRealm.beginTransaction();
        //MoodForTheDay moodForTheDay = mRealm.createObject(MoodForTheDay.class, getNextDayKey());
        //moodForTheDay.setDate(date);
        //moodForTheDay.setColor(color);
        //moodForTheDay.setComment(comment);
        //mRealm.commitTransaction();
    //}

    //public int getNextDayKey() {
        //if (mRealm.where(MoodForTheDay.class).count() > 0)
            //return mRealm.where(MoodForTheDay.class).max("id").intValue() + 1;
        //else
            //return 0;
    //}

    ///**
     //* Adding comment to Realm database
     //* @param comment the comment to add
     //*/
    //public static void saveComment(String comment) {
        //Realm realm = Realm.getDefaultInstance();
        //realm.beginTransaction();

        //MoodForTheDay day = new MoodForTheDay();
        //day.setComment(comment);

        //try {
            //realm.copyToRealmOrUpdate(day);
            //realm.commitTransaction();
        //} catch (Exception e) {
            //e.printStackTrace();
        //} finally {
            //System.out.println("OK");
            //realm.close();
        //}
    //}

    ///**
     //* Loading comment from Realm database
     //* @return comment
     //*/
    //public static String loadComment() {
        //Realm realm = Realm.getDefaultInstance();
        //RealmResults<MoodForTheDay> day = realm.where(MoodForTheDay.class).findAll();
        //return MoodForTheDay.getComment();
    //}

    //public static ArrayList<MoodForTheDay> getMoods() {
        //ArrayList<MoodForTheDay> moods = new ArrayList<>();
        //moods.add(new MoodForTheDay(Instant.now().toString(), MoodForTheDay.getColor(), loadComment()));
        //return moods;
    //}

}

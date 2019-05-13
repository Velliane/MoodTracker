package com.menard.moodtracker.model;

import org.threeten.bp.Instant;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

import static com.menard.moodtracker.model.Mood.DISAPPOINTED;
import static com.menard.moodtracker.model.Mood.HAPPY;
import static com.menard.moodtracker.model.Mood.NORMAL;
import static com.menard.moodtracker.model.Mood.SAD;
import static com.menard.moodtracker.model.Mood.VERYHAPPY;

public class Day extends RealmObject {

    private static Mood mMood = NORMAL;

    @PrimaryKey
    private String mDay;
    private static int mColor;
    private static String mComment;

    private Day(String day, int color, String comment) {
        mDay = day;
        mColor = color;
        mComment = comment;

    }

    public Day() {

    }


    public String getDay() {
        mDay = Instant.now().toString();
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public static int getColor() {
        switch (mMood) {
            case DISAPPOINTED:
                mColor = DISAPPOINTED.getColorRes();
            case SAD:
                mColor = SAD.getColorRes();
            case HAPPY:
                mColor = HAPPY.getColorRes();
            case NORMAL:
                mColor = NORMAL.getColorRes();
            case VERYHAPPY:
                mColor = VERYHAPPY.getColorRes();
        }
        return mColor;
    }

        public void setColor ( int color){
            mColor = color;
        }

        private static String getComment () {
            return mComment;
        }

        private void setComment (String comment){
            mComment = comment;
        }

        /**
         * Adding comment to Realm database
         * @param comment the comment to add
         */
        public static void saveComment (String comment){
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            Day day = new Day();
            day.setComment(comment);

            try {
                realm.copyToRealmOrUpdate(day);
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("OK");
                realm.close();
            }
        }

        public static String loadComment () {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Day> day = realm.where(Day.class).findAll();
            String comment = getComment();
            return comment;
        }


        public static ArrayList<Day> getMoods () {

            ArrayList<Day> moods = new ArrayList<>();
            moods.add(new Day(Instant.now().toString(), getColor(), loadComment()));

            return moods;
        }


}

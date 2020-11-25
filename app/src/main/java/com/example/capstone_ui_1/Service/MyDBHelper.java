package com.example.capstone_ui_1.Service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context) {
        super(context, "capstoneDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE class (classId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, className TEXT NOT NULL, " +
                "startDate TEXT NOT NULL,  endDate TEXT NOT NULL,  timesPerDay INTEGER NOT NULL," +
                "mon INTEGER, tue INTEGER, wed INTEGER, thu INTEGER," +
                "fri INTEGER, sat INTEGER, sun INTEGER);");

        db.execSQL("CREATE TABLE time (timeId INTEGER PRIMARY KEY AUTOINCREMENT, oneTime TEXT NOT NULL, " +
                "twoTime TEXT, threeTime TEXT, " +
                "fourTime TEXT, fiveTime TEXT, " +
                "FOREIGN KEY(timeId) REFERENCES class(classId));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<ListItem> allListItems() {
        ArrayList<ListItem> listItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor, cursor2;
        cursor = db.rawQuery("SELECT * FROM class WHERE endDate >= date('now');", null);
        cursor2 = db.rawQuery("SELECT * FROM class JOIN time ON class.classId = time.timeId WHERE endDate >= date('now');", null);

        while (cursor.moveToNext() && cursor2.moveToNext()) {
            ListItem listItem = new ListItem();
            listItem.setClassId(cursor.getInt(0));
            listItem.setClassName(cursor.getString(1));
            listItem.setStartDate(cursor.getString(2));
            listItem.setEndDate(cursor.getString(3));
            listItem.setTimesPerDay(cursor.getInt(4));
            listItem.setMon(cursor.getInt(5));
            listItem.setTue(cursor.getInt(6));
            listItem.setWed(cursor.getInt(7));
            listItem.setThu(cursor.getInt(8));
            listItem.setFri(cursor.getInt(9));
            listItem.setSat(cursor.getInt(10));
            listItem.setSun(cursor.getInt(11));
            listItem.setOneTime(cursor2.getString(13));
            listItem.setTwoTime(cursor2.getString(14));
            listItem.setThreeTime(cursor2.getString(15));
            listItem.setFourTime(cursor2.getString(16));
            listItem.setFiveTime(cursor2.getString(17));
            listItems.add(listItem);
        }

        cursor.close();
        cursor2.close();
        db.close();

        return listItems;
    }

    public ArrayList<LectureList> allLListItems() {
        ArrayList<LectureList> lListItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor, cursor2;
        cursor = db.rawQuery("SELECT * FROM class WHERE endDate >= date('now');", null);
        cursor2 = db.rawQuery("SELECT * FROM class JOIN time ON class.classId = time.timeId  WHERE endDate >= date('now');" , null);

        String now = "";
        Calendar calNow = Calendar.getInstance();
        int numNow = calNow.get(Calendar.DAY_OF_WEEK);
        switch (numNow) {
            case 1:
                now = "일";
                break ;
            case 2:
                now = "월";
                break ;
            case 3:
                now = "화";
                break ;
            case 4:
                now = "수";
                break ;
            case 5:
                now = "목";
                break ;
            case 6:
                now = "금";
                break ;
            case 7:
                now = "토";
                break ;
        }

        while (cursor.moveToNext() && cursor2.moveToNext()) {
            if (now.equals("일") && cursor.getInt(11) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("일:", cursor.getString(1));
            } else if (now.equals("월") && cursor.getInt(5) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("월:", cursor.getString(1));
            } else if (now.equals("화") && cursor.getInt(6) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("화:", cursor.getString(1));
            } else if (now.equals("수") && cursor.getInt(7) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("수:", cursor.getString(1));
            } else if (now.equals("목") && cursor.getInt(8) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("목:", cursor.getString(1));
            } else if (now.equals("금") && cursor.getInt(9) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("금:", cursor.getString(1));
            } else if (now.equals("토") && cursor.getInt(10) == 1) {
                LectureList lListItem = new LectureList();
                lListItem.setClassId(cursor.getInt(0));
                lListItem.setClassName(cursor.getString(1));
                lListItem.setStartDate(cursor.getString(2));
                lListItem.setEndDate(cursor.getString(3));
                lListItem.setTimesPerDay(cursor.getInt(4));
                lListItem.setOneTime(cursor2.getString(13));
                lListItem.setTwoTime(cursor2.getString(14));
                lListItem.setThreeTime(cursor2.getString(15));
                lListItem.setFourTime(cursor2.getString(16));
                lListItem.setFiveTime(cursor2.getString(17));
                lListItems.add(lListItem);
                Log.e("토:", cursor.getString(1));
            }
        }

        cursor.close();
        cursor2.close();
        db.close();

        return lListItems;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM class WHERE classId = '" + id + "';");
        db.execSQL("DELETE FROM time WHERE timeId = '" + id + "';");
        db.close();
    }
}

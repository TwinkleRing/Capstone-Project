package com.example.capstone_ui_1.Service;

public class ListItem {


    int classId;
    String className;
    String startDate;
    String endDate;
    int timesPerDay;
    int mon, tue, wed, thu, fri, sat, sun;
    String oneTime, twoTime, threeTime, fourTime, fiveTime;

    public ListItem() {
    }

    public ListItem(String className, String startDate, String endDate, int timesPerDay) {
        this.className = className;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timesPerDay = timesPerDay;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getTue() {
        return tue;
    }

    public void setTue(int tue) {
        this.tue = tue;
    }

    public int getWed() {
        return wed;
    }

    public void setWed(int wed) {
        this.wed = wed;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public int getFri() {
        return fri;
    }

    public void setFri(int fri) {
        this.fri = fri;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(String twoTime) {
        this.twoTime = twoTime;
    }

    public String getThreeTime() {
        return threeTime;
    }

    public void setThreeTime(String threeTime) {
        this.threeTime = threeTime;
    }

    public String getFourTime() {
        return fourTime;
    }

    public void setFourTime(String fourTime) {
        this.fourTime = fourTime;
    }

    public String getFiveTime() {
        return fiveTime;
    }

    public void setFiveTime(String fiveTime) {
        this.fiveTime = fiveTime;
    }
}

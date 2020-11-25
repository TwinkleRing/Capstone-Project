package com.example.capstone_ui_1.FireDB;

public class junkonglist {
    private int cno;
    private String classname;
    private String grade;
    private String major;
    private String point;
    private String professor;
    private String time;
    private String classroom;

    private long day_1;
    private long day_2;

    private String day1_start_time;
    private String day2_start_time;

    private String day1_end_time;
    private String day2_end_time;

    public junkonglist(){}

    public long getDay_1() {
        return day_1;
    }

    public void setDay_1(int day_1) {
        this.day_1 = day_1;
    }

    public long getDay_2() {
        return day_2;
    }

    public void setDay_2(int day_2) {
        this.day_2 = day_2;
    }

    public String getDay1_start_time() {
        return day1_start_time;
    }

    public void setDay1_start_time(String day1_start_time) {
        this.day1_start_time = day1_start_time;
    }

    public String getDay2_start_time() {
        return day2_start_time;
    }

    public void setDay2_start_time(String day2_start_time) {
        this.day2_start_time = day2_start_time;
    }

    public String getDay1_end_time() {
        return day1_end_time;
    }

    public void setDay1_end_time(String day1_end_time) {
        this.day1_end_time = day1_end_time;
    }

    public String getDay2_end_time() {
        return day2_end_time;
    }

    public void setDay2_end_time(String day2_end_time) {
        this.day2_end_time = day2_end_time;
    }

    public int getCno() {
        return cno;
    }
    public String getClassname() {
        return classname;
    }
    public String getGrade() {
        return grade;
    }

    public String getClassroom() {
        return classroom;
    }
    public String getMajor() {
        return major;
    }

    public String getPoint() {
        return point;
    }

    public String getProfessor() {
        return professor;
    }

    public String getTime() {
        return time;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

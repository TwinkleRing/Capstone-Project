package com.example.capstone_ui_1.Helper;

public class class_junkong_DB {
    public int cno;
    public String classname;
    public int grade;
    public String major;
    public String point;
    public String professor;
    public String time;

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public int getCno(int anInt) {
        return cno;
    }

    public int getGrade() {
        return grade;
    }

    public String getClassname() {
        return classname;
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
}

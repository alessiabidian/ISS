package com.example.magazinonlineapp.domain;

public class DateTime {
    private Integer min, h, day, month, year;

    public DateTime(Integer min, Integer h, Integer day, Integer month, Integer year) {
        this.min = min;
        this.h = h;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getHour() {
        return h;
    }

    public void setHour(Integer h) {
        this.h = h;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

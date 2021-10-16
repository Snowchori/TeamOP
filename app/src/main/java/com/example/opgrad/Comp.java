package com.example.opgrad;

public class Comp {

    String title;
    String category;
    String startDate;
    String endDate;
    String dDay;

    int comp_id;

    public Comp(String title, String category, String startDate, String endDate, String dDay, int comp_id) {
        this.title = title;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dDay = dDay;

        this.comp_id=comp_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getdDay() {
        return dDay;
    }

    public void setdDay(String dDay) {
        this.dDay = dDay;
    }

    public int getcomp_id() {
        return comp_id;
    }

    public void setcomp_id(int comp_id) {
        this.comp_id = comp_id;
    }
}

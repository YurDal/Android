package com.example.yurdaer.kavun;

import java.io.Serializable;

/**
 * Created by YURDAER on 2017-10-10.
 */

public class Income {
    private int id;
    private String date;
    private String tittle;
    private String category;
    private int amount;

    public Income(int id, String date, String tittle, String category, int amount) {
        this.id=id;
        this.date=date;
        this.tittle=tittle;
        this.category=category;
        this.amount=amount;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTittle() {
        return tittle;
    }

    public String getCategory() {
        return category;
    }

    public String getAmount() {
        return Integer.toString(amount);
    }
}

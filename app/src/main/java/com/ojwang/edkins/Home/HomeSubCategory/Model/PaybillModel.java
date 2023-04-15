package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "paybill_table")
public class PaybillModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String title;
    public String body;

    public PaybillModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}

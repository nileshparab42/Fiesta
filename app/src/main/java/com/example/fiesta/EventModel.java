package com.example.fiesta;

import android.graphics.Bitmap;

import java.util.Date;

public class EventModel {

    Date date;

    String img;

    public EventModel(){}

    public EventModel(Date date, String img, String name, String shdes, String lgdes) {
        this.date = date;
        this.img = img;
        this.name = name;
        this.shdes = shdes;
        this.lgdes = lgdes;
    }

    public EventModel(String img, String name, String shdes) {
        this.img=img;
        this.name=name;
        this.shdes=shdes;
    }

    public EventModel(String img, String name, String shdes, String Price) {
        this.img=img;
        this.name=name;
        this.shdes=shdes;
        this.Price=Price;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShdes() {
        return shdes;
    }

    public void setShdes(String shdes) {
        this.shdes = shdes;
    }

    public String getLgdes() {
        return lgdes;
    }

    public void setLgdes(String lgdes) {
        this.lgdes = lgdes;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    String name, shdes,lgdes, Price;

}

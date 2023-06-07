package com.example.fiesta;

public class GroupModel {
    String img,name,des;

    public GroupModel(){}

    public GroupModel(String img, String name, String des) {
        this.img = img;
        this.name = name;
        this.des = des;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}

package com.example.fiesta;

public class PreticketModel {

    String ename,price,uname,uemail,tid;

    public PreticketModel(){}

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPrice() {
        return price;
    }

    public String getUname() {
        return uname;
    }

    public String getUemail() {
        return uemail;
    }

    public String getTid() {
        return tid;
    }

    public PreticketModel(String ename, String price, String uname, String uemail, String tid) {
        this.ename = ename;
        this.price = price;
        this.uname = uname;
        this.uemail = uemail;
        this.tid = tid;
    }
}

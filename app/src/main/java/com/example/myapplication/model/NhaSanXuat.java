package com.example.myapplication.model;

import java.io.Serializable;

public class NhaSanXuat implements Serializable {
    private int maNSX;
    private String tenNSX;


    public NhaSanXuat() {

    }
    public NhaSanXuat(int maNSX, String tenNSX) {
        this.maNSX = maNSX;
        this.tenNSX = tenNSX;
    }

    public int getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(int maNSX) {
        this.maNSX = maNSX;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    @Override
    public String toString() {
        return tenNSX ;
    }
}

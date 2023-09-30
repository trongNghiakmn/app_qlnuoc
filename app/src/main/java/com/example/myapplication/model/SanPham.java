package com.example.myapplication.model;

import android.graphics.Bitmap;

import com.example.myapplication.model.NhaSanXuat;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int maSP;
    private String tenSP;
    private String loaiSP;
    private int giaSP;
    private String xuatXu;
    private String hinhSP;
    private NhaSanXuat nSX;

    public SanPham(int maSP, String tenSP, String loaiSP, int giaSP, String xuatXu, String hinhSP, NhaSanXuat nSX) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
        this.xuatXu = xuatXu;
        this.hinhSP = hinhSP;
        this.nSX = nSX;
    }
    public SanPham() {

    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public NhaSanXuat getnSX() {
        return nSX;
    }

    public void setnSX(NhaSanXuat nSX) {
        this.nSX = nSX;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSP=" + maSP +
                ", tenSP='" + tenSP + '\'' +
                ", loaiSP='" + loaiSP + '\'' +
                ", giaSP=" + giaSP +
                ", xuatXu='" + xuatXu + '\'' +
                ", hinhSP='" + hinhSP + '\'' +
                ", nSX=" + nSX +
                '}';
    }
}

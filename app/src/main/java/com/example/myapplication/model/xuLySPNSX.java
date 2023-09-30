package com.example.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class xuLySPNSX implements Serializable
{
    private static List<NhaSanXuat> dsnsx=new ArrayList<>();
    private static List<SanPham> dssp=new ArrayList<>();

    static {
//        dsnsx.add(new NhaSanXuat(01,"Pepsico"));
//        dsnsx.add(new NhaSanXuat(02,"Tan Hiep Phat"));
//        dsnsx.add(new NhaSanXuat(03,"Coca"));


    }

    public static List<NhaSanXuat> getDsnsx() {
        return dsnsx;
    }

    public static void setDsnsx(List<NhaSanXuat> dsnsx) {
        xuLySPNSX.dsnsx = dsnsx;
    }

    public static List<SanPham> getDssp() {
        return dssp;
    }

    public static void setDssp(List<SanPham> dssp) {
        xuLySPNSX.dssp = dssp;
    }
}

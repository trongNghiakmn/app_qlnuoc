package com.example.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbUtil extends SQLiteOpenHelper {


    public dbUtil(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE table IF NOT EXISTS nhasanxuat(\n" +
                "                mansx INTEGER PRIMARY KEY ,\n" +
                "                tennsx nvarchar(100)\n" +
                "                );";
        db.execSQL(sql);
        sql = "CREATE table IF NOT EXISTS sanpham(\n" +
                "                                id INTEGER PRIMARY KEY ,\n" +
                "                                tensp nvarchar(100), \n" +
                "                                mansx INTEGER,\n" +
                "                                gia INTEGER,\n" +
                "                                xuatxu nvarchar(100),\n" +
                "                                loai nvarchar(100),\n" +
                "                                img longtext,\n" +
                "                                FOREIGN KEY(mansx)\n" +
                "                                REFERENCES nhasanxuat(mansx)\n" +
                "                                on UPDATE RESTRICT \n" +
                "                                ON DELETE RESTRICT\n" +
                "                               );";
        db.execSQL(sql);
        //db.close();
    }
    public void queryData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL(sql);
    }
    //query select all
    public Cursor getAllData(String sql){
        SQLiteDatabase databse = getReadableDatabase();
        return databse.rawQuery(sql,null);
    }
    public void deleteSanPham(int id){
        SQLiteDatabase database =this.getWritableDatabase();
        String sql = "DELETE FROM sanpham WHERE id ="+id+"";
        database.execSQL(sql);
    }
    public void deleteNhaSanXuat(int id){
        SQLiteDatabase database =this.getWritableDatabase();
        String sql = "DELETE FROM nhasanxuat WHERE mansx ="+id+"";
        database.execSQL(sql);
    }
    public Boolean changeNhaSanXuat(int id,String tennsx){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tennsx",tennsx);
        return database.update("nhasanxuat",contentValues,"mansx="+id,null)>0;
//        SQLiteDatabase database =this.getWritableDatabase();
//        String sql = "UPDATE phongban SET tenpb="+"'"+tenpb+"'"+","+"sopb="+"'"+sopb+"'"+"WHERE id="+id+"";
//        database.execSQL(sql);
    }
    public Boolean changeSanPham(int id,String tensp, int mansx, int gia, String xuatxu,String loai, String hinh ){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp",tensp);
        contentValues.put("mansx",mansx);
        contentValues.put("gia",gia);
        contentValues.put("xuatxu",xuatxu);
        contentValues.put("loai",loai);
        contentValues.put("img",hinh);
        int result =database.update("sanpham",contentValues,"id="+id+"",null);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean insertSanPham(int id,String tensp, int mansx, int gia, String xuatxu,String loai ,String hinh){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("tensp",tensp);
        contentValues.put("mansx",mansx);
        contentValues.put("gia",gia);
        contentValues.put("xuatxu",xuatxu);
        contentValues.put("loai",loai);
        contentValues.put("img",hinh);
        long result = database.insert("sanpham",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

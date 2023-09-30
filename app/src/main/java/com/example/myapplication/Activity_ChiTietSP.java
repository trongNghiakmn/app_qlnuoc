package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.SanPham;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class Activity_ChiTietSP extends AppCompatActivity {

    ImageView hinhsp;
    TextView tensp,gia,loai,nsx,xuatxu,ma;
    Bitmap imageB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        addControl();

        SanPham sp =(SanPham) getIntent().getSerializableExtra("data");


        //string to bitmap
        byte[] bytes = Base64.decode(sp.getHinhSP(),0);
        Bitmap bm =BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);
        hinhsp.setImageBitmap(bm);

        ma.setText(sp.getMaSP()+"");
        tensp.setText(sp.getTenSP());
        gia.setText(sp.getGiaSP()+"");
        loai.setText(sp.getLoaiSP());
        nsx.setText(sp.getnSX().toString());
        xuatxu.setText(sp.getXuatXu());


    }

    private void addControl() {
        ma=findViewById(R.id.tv_chitiet_ma);
        hinhsp=findViewById(R.id.img_chitietsp);
        tensp=findViewById(R.id.tv_chitiet_tensp);
        gia=findViewById(R.id.tv_chitiet_gia);
        loai=findViewById(R.id.tv_chitiet_loai);
        nsx=findViewById(R.id.tv_chitiet_nsx);
        xuatxu=findViewById(R.id.tv_chitiet_xuatxu);
    }
}
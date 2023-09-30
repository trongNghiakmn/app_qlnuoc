package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.model.NhaSanXuat;
import com.example.myapplication.model.SanPham;

public class Activity_chitietNSX extends AppCompatActivity {

    TextView ma,ten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_nsx);
        ma = findViewById(R.id.tv_chitiet_mansx);
        ten = findViewById(R.id.tv_chitiet_tennsx);

        NhaSanXuat sp =(NhaSanXuat) getIntent().getSerializableExtra("nsx");

        ma.setText(sp.getMaNSX()+"");
        ten.setText(sp.getTenNSX());
    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Activity_DanhNhap extends AppCompatActivity {

    Button btndn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_nhap);

        addControl();
        addEvent();
    }

    private void addEvent() {
        btndn.setOnClickListener(v->{
            Intent i =new Intent(Activity_DanhNhap.this,MainActivity.class);
            startActivity(i);
        });
    }

    private void addControl() {
        btndn=findViewById(R.id.btnDangNhap);
    }
}
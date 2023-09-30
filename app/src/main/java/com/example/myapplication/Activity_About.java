package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_About extends AppCompatActivity {


    TextView phone;
    ImageView call;
    static int PERMISSIONC_CODE = 100;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    private static final String LOG_TAG = "AndroidExample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        phone = findViewById(R.id.phone);
        call = findViewById(R.id.btn_goi);

        if(ContextCompat.checkSelfPermission(Activity_About.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Activity_About.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSIONC_CODE);
        }
        call.setOnClickListener(v->{
            String sdt = phone.getText().toString();
            Intent i =new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:"+sdt));
            startActivity(i);
        });
    }


}
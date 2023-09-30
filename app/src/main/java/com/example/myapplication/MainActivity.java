package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.model.NhaSanXuat;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.xuLySPNSX;
import com.example.myapplication.util.dbUtil;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener
{

    Button btn_sp,btn_nsx;
    TextView tv_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        addControl();
        addEvent();


    }

    private void createDB() {
        dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
        //db.queryData("INSERT into nhasanxuat values (2,'coca')");
        Cursor cursor = db.getAllData("select * from nhasanxuat");
        while(cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            xuLySPNSX.getDsnsx().add(new NhaSanXuat(ma,ten));
        }
        Cursor cursor_sp = db.getAllData("select * from sanpham");
        while(cursor_sp.moveToNext()){
            int ma = cursor_sp.getInt(0);
            String ten = cursor_sp.getString(1);
            int mansx = cursor_sp.getInt(2);
            int gia = cursor_sp.getInt(3);
            String xx = cursor_sp.getString(4);
            String loai = cursor_sp.getString(5);
            String img = cursor_sp.getString(6);
            NhaSanXuat nsx =new NhaSanXuat();
            for(NhaSanXuat temp:xuLySPNSX.getDsnsx()){
                if(temp.getMaNSX()==mansx){
                    nsx=temp;
                }            }
            xuLySPNSX.getDssp().add(new SanPham(ma,ten,loai,gia,xx,img,nsx));

        }

    }

    private void addEvent() {
        btn_sp.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this,Activity_DSSP.class);
            startActivity(i);
        });

        btn_nsx.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this,Activity_DSNSX.class);
            startActivity(i);
        });
        tv_about.setOnClickListener(v->{
            PopupMenu popup = new PopupMenu(MainActivity.this, v);
            popup.setOnMenuItemClickListener(MainActivity.this);
            popup.inflate(R.menu.menu_chinh);
            popup.show();
        });
    }


    private void addControl() {
        btn_sp = findViewById(R.id.btn_mainsp);
        btn_nsx = findViewById(R.id.btn_mainnsx);
        tv_about = findViewById(R.id.tv_about);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_about:
                Intent i =new Intent(MainActivity.this,Activity_About.class);
                startActivity(i);
                break;
            case R.id.item_exit:
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
                break;
        }
        return false;
    }
}
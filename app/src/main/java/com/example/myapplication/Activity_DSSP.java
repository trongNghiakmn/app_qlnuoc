package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.adapter.recyclerViewAdapter;
import com.example.myapplication.adapter.recyclerViewAdapter_SP;
import com.example.myapplication.listenner.clickListenner;
import com.example.myapplication.listenner.longClickListenner_sp;
import com.example.myapplication.model.NhaSanXuat;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.xuLySPNSX;
import com.example.myapplication.util.dbUtil;

import java.util.ArrayList;
import java.util.List;

public class Activity_DSSP extends AppCompatActivity implements clickListenner, longClickListenner_sp {

    Button btnthem;
    AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private recyclerViewAdapter_SP readap;
    private List<SanPham>dssp= xuLySPNSX.getDssp();
    int index=0;
    public static final int REQUEST_CODE_SUA=2;
    public static final int REQUEST_CODE_ADD=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssp);

        addControl();

        //SanPham data = (SanPham) getIntent().getSerializableExtra("data");
//        if(data!=null){
//            //them tai day
//            //dssp.add(data);
//            dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
//            //db.queryData("INSERT into nhasanxuat values ("+data.getMaSP()+",'"+ data.getTenSP() +"',"+data.getnSX().getMaNSX()+","+data.getGiaSP()+",'"+data.getXuatXu()+"','"+data.getLoaiSP()+"','"+data.getHinhSP()+"')");
//            db.insertSanPham(data.getMaSP(),data.getTenSP(),data.getnSX().getMaNSX(),data.getGiaSP(),data.getXuatXu(),data.getLoaiSP(),data.getHinhSP());
//            hienThi();
//        }

        dssp=xuLySPNSX.getDssp();
        readap = new recyclerViewAdapter_SP(dssp, this,this);
        //recyclerViewAdapter_SP readapSP = new recyclerViewAdapter_SP(dssp,this);
        recyclerView.setAdapter(readap);


        addEvent();
    }
    private void hienThi(){
        dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
        //db.queryData("INSERT into nhasanxuat values (2,'coca')");
        Cursor cursor_sp = db.getAllData("select * from sanpham");
        dssp.clear();
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
        readap.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD && resultCode==RESULT_OK){
            dssp=xuLySPNSX.getDssp();
            readap.notifyDataSetChanged();
        }
    }





    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item_xoa:
                if(alertDialog==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View view = LayoutInflater.from(this).inflate(R.layout.dialogxoa, (ViewGroup) findViewById(R.id.layoutCustomDialog));
                    builder.setView(view);
                    alertDialog = builder.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    view.findViewById(R.id.yes).setOnClickListener(v -> {
                        //xoa tai day
                        if(index >= 0 && index <dssp.size()){
                            //dssp.remove(index);
                            dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
                            db.deleteSanPham(dssp.get(index).getMaSP());
                        }
                        hienThi();
                        //readap.notifyDataSetChanged();
                        alertDialog.dismiss();


                    });
                    view.findViewById(R.id.no).setOnClickListener(v -> {
                        alertDialog.dismiss();
                    });
                }
                alertDialog.show();
                break;
            case R.id.item_sua:

                Intent i =new Intent(Activity_DSSP.this,Activity_ThemSP.class);
                i.putExtra("sua",index);
                startActivityForResult(i,REQUEST_CODE_SUA);
                break;

        }

        return super.onContextItemSelected(item);
    }


    private void addEvent() {
        btnthem.setOnClickListener(v->{
            Intent intent =new Intent(Activity_DSSP.this,Activity_ThemSP.class);
            startActivityForResult(intent,REQUEST_CODE_ADD);
            //finish();
        });
    }

    private void addControl() {
        btnthem=findViewById(R.id.btn_themsp);
        recyclerView=findViewById(R.id.rcv_dssp);
    }

    @Override
    public void onClickNext(int position) {
        SanPham sp = dssp.get(position);
        Intent i =new Intent(Activity_DSSP.this,Activity_ChiTietSP.class);
        i.putExtra("data",sp);
        startActivity(i);
    }

    @Override
    public void onLongClickNext(int position) {
        index = position;
    }
}
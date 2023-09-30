package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.adapter.recyclerViewAdapter;
import com.example.myapplication.listenner.clickListenner;
import com.example.myapplication.listenner.clickNSX;
import com.example.myapplication.listenner.longClickNSX;
import com.example.myapplication.model.NhaSanXuat;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.xuLySPNSX;
import com.example.myapplication.util.dbUtil;

import java.util.ArrayList;
import java.util.List;

public class Activity_DSNSX extends AppCompatActivity implements longClickNSX, clickNSX {


    private RecyclerView recyclerView;
    private EditText edt_ten,edt_ma;
    private Button btn_them;
    int index=0;
    int token=0;
    AlertDialog alertDialog;
    recyclerViewAdapter readap;
    private List<NhaSanXuat> dsnsx=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsnsx);

        addControl();



        addEvent();
        dsnsx = xuLySPNSX.getDsnsx();
        readap = new recyclerViewAdapter(dsnsx,this,this);
        recyclerView.setAdapter(readap);
    }

    private void addEvent() {
        btn_them.setOnClickListener(v->{
            if(token==0){

                int ma =Integer.parseInt(edt_ma.getText().toString());
                String ten = edt_ten.getText().toString();

                dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
                db.queryData("INSERT into nhasanxuat values ("+ma+",'"+ ten +"')");
                hienThi();
                //NhaSanXuat nsx = new NhaSanXuat(ma,ten);
                //dsnsx.add(nsx);
                //readap.notifyDataSetChanged();
            }else{
                NhaSanXuat sua = xuLySPNSX.getDsnsx().get(index);
                dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
                int ma = Integer.parseInt(edt_ma.getText().toString());
                String ten = edt_ten.getText().toString();
                db.changeNhaSanXuat(ma,ten);

                sua.setTenNSX(edt_ten.getText().toString());
                hienThi();
                token=0;
            }


        });
    }
    private void hienThi(){
        dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
        //db.queryData("INSERT into nhasanxuat values (2,'coca')");
        Cursor cursor = db.getAllData("select * from nhasanxuat");
        dsnsx.clear();
        while(cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            xuLySPNSX.getDsnsx().add(new NhaSanXuat(ma,ten));
        }
        readap.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_xoa_nsx:
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
                        if(index >= 0 && index <dsnsx.size()){
                            int flag=0;
                            //dssp.remove(index);
                            int maxoa=dsnsx.get(index).getMaNSX();
                            for(SanPham sp:xuLySPNSX.getDssp()){
                                if(sp.getnSX().getMaNSX()==maxoa){
                                    flag=1;
                                    break;
                                }
                            }
                            if (flag==0){
                                dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
                                //db.deleteSanPham(dssp.get(index).getMaSP());
                                db.deleteNhaSanXuat(dsnsx.get(index).getMaNSX());
                            }else{
                                Toast.makeText(this, "Co San Pham Roi", Toast.LENGTH_SHORT).show();
                            }

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
            case R.id.item_sua_nsx:
                token=1;
                NhaSanXuat sua = xuLySPNSX.getDsnsx().get(index);
                edt_ma.setText(sua.getMaNSX()+"");
                edt_ten.setText(sua.getTenNSX());
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void addControl() {
        recyclerView=findViewById(R.id.rcv_dsnsx);
        edt_ten=findViewById(R.id.edt_themtennsx);
        edt_ma=findViewById(R.id.edt_themmansx);
        btn_them=findViewById(R.id.btn_themnsx);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE,R.id.item_xoa_nsx,Menu.NONE,"XOA");
        menu.add(Menu.NONE,R.id.item_sua_nsx,Menu.NONE,"SUA");
    }

    @Override
    public void onLongClickNext(int position) {
        index=position;
    }

    @Override
    public void onClickNext(int position) {
        NhaSanXuat nsx = dsnsx.get(position);
        Intent i =new Intent(Activity_DSNSX.this,Activity_chitietNSX.class);
        i.putExtra("nsx",nsx);
        startActivity(i);
    }
}
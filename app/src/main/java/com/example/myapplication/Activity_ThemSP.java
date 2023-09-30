package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.recyclerViewAdapter_SP;
import com.example.myapplication.model.NhaSanXuat;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.xuLySPNSX;
import com.example.myapplication.util.dbUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Activity_ThemSP extends AppCompatActivity {

    EditText tv_ma,tv_ten,tv_gia,tv_xuatxu;
    Spinner spn_loai,spn_nsx;
    ImageView img;
    Button btn_them;
    List<String>dsloai = new ArrayList<>();
    List<NhaSanXuat>dsnsx;
    Bitmap imageB;
    String strimg;
    int sua =0;

    public static final int REQUEST_CODE_IMAGE=1;
    public static final int REQUEST_CODE_Storage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sp);

        addControl();

        dsloai.add("Nuoc ngot co gas");
        dsloai.add("Nuoc ngot");
        dsloai.add("Nuoc suoi");

        ArrayAdapter<String> adapterloai = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dsloai);
        adapterloai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_loai.setAdapter(adapterloai);

        ArrayAdapter<NhaSanXuat> adapternsx = new ArrayAdapter<NhaSanXuat>(this, android.R.layout.simple_spinner_item, xuLySPNSX.getDsnsx());
        adapternsx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_nsx.setAdapter(adapternsx);

        int index = getIntent().getIntExtra("sua",-1);
        sua=index;
        if(index!=-1){
            SanPham data = xuLySPNSX.getDssp().get(index);
            if(data!=null){

                tv_ma.setText(data.getMaSP()+"");
                tv_ten.setText(data.getTenSP());
                tv_gia.setText(data.getGiaSP()+"");
                tv_xuatxu.setText(data.getXuatXu());
                int position = xuLySPNSX.getDsnsx().indexOf(data.getnSX());
                spn_nsx.setSelection(position);


                byte[] bytes = Base64.decode(data.getHinhSP(),0);
                Bitmap bm =BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);
                img.setImageBitmap(bm);

            }
        }


        addEvent();
    }

    private void addEvent() {
        img.setOnClickListener(v->{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_Storage);
        });
        btn_them.setOnClickListener(v->{
            if(sua==-1){
            SanPham sp;
            int ma =Integer.parseInt(tv_ma.getText().toString());
            String ten=tv_ten.getText().toString();
            int gia=Integer.parseInt(tv_gia.getText().toString());
            String sx=tv_xuatxu.getText().toString();

            NhaSanXuat nsx = (NhaSanXuat) spn_nsx.getSelectedItem();

            String loai= (String) spn_loai.getSelectedItem();

            sp = new SanPham(ma,ten,loai,gia,sx,strimg,nsx);
            dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
            db.insertSanPham(sp.getMaSP(),sp.getTenSP(),sp.getnSX().getMaNSX(),sp.getGiaSP(),sp.getXuatXu(),sp.getLoaiSP(),sp.getHinhSP());
            xuLySPNSX.getDssp().add(sp);
            Intent i =new Intent(this,Activity_DSSP.class);
            //i.putExtra("data", sp);
            startActivity(i);
            setResult(RESULT_OK);
            finish();
            }
            else{
                int index = getIntent().getIntExtra("sua",0);
                    SanPham a =xuLySPNSX.getDssp().get(index);
//                a.setTenSP(tv_ten.getText().toString());
//                a.setGiaSP(Integer.parseInt(tv_gia.getText().toString()));
//                a.setXuatXu(tv_xuatxu.getText().toString());
//                a.setnSX((NhaSanXuat) spn_nsx.getSelectedItem());
//                a.setLoaiSP((String) spn_loai.getSelectedItem());
//                if(strimg!=null){
//                   a.setHinhSP(strimg);
//                }
                int ma =Integer.parseInt(tv_ma.getText().toString());
                String ten=tv_ten.getText().toString();
                int gia=Integer.parseInt(tv_gia.getText().toString());
                String sx=tv_xuatxu.getText().toString();
                NhaSanXuat nsx = (NhaSanXuat) spn_nsx.getSelectedItem();
                String loai= (String) spn_loai.getSelectedItem();
                String hinh;
                if(strimg != null){
                    hinh = strimg;
                }else{
                    hinh=a.getHinhSP();
                }
                dbUtil db = new dbUtil(getApplicationContext(),"sp.sql",null,1);
                db.changeSanPham(ma,ten,nsx.getMaNSX(),gia,sx,loai,hinh);

                a.setTenSP(tv_ten.getText().toString());
                a.setGiaSP(Integer.parseInt(tv_gia.getText().toString()));
                a.setXuatXu(tv_xuatxu.getText().toString());
                a.setnSX((NhaSanXuat) spn_nsx.getSelectedItem());
                if(strimg!=null){
                       a.setHinhSP(strimg);
                    }

                Intent i =new Intent(this,Activity_DSSP.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==  REQUEST_CODE_Storage&& grantResults.length>=0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "sai roi", Toast.LENGTH_SHORT).show();
            }        }
    }
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_IMAGE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE&&resultCode==RESULT_OK){
            Uri image = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(image);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                imageB = bm;
                //bitmap to string
                //Bitmap reviewBitmap = Bitmap.createBitmap(bm,200,200,);
                ByteArrayOutputStream byt=  new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG,100,byt);
                byte[] bytes = byt.toByteArray();
                strimg = Base64.encodeToString(bytes,0,bytes.length,0);
                img.setImageBitmap(bm);
                Log.d("msg",image+"");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    private void addControl() {
        tv_ma=findViewById(R.id.edt_themsp_ma);
        tv_ten=findViewById(R.id.edt_themsp_ten);
        tv_gia=findViewById(R.id.edt_themsp_gia);
        tv_xuatxu=findViewById(R.id.edt_themsp_xuatxu);
        spn_loai=findViewById(R.id.spn_them_loai);
        spn_nsx=findViewById(R.id.spn_them_nsx);
        btn_them=findViewById(R.id.btn_them_themsp);
        img=findViewById(R.id.img_them_hinh);
    }
}
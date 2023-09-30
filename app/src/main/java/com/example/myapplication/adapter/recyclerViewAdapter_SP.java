package com.example.myapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.listenner.clickListenner;
import com.example.myapplication.listenner.longClickListenner_sp;
import com.example.myapplication.model.NhaSanXuat;
import com.example.myapplication.model.SanPham;

import java.util.List;

public class recyclerViewAdapter_SP extends RecyclerView.Adapter<recyclerViewAdapter_SP.recyclerViewHoder> {
    private List<SanPham>sp;
    clickListenner clickListenner;
    longClickListenner_sp longclick;

    public recyclerViewAdapter_SP(List<SanPham> sp, com.example.myapplication.listenner.clickListenner clickListenner, longClickListenner_sp longclick) {
        this.sp = sp;
        this.clickListenner = clickListenner;
        this.longclick = longclick;
    }

    @NonNull
    @Override
    public recyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new recyclerViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycalview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewHoder holder, int position) {
        holder.setData(sp.get(position));
        holder.itemView.setOnClickListener(v->{
            clickListenner.onClickNext(position);
        });
        holder.itemView.setOnLongClickListener(v->{
            longclick.onLongClickNext(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return sp.size();
    }


    class recyclerViewHoder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener{
        TextView tv_tensp,tv_giasp;
        ImageView imgsp;

        public recyclerViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_tensp=itemView.findViewById(R.id.tv_item_tensp);
            tv_giasp=itemView.findViewById(R.id.tv_item_gia);
            imgsp = itemView.findViewById(R.id.img_item_rcv);
            itemView.setOnCreateContextMenuListener(this);

        }
        void setData(SanPham sp){
            tv_tensp.setText(sp.getTenSP());
            tv_giasp.setText(sp.getGiaSP()+"");
            //String to byte
            byte[] bt= Base64.decode(sp.getHinhSP(),0);
            //byte to Bitmap
            Bitmap bm = BitmapFactory.decodeByteArray(bt,0,bt.length,null);
            imgsp.setImageBitmap(bm);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,R.id.item_xoa,Menu.NONE,"XOA");
            menu.add(Menu.NONE,R.id.item_sua,Menu.NONE,"SUA");
        }
    }
}

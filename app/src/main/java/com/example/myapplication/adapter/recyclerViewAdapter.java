package com.example.myapplication.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.listenner.clickNSX;
import com.example.myapplication.listenner.longClickListenner_sp;
import com.example.myapplication.listenner.longClickNSX;
import com.example.myapplication.model.NhaSanXuat;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.recyclerViewHoder> {
    private List<NhaSanXuat> nsx;
    private longClickNSX longClick;
    private com.example.myapplication.listenner.clickNSX clickNSX;

    public recyclerViewAdapter(List<NhaSanXuat>nsx,longClickNSX longClick,clickNSX clickNSX){
        this.nsx=nsx;
        this.longClick=longClick;
        this.clickNSX=clickNSX;
    }

    @NonNull
    @Override
    public recyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new recyclerViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_nsx,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewHoder holder, int position) {
       holder.setData(nsx.get(position));
       holder.itemView.setOnLongClickListener(v->{
           longClick.onLongClickNext(position);
           return false;
       });
       holder.itemView.setOnClickListener(v->{
           clickNSX.onClickNext(position);

       });
    }


    @Override
    public int getItemCount() {
        return nsx.size();
    }

    class recyclerViewHoder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tv_tensp,tv_giasp,tv_tennnsx,tv_mansx;
        ImageView imgsp;
        public recyclerViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_tennnsx=itemView.findViewById(R.id.tv_item_tennsx);
            itemView.setOnCreateContextMenuListener(this);
        }
        void setData(NhaSanXuat nsx){
            tv_tennnsx.setText(nsx.getTenNSX());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,R.id.item_xoa_nsx,Menu.NONE,"XOA");
            menu.add(Menu.NONE,R.id.item_sua_nsx,Menu.NONE,"SUA");
        }

    }

}

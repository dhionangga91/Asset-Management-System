package com.example.projectskripsi170101007.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.model.ModelHistoricalPmElectronic;


import java.util.ArrayList;

public class RvaHistoryElectronic extends RecyclerView.Adapter<RvaHistoryElectronic.ViewHolder> {

    //Deklarasi Variable
    private ArrayList<ModelHistoricalPmElectronic> listHistory;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RvaHistoryElectronic(ArrayList<ModelHistoricalPmElectronic> listHistory, Context context) {
        this.listHistory = listHistory;
        this.context = context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView MaintenanceDate,Pic, Remarks;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            MaintenanceDate = itemView.findViewById(R.id.maintenancedate);
            Remarks = itemView.findViewById(R.id.remarks);
            Pic = itemView.findViewById(R.id.pic);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public RvaHistoryElectronic.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_history_pm, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RvaHistoryElectronic.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu

        final String MaintenanceDate = listHistory.get(position).getMaintenanceDate();
        final String Remarks = listHistory.get(position).getRemarks();
        final String Pic = listHistory.get(position).getPic();


        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelHistoricalPmElectronic currentHistory = listHistory.get(position);
        holder.MaintenanceDate.setText(MaintenanceDate);
        holder.Remarks.setText(Remarks);
        holder.Pic.setText(Pic);

    }


    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listHistory.size();
    }

}



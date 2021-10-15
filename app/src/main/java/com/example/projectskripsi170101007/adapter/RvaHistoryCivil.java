package com.example.projectskripsi170101007.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectskripsi170101007.LoginActivity;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.model.ModelHistoricalCivil;

import java.util.ArrayList;

public class RvaHistoryCivil extends RecyclerView.Adapter<RvaHistoryCivil.ViewHolder> {

    //Deklarasi Variable
    private ArrayList<ModelHistoricalCivil> listHistory;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RvaHistoryCivil(ArrayList<ModelHistoricalCivil> listHistory, Context context) {
        this.listHistory = listHistory;
        this.context = context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView MaintenanceDate, Pic, Remarks, Name, Location;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            Name = itemView.findViewById(R.id.name);
            Location = itemView.findViewById(R.id.location);
            MaintenanceDate = itemView.findViewById(R.id.maintenancedate);
            Remarks = itemView.findViewById(R.id.remarks);
            Pic = itemView.findViewById(R.id.pic);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public RvaHistoryCivil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_history_civil, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RvaHistoryCivil.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu

        final String Name = listHistory.get(position).getName();
        final String Location = listHistory.get(position).getLocation();
        final String MaintenanceDate = listHistory.get(position).getMaintenanceDate();
        final String Remarks = listHistory.get(position).getRemarks();
        final String Pic = listHistory.get(position).getPic();


        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelHistoricalCivil currentHistory = listHistory.get(position);
        holder.Name.setText("Name : "+Name);
        holder.Location.setText("Location : "+Location+"th");
        holder.MaintenanceDate.setText("Repair Date : "+MaintenanceDate);
        holder.Remarks.setText("Remarks : "+Remarks);
        holder.Pic.setText("PIC : "+Pic);

    }


    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listHistory.size();
    }

}



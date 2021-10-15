//RecyclerViewAdapter untuk Aset Electrical

package com.example.projectskripsi170101007.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectskripsi170101007.electrical.DeactivateElectrical;
import com.example.projectskripsi170101007.electrical.HistoryListPmElectrical;
import com.example.projectskripsi170101007.model.ModelElectrical;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.electrical.UpdateElectrical;
import com.example.projectskripsi170101007.electrical.addWoElectrical;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder> {

    private AdapterView.OnItemClickListener mListener;
    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelElectrical data, int position);
    }


    //Deklarasi objek dari Interfece
    RecyclerViewAdapter1.dataListener listener;

    //Deklarasi Variable
    private ArrayList<ModelElectrical> listElectrical;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter1(ArrayList<ModelElectrical> listElectrical, Context context) {
        this.listElectrical = listElectrical;
        this.context = context;
        listener = (dataListener) context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CODE, Name, Brand, Capacity, Voltage, Ampere, Location, MaintenanceDate, User, ModifiedDate, Status;
        private ImageView Picture;
        private LinearLayout ListItem;


        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            CODE = itemView.findViewById(R.id.code);
            Name = itemView.findViewById(R.id.name);
            Brand = itemView.findViewById(R.id.brand);
            Capacity = itemView.findViewById(R.id.capacity);
            Voltage = itemView.findViewById(R.id.voltage);
            Ampere = itemView.findViewById(R.id.ampere);
            Location = itemView.findViewById(R.id.location);
            MaintenanceDate = itemView.findViewById(R.id.maintenanceDate);
            Picture = itemView.findViewById(R.id.picture);
            User = itemView.findViewById(R.id.user);
            ModifiedDate =  itemView.findViewById(R.id.modifieddate);
            Status = itemView.findViewById(R.id.status);
            ListItem = itemView.findViewById(R.id.list_item);

        }
    }

    @Override
    public RecyclerViewAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_electrical, parent, false);
        return new RecyclerViewAdapter1.ViewHolder(V);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter1.ViewHolder holder, final int position) {

        final String CODE = listElectrical.get(position).getCode();
        final String Name = listElectrical.get(position).getName();
        final String Brand = listElectrical.get(position).getBrand();
        final String Capacity = listElectrical.get(position).getCapacity();
        final String Voltage = listElectrical.get(position).getVoltage();
        final String Ampere = listElectrical.get(position).getAmpere();
        final String Location = listElectrical.get(position).getLocation();
        final String MaintenanceDate = listElectrical.get(position).getMaintenanceDate();
        final String User = listElectrical.get(position).getUser();
        final String Picture = listElectrical.get(position).getPicture();
        final String ModifiedDate = listElectrical.get(position).getModifiedDate();
        final String Status = listElectrical.get(position).getStatusAset();

        //Memasukan Nilai/Value kedalam View (All TextView)
        ModelElectrical currentElectrical = listElectrical.get(position);
        holder.CODE.setText("ID Asset : "+CODE);
        holder.Name.setText("Name : "+Name);
        holder.Brand.setText("Brand : "+Brand);
        holder.Capacity.setText("Capacity : "+Capacity);
        holder.Voltage.setText("Voltage : "+Voltage);
        holder.Ampere.setText("Current : "+Ampere);
        holder.Location.setText("Floor(th) : "+Location);
        holder.MaintenanceDate.setText("Maintenance : "+MaintenanceDate);
        holder.Status.setText("Asset Status : "+Status);
        Picasso.with(context)
                .load(currentElectrical.getPicture())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_baseline_error_outline_24)
                .fit()
                .centerCrop()
                .into(holder.Picture);
        holder.User.setText("Last modified by : " + User + " ( " + ModifiedDate + " )");

        //Menampilkan Menu Update dan Delete saat user melakukan long klik pada salah satu item
        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final String[] action = {"Update","Add Workorder", "PM History", "Deactivate"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listElectrical, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataCODE", listElectrical.get(position).getCode());
                                bundle.putString("dataName", listElectrical.get(position).getName());
                                bundle.putString("dataBrand", listElectrical.get(position).getBrand());
                                bundle.putString("dataCapacity", listElectrical.get(position).getCapacity());
                                bundle.putString("dataVoltage", listElectrical.get(position).getVoltage());
                                bundle.putString("dataAmpere", listElectrical.get(position).getAmpere());
                                bundle.putString("dataLocation", listElectrical.get(position).getLocation());
                                bundle.putString("dataMaintenanceDate", listElectrical.get(position).getMaintenanceDate());
                                bundle.putString("dataPicture", listElectrical.get(position).getPicture());
                                bundle.putString("dataUser", listElectrical.get(position).getUser());
                                bundle.putString("dataModifiedDate", listElectrical.get(position).getModifiedDate());
                                bundle.putString("dataStatusAset", listElectrical.get(position).getStatusAset());
                                bundle.putString("getPrimaryKey", listElectrical.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdateElectrical.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listElectrical, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundlewo = new Bundle();
                                bundlewo.putString("dataName", listElectrical.get(position).getName());
                                bundlewo.putString("dataLocation", listElectrical.get(position).getLocation());
                                bundlewo.putString("dataStatusAset", listElectrical.get(position).getStatusAset());
                                bundlewo.putString("getPrimaryKey", listElectrical.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), addWoElectrical.class);
                                intent1.putExtras(bundlewo);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataName", listElectrical.get(position).getName());
                                bundleHistory.putString("dataLocation", listElectrical.get(position).getLocation());
                                bundleHistory.putString("getPrimaryKey", listElectrical.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), HistoryListPmElectrical.class);
                                intent2.putExtras(bundleHistory);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleDelete = new Bundle();
                                bundleDelete.putString("dataCODE", listElectrical.get(position).getCode());
                                bundleDelete.putString("dataName", listElectrical.get(position).getName());
                                bundleDelete.putString("dataBrand", listElectrical.get(position).getBrand());
                                bundleDelete.putString("dataCapacity", listElectrical.get(position).getCapacity());
                                bundleDelete.putString("dataVoltage", listElectrical.get(position).getVoltage());
                                bundleDelete.putString("dataAmpere", listElectrical.get(position).getAmpere());
                                bundleDelete.putString("dataLocation", listElectrical.get(position).getLocation());
                                bundleDelete.putString("dataMaintenanceDate", listElectrical.get(position).getMaintenanceDate());
                                bundleDelete.putString("dataPicture", listElectrical.get(position).getPicture());
                                bundleDelete.putString("dataUser", listElectrical.get(position).getUser());
                                bundleDelete.putString("dataModifiedDate", listElectrical.get(position).getModifiedDate());
                                bundleDelete.putString("dataStatusAset", listElectrical.get(position).getStatusAset());
                                bundleDelete.putString("getPrimaryKey", listElectrical.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), DeactivateElectrical.class);
                                intent3.putExtras(bundleDelete);
                                context.startActivity(intent3);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;

            }
        });

    }


    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listElectrical.size();
    }

}


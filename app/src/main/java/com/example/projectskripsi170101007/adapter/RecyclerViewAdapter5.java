//RecyclerViewAdapter untuk Aset Plumbing

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

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.model.ModelPlumbing;
import com.example.projectskripsi170101007.plumbing.DeactivatePlumbing;
import com.example.projectskripsi170101007.plumbing.HistoryListPmPlumbing;
import com.example.projectskripsi170101007.plumbing.UpdatePlumbing;
import com.example.projectskripsi170101007.plumbing.addWoPlumbing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter5 extends RecyclerView.Adapter<RecyclerViewAdapter5.ViewHolder> {

    private AdapterView.OnItemClickListener mListener;
    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelPlumbing data, int position);
    }


    //Deklarasi objek dari Interfece
    RecyclerViewAdapter5.dataListener listener;

    //Deklarasi Variable
    private ArrayList<ModelPlumbing> listPlumbing;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter5(ArrayList<ModelPlumbing> listPlumbing, Context context) {
        this.listPlumbing = listPlumbing;
        this.context = context;
        listener = (RecyclerViewAdapter5.dataListener) context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CODE, Name, Brand, Capacity, Location, MaintenanceDate, User, ModifiedDate, Flow, Pressure, Status;
        private ImageView Picture;
        private LinearLayout ListItem;


        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            CODE = itemView.findViewById(R.id.code);
            Name = itemView.findViewById(R.id.name);
            Brand = itemView.findViewById(R.id.brand);
            Capacity = itemView.findViewById(R.id.capacity);
            Flow = itemView.findViewById(R.id.flow);
            Pressure = itemView.findViewById(R.id.pressure);
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
    public RecyclerViewAdapter5.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_plumbing, parent, false);
        return new RecyclerViewAdapter5.ViewHolder(V);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter5.ViewHolder holder, final int position) {


        //Mengambil Nilai/Value yang terdapat pada RecyclerView berdasarkan Posisi Tertentu

        final String CODE = listPlumbing.get(position).getCode();
        final String Name = listPlumbing.get(position).getName();
        final String Brand = listPlumbing.get(position).getBrand();
        final String Capacity = listPlumbing.get(position).getCapacity();
        final String Flow = listPlumbing.get(position).getFlow();
        final String Pressure = listPlumbing.get(position).getPressure();
        final String Location = listPlumbing.get(position).getLocation();
        final String MaintenanceDate = listPlumbing.get(position).getMaintenanceDate();
        final String User = listPlumbing.get(position).getUser();
        final String Picture = listPlumbing.get(position).getPicture();
        final String ModifiedDate = listPlumbing.get(position).getModifiedDate();
        final String Status = listPlumbing.get(position).getStatusAset();

        //Memasukan Nilai/Value kedalam View (All TextView)
        ModelPlumbing currentPlumbing = listPlumbing.get(position);
        holder.CODE.setText("ID Asset : "+CODE);
        holder.Name.setText("Name : "+Name);
        holder.Brand.setText("Brand : "+Brand);
        holder.Capacity.setText("Capacity : "+Capacity);
        holder.Flow.setText("Flow : "+Flow);
        holder.Pressure.setText("Pressure : "+Pressure);
        holder.Location.setText("Floor(th) : "+Location);
        holder.MaintenanceDate.setText("Maintenance : "+MaintenanceDate);
        holder.Status.setText("Asset Status : "+Status);
        Picasso.with(context)
                .load(currentPlumbing.getPicture())
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
                          dan mengambil data pada listPlumbing, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataCODE", listPlumbing.get(position).getCode());
                                bundle.putString("dataName", listPlumbing.get(position).getName());
                                bundle.putString("dataBrand", listPlumbing.get(position).getBrand());
                                bundle.putString("dataCapacity", listPlumbing.get(position).getCapacity());
                                bundle.putString("dataFlow", listPlumbing.get(position).getFlow());
                                bundle.putString("dataPressure", listPlumbing.get(position).getPressure());
                                bundle.putString("dataLocation", listPlumbing.get(position).getLocation());
                                bundle.putString("dataMaintenanceDate", listPlumbing.get(position).getMaintenanceDate());
                                bundle.putString("dataPicture", listPlumbing.get(position).getPicture());
                                bundle.putString("dataUser", listPlumbing.get(position).getUser());
                                bundle.putString("dataModifiedDate", listPlumbing.get(position).getModifiedDate());
                                bundle.putString("dataStatusAset", listPlumbing.get(position).getStatusAset());
                                bundle.putString("getPrimaryKey", listPlumbing.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdatePlumbing.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listPlumbing, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundlewo = new Bundle();
                                bundlewo.putString("dataName", listPlumbing.get(position).getName());
                                bundlewo.putString("dataLocation", listPlumbing.get(position).getLocation());
                                bundlewo.putString("dataStatusAset", listPlumbing.get(position).getStatusAset());
                                bundlewo.putString("getPrimaryKey", listPlumbing.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), addWoPlumbing.class);
                                intent1.putExtras(bundlewo);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataName", listPlumbing.get(position).getName());
                                bundleHistory.putString("dataLocation", listPlumbing.get(position).getLocation());
                                bundleHistory.putString("getPrimaryKey", listPlumbing.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), HistoryListPmPlumbing.class);
                                intent2.putExtras(bundleHistory);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                //Menggunakan interface untuk mengirim data plumbing, yang akan dihapus
                                Bundle bundleDelete = new Bundle();
                                bundleDelete.putString("dataCODE", listPlumbing.get(position).getCode());
                                bundleDelete.putString("dataName", listPlumbing.get(position).getName());
                                bundleDelete.putString("dataBrand", listPlumbing.get(position).getBrand());
                                bundleDelete.putString("dataCapacity", listPlumbing.get(position).getCapacity());
                                bundleDelete.putString("dataFlow", listPlumbing.get(position).getFlow());
                                bundleDelete.putString("dataPressure", listPlumbing.get(position).getPressure());
                                bundleDelete.putString("dataLocation", listPlumbing.get(position).getLocation());
                                bundleDelete.putString("dataMaintenanceDate", listPlumbing.get(position).getMaintenanceDate());
                                bundleDelete.putString("dataPicture", listPlumbing.get(position).getPicture());
                                bundleDelete.putString("dataUser", listPlumbing.get(position).getUser());
                                bundleDelete.putString("dataModifiedDate", listPlumbing.get(position).getModifiedDate());
                                bundleDelete.putString("dataStatusAset", listPlumbing.get(position).getStatusAset());
                                bundleDelete.putString("getPrimaryKey", listPlumbing.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), DeactivatePlumbing.class);
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
        return listPlumbing.size();
    }

}



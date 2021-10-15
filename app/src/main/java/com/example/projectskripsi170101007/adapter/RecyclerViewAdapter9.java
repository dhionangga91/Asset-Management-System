//RecyclerViewAdapter untuk Aset Electronic

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
import com.example.projectskripsi170101007.electronic.DeactivateElectronic;
import com.example.projectskripsi170101007.electronic.HistoryListPmElectronic;
import com.example.projectskripsi170101007.electronic.UpdateElectronic;
import com.example.projectskripsi170101007.electronic.addWoElectronic;
import com.example.projectskripsi170101007.model.ModelElectronic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter9 extends RecyclerView.Adapter<RecyclerViewAdapter9.ViewHolder> {

    private AdapterView.OnItemClickListener mListener;
    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelElectronic data, int position);
    }


    //Deklarasi objek dari Interfece
    RecyclerViewAdapter9.dataListener listener;
//    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    //Deklarasi Variable
    private ArrayList<ModelElectronic> listElectronic;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter9(ArrayList<ModelElectronic> listElectronic, Context context) {
        this.listElectronic = listElectronic;
        this.context = context;
        listener = (RecyclerViewAdapter9.dataListener) context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CODE, Name, Brand, Capacity, Location, MaintenanceDate, User, ModifiedDate, Type, Voltage, Status;
        private ImageView Picture;
        private LinearLayout ListItem;


        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            CODE = itemView.findViewById(R.id.code);
            Name = itemView.findViewById(R.id.name);
            Brand = itemView.findViewById(R.id.brand);
            Capacity = itemView.findViewById(R.id.capacity);
            Type = itemView.findViewById(R.id.type);
            Voltage = itemView.findViewById(R.id.voltage);
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
    public RecyclerViewAdapter9.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_electronic, parent, false);
        return new RecyclerViewAdapter9.ViewHolder(V);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter9.ViewHolder holder, final int position) {

        final String CODE = listElectronic.get(position).getCode();
        final String Name = listElectronic.get(position).getName();
        final String Brand = listElectronic.get(position).getBrand();
        final String Capacity = listElectronic.get(position).getCapacity();
        final String Type = listElectronic.get(position).getType();
        final String Voltage = listElectronic.get(position).getVoltage();
        final String Location = listElectronic.get(position).getLocation();
        final String MaintenanceDate = listElectronic.get(position).getMaintenanceDate();
        final String User = listElectronic.get(position).getUser();
        final String Picture = listElectronic.get(position).getPicture();
        final String Status = listElectronic.get(position).getStatusAset();
        final String ModifiedDate = listElectronic.get(position).getModifiedDate();

        //Memasukan Nilai/Value kedalam View (All TextView)
        ModelElectronic currentElectronic = listElectronic.get(position);
        holder.CODE.setText("Code : "+CODE);
        holder.Name.setText("Name : "+Name);
        holder.Brand.setText("Brand : "+Brand);
        holder.Capacity.setText("Capacity : "+Capacity);
        holder.Type.setText("Type : "+Type);
        holder.Voltage.setText("Voltage : "+Voltage);
        holder.Location.setText("Floor(th) : "+Location);
        holder.MaintenanceDate.setText("Maintenance : "+MaintenanceDate);
        holder.Status.setText("Asset Status : "+Status);
        Picasso.with(context)
                .load(currentElectronic.getPicture())
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
                final String[] action = {"Update","Add Workorder", "PM History", "Deactivate" };
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listElectronic, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataCODE", listElectronic.get(position).getCode());
                                bundle.putString("dataName", listElectronic.get(position).getName());
                                bundle.putString("dataBrand", listElectronic.get(position).getBrand());
                                bundle.putString("dataCapacity", listElectronic.get(position).getCapacity());
                                bundle.putString("dataType", listElectronic.get(position).getType());
                                bundle.putString("dataVoltage", listElectronic.get(position).getVoltage());
                                bundle.putString("dataLocation", listElectronic.get(position).getLocation());
                                bundle.putString("dataMaintenanceDate", listElectronic.get(position).getMaintenanceDate());
                                bundle.putString("dataPicture", listElectronic.get(position).getPicture());
                                bundle.putString("dataUser", listElectronic.get(position).getUser());
                                bundle.putString("dataModifiedDate", listElectronic.get(position).getModifiedDate());
                                bundle.putString("dataStatusAset", listElectronic.get(position).getStatusAset());
                                bundle.putString("getPrimaryKey", listElectronic.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdateElectronic.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listElectronic, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundlewo = new Bundle();
                                bundlewo.putString("dataName", listElectronic.get(position).getName());
                                bundlewo.putString("dataLocation", listElectronic.get(position).getLocation());
                                bundlewo.putString("dataStatusAset", listElectronic.get(position).getStatusAset());
                                bundlewo.putString("getPrimaryKey", listElectronic.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), addWoElectronic.class);
                                intent1.putExtras(bundlewo);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataName", listElectronic.get(position).getName());
                                bundleHistory.putString("dataLocation", listElectronic.get(position).getLocation());
                                bundleHistory.putString("getPrimaryKey", listElectronic.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), HistoryListPmElectronic.class);
                                intent2.putExtras(bundleHistory);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                //Menggunakan interface untuk mengirim data electronic, yang akan dihapus
//                                listener.onDeleteData(listElectronic.get(position), position);
//                                break;
                                Bundle bundleDelete = new Bundle();
                                bundleDelete.putString("dataCODE", listElectronic.get(position).getCode());
                                bundleDelete.putString("dataName", listElectronic.get(position).getName());
                                bundleDelete.putString("dataBrand", listElectronic.get(position).getBrand());
                                bundleDelete.putString("dataCapacity", listElectronic.get(position).getCapacity());
                                bundleDelete.putString("dataType", listElectronic.get(position).getType());
                                bundleDelete.putString("dataVoltage", listElectronic.get(position).getVoltage());
                                bundleDelete.putString("dataLocation", listElectronic.get(position).getLocation());
                                bundleDelete.putString("dataMaintenanceDate", listElectronic.get(position).getMaintenanceDate());
                                bundleDelete.putString("dataPicture", listElectronic.get(position).getPicture());
                                bundleDelete.putString("dataUser", listElectronic.get(position).getUser());
                                bundleDelete.putString("dataModifiedDate", listElectronic.get(position).getModifiedDate());
                                bundleDelete.putString("dataStatusAset", listElectronic.get(position).getStatusAset());
                                bundleDelete.putString("getPrimaryKey", listElectronic.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), DeactivateElectronic.class);
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
        return listElectronic.size();
    }

}



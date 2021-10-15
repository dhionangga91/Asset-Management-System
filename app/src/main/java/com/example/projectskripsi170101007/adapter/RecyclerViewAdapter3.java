//RecyclerViewAdapter untuk Aset HVAC

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
import com.example.projectskripsi170101007.hvac.DeactivateHvac;
import com.example.projectskripsi170101007.hvac.HistoryListPmHvac;
import com.example.projectskripsi170101007.hvac.UpdateHvac;
import com.example.projectskripsi170101007.hvac.addWoHvac;
import com.example.projectskripsi170101007.model.ModelHvac;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder> {

    private AdapterView.OnItemClickListener mListener;
    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelHvac data, int position);
    }


    //Deklarasi objek dari Interfece
    RecyclerViewAdapter3.dataListener listener;
//    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    //Deklarasi Variable
    private ArrayList<ModelHvac> listHvac;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter3(ArrayList<ModelHvac> listHvac, Context context) {
        this.listHvac = listHvac;
        this.context = context;
        listener = (RecyclerViewAdapter3.dataListener) context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CODE, Name, Brand, Capacity, Location, MaintenanceDate, User, ModifiedDate, Status;
        private ImageView Picture;
        private LinearLayout ListItem;


        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            CODE = itemView.findViewById(R.id.code);
            Name = itemView.findViewById(R.id.name);
            Brand = itemView.findViewById(R.id.brand);
            Capacity = itemView.findViewById(R.id.capacity);
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
    public RecyclerViewAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_hvac, parent, false);
        return new RecyclerViewAdapter3.ViewHolder(V);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter3.ViewHolder holder, final int position) {


        final String CODE = listHvac.get(position).getCode();
        final String Name = listHvac.get(position).getName();
        final String Brand = listHvac.get(position).getBrand();
        final String Capacity = listHvac.get(position).getCapacity();
        final String Location = listHvac.get(position).getLocation();
        final String MaintenanceDate = listHvac.get(position).getMaintenanceDate();
        final String User = listHvac.get(position).getUser();
        final String Picture = listHvac.get(position).getPicture();
        final String ModifiedDate = listHvac.get(position).getModifiedDate();
        final String Status = listHvac.get(position).getStatusAset();

        //Memasukan Nilai/Value kedalam View (All TextView)
        ModelHvac currentHvac = listHvac.get(position);
        holder.CODE.setText("ID Asset : "+CODE);
        holder.Name.setText("Name : "+Name);
        holder.Brand.setText("Brand : "+Brand);
        holder.Capacity.setText("Capacity : "+Capacity);
        holder.Location.setText("Floor(th) : "+Location);
        holder.MaintenanceDate.setText("Maintenance : "+MaintenanceDate);
        holder.Status.setText("Asset Status : "+Status);
        Picasso.with(context)
                .load(currentHvac.getPicture())
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
                          dan mengambil data pada listHvac, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataCODE", listHvac.get(position).getCode());
                                bundle.putString("dataName", listHvac.get(position).getName());
                                bundle.putString("dataBrand", listHvac.get(position).getBrand());
                                bundle.putString("dataCapacity", listHvac.get(position).getCapacity());
                                bundle.putString("dataLocation", listHvac.get(position).getLocation());
                                bundle.putString("dataMaintenanceDate", listHvac.get(position).getMaintenanceDate());
                                bundle.putString("dataPicture", listHvac.get(position).getPicture());
                                bundle.putString("dataUser", listHvac.get(position).getUser());
                                bundle.putString("dataModifiedDate", listHvac.get(position).getModifiedDate());
                                bundle.putString("dataStatusAset", listHvac.get(position).getStatusAset());
                                bundle.putString("getPrimaryKey", listHvac.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdateHvac.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listHvac, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundlewo = new Bundle();
                                bundlewo.putString("dataName", listHvac.get(position).getName());
                                bundlewo.putString("dataLocation", listHvac.get(position).getLocation());
                                bundlewo.putString("dataStatusAset", listHvac.get(position).getStatusAset());
                                bundlewo.putString("getPrimaryKey", listHvac.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), addWoHvac.class);
                                intent1.putExtras(bundlewo);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataName", listHvac.get(position).getName());
                                bundleHistory.putString("dataLocation", listHvac.get(position).getLocation());
                                bundleHistory.putString("getPrimaryKey", listHvac.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), HistoryListPmHvac.class);
                                intent2.putExtras(bundleHistory);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                //Menggunakan interface untuk mengirim data hvac, yang akan dihapus
                                Bundle bundleDelete = new Bundle();
                                bundleDelete.putString("dataCODE", listHvac.get(position).getCode());
                                bundleDelete.putString("dataName", listHvac.get(position).getName());
                                bundleDelete.putString("dataBrand", listHvac.get(position).getBrand());
                                bundleDelete.putString("dataCapacity", listHvac.get(position).getCapacity());
                                bundleDelete.putString("dataLocation", listHvac.get(position).getLocation());
                                bundleDelete.putString("dataMaintenanceDate", listHvac.get(position).getMaintenanceDate());
                                bundleDelete.putString("dataPicture", listHvac.get(position).getPicture());
                                bundleDelete.putString("dataUser", listHvac.get(position).getUser());
                                bundleDelete.putString("dataModifiedDate", listHvac.get(position).getModifiedDate());
                                bundleDelete.putString("dataStatusAset", listHvac.get(position).getStatusAset());
                                bundleDelete.putString("getPrimaryKey", listHvac.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), DeactivateHvac.class);
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
        return listHvac.size();
    }

}


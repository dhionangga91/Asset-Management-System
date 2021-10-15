// RecyclerViewAdapter untuk Asset Lift

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
import com.example.projectskripsi170101007.lift.AddWoLift;
import com.example.projectskripsi170101007.lift.DeactivateLift;
import com.example.projectskripsi170101007.lift.HistoryListPmLift;
import com.example.projectskripsi170101007.lift.UpdateLift;
import com.example.projectskripsi170101007.model.ModelLift;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter11 extends RecyclerView.Adapter<RecyclerViewAdapter11.ViewHolder> {

    private AdapterView.OnItemClickListener mListener;
    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelLift data, int position);
    }


    //Deklarasi objek dari Interfece
    RecyclerViewAdapter11.dataListener listener;
//    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    //Deklarasi Variable
    private ArrayList<ModelLift> listLift;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter11(ArrayList<ModelLift> listLift, Context context) {
        this.listLift = listLift;
        this.context = context;
        listener = (RecyclerViewAdapter11.dataListener) context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CODE, Name, Brand, Capacity, Location, Type, Power, Rope, Speed, MaintenanceDate, User, ModifiedDate, Status;
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
            Type = itemView.findViewById(R.id.type);
            Power = itemView.findViewById(R.id.power);
            Rope = itemView.findViewById(R.id.rope);
            Speed = itemView.findViewById(R.id.speed);
            MaintenanceDate = itemView.findViewById(R.id.maintenancedate);
            User = itemView.findViewById(R.id.user);
            ModifiedDate =  itemView.findViewById(R.id.modifieddate);
            Status = itemView.findViewById(R.id.status);
            Picture = itemView.findViewById(R.id.picture);
            ListItem = itemView.findViewById(R.id.list_item);

        }
    }

    @Override
    public RecyclerViewAdapter11.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design_lift, parent, false);
        return new RecyclerViewAdapter11.ViewHolder(V);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter11.ViewHolder holder, final int position) {

        final String CODE = listLift.get(position).getCode();
        final String Name = listLift.get(position).getName();
        final String Brand = listLift.get(position).getBrand();
        final String Capacity = listLift.get(position).getCapacity();
        final String Location = listLift.get(position).getLocation();
        final String Type = listLift.get(position).getType();
        final String Power = listLift.get(position).getPower();
        final String Rope = listLift.get(position).getRope();
        final String Speed = listLift.get(position).getSpeed();
        final String MaintenanceDate = listLift.get(position).getMaintenanceDate();
        final String ModifiedDate = listLift.get(position).getModifiedDate();
        final String User = listLift.get(position).getUser();
        final String Status = listLift.get(position).getStatusAset();
        final String Picture = listLift.get(position).getPicture();

        //Memasukan Nilai/Value kedalam View (All TextView)
        ModelLift currentlift = listLift.get(position);
        holder.CODE.setText("ID Asset : "+CODE);
        holder.Name.setText("Name : "+Name);
        holder.Brand.setText("Brand : "+Brand);
        holder.Capacity.setText("Capacity : "+Capacity);
        holder.Location.setText("Floor(th) : "+Location);
        holder.Type.setText("Type : "+Type);
        holder.Power.setText("Power : "+Power);
        holder.Rope.setText("Rope Diameter : "+Rope);
        holder.Speed.setText("Speed : "+Speed);
        holder.MaintenanceDate.setText("Maintenance : "+MaintenanceDate);
        holder.Status.setText("Asset Status : "+Status);
        Picasso.with(context)
                .load(currentlift.getPicture())
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
                          dan mengambil data pada listLift, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataCODE", listLift.get(position).getCode());
                                bundle.putString("dataName", listLift.get(position).getName());
                                bundle.putString("dataBrand", listLift.get(position).getBrand());
                                bundle.putString("dataCapacity", listLift.get(position).getCapacity());
                                bundle.putString("dataLocation", listLift.get(position).getLocation());
                                bundle.putString("dataType", listLift.get(position).getType());
                                bundle.putString("dataPower", listLift.get(position).getPower());
                                bundle.putString("dataRope", listLift.get(position).getRope());
                                bundle.putString("dataSpeed", listLift.get(position).getSpeed());
                                bundle.putString("dataMaintenanceDate", listLift.get(position).getMaintenanceDate());
                                bundle.putString("dataModifiedDate", listLift.get(position).getModifiedDate());
                                bundle.putString("dataStatusAset", listLift.get(position).getStatusAset());
                                bundle.putString("dataPicture", listLift.get(position).getPicture());
                                bundle.putString("dataUser", listLift.get(position).getUser());
                                bundle.putString("getPrimaryKey", listLift.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdateLift.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listlift, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundlewo = new Bundle();
                                bundlewo.putString("dataName", listLift.get(position).getName());
                                bundlewo.putString("dataLocation", listLift.get(position).getLocation());
                                bundlewo.putString("dataStatusAset", listLift.get(position).getStatusAset());
                                bundlewo.putString("getPrimaryKey", listLift.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), AddWoLift.class);
                                intent1.putExtras(bundlewo);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataName", listLift.get(position).getName());
                                bundleHistory.putString("dataLocation", listLift.get(position).getLocation());
                                bundleHistory.putString("getPrimaryKey", listLift.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), HistoryListPmLift.class);
                                intent2.putExtras(bundleHistory);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                //Menggunakan interface untuk mengirim data lift, yang akan dihapus
                                Bundle bundleDelete = new Bundle();
                                bundleDelete.putString("dataCODE", listLift.get(position).getCode());
                                bundleDelete.putString("dataName", listLift.get(position).getName());
                                bundleDelete.putString("dataBrand", listLift.get(position).getBrand());
                                bundleDelete.putString("dataCapacity", listLift.get(position).getCapacity());
                                bundleDelete.putString("dataLocation", listLift.get(position).getLocation());
                                bundleDelete.putString("dataType", listLift.get(position).getType());
                                bundleDelete.putString("dataPower", listLift.get(position).getPower());
                                bundleDelete.putString("dataRope", listLift.get(position).getRope());
                                bundleDelete.putString("dataSpeed", listLift.get(position).getSpeed());
                                bundleDelete.putString("dataMaintenanceDate", listLift.get(position).getMaintenanceDate());
                                bundleDelete.putString("dataModifiedDate", listLift.get(position).getModifiedDate());
                                bundleDelete.putString("dataStatusAset", listLift.get(position).getStatusAset());
                                bundleDelete.putString("dataPicture", listLift.get(position).getPicture());
                                bundleDelete.putString("dataUser", listLift.get(position).getUser());
                                bundleDelete.putString("getPrimaryKey", listLift.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), DeactivateLift.class);
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
        return listLift.size();
    }

}


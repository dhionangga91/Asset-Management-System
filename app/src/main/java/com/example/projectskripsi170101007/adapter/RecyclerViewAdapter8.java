// RecyclerViewAdapter untuk Workorder Civil

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.civil.AddHistoryPmCivil;
import com.example.projectskripsi170101007.civil.MyListWoCivil;
import com.example.projectskripsi170101007.civil.Update1WoCivil;
import com.example.projectskripsi170101007.civil.Update2WoCivil;
import com.example.projectskripsi170101007.civil.Update3WoCivil;
import com.example.projectskripsi170101007.civil.Update4WoCivil;
import com.example.projectskripsi170101007.model.ModelWoCivil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter8 extends RecyclerView.Adapter<RecyclerViewAdapter8.ViewHolder>{

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelWoCivil data, int position);
    }

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter8.dataListener listener;


    //Deklarasi Variable
    private ArrayList<ModelWoCivil> listWoCivil;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter8(ArrayList<ModelWoCivil> listWoCivil, Context context) {
        this.listWoCivil = listWoCivil;
        this.context = context;
        listener = (MyListWoCivil)context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Wocode, Name, Pic, Status, Location, MaintenanceDate, Remarks, UserInput, WoDate, UserStart, StartDate, UserFinish, FinishDate, UserApproved, ApprovedDate, UserReject, RejectDate;
        private LinearLayout ListItem;
        private ImageView Container;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
            Wocode = itemView.findViewById(R.id.wocode);
            Name = itemView.findViewById(R.id.name);
            Location = itemView.findViewById(R.id.location);
            Pic = itemView.findViewById(R.id.pic);
            MaintenanceDate = itemView.findViewById(R.id.maintenanceDate);
            Remarks = itemView.findViewById(R.id.remarks);
            Status = itemView.findViewById(R.id.status);
            Container = itemView.findViewById(R.id.imageView);
            WoDate = itemView.findViewById(R.id.woDate);
            FinishDate = itemView.findViewById(R.id.finishDate);
            UserFinish = itemView.findViewById(R.id.userFinish);
            UserInput = itemView.findViewById(R.id.userInput);
            UserStart = itemView.findViewById(R.id.userStart);
            StartDate = itemView.findViewById(R.id.startDate);
            UserApproved = itemView.findViewById(R.id.userApproved);
            ApprovedDate = itemView.findViewById(R.id.approvedDate);
            UserReject = itemView.findViewById(R.id.userReject);
            RejectDate = itemView.findViewById(R.id.rejectDate);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public RecyclerViewAdapter8.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design2, parent, false);
        return new RecyclerViewAdapter8.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter8.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Wocode = listWoCivil.get(position).getWocode();
        final String Name = listWoCivil.get(position).getName();
        final String Location = listWoCivil.get(position).getLocation();
        final String Pic = listWoCivil.get(position).getPic();
        final String MaintenanceDate = listWoCivil.get(position).getMaintenanceDate();
        final String Remarks = listWoCivil.get(position).getRemarks();
        final String Status = listWoCivil.get(position).getStatus();
        final String UserInput = listWoCivil.get(position).getUserInput();
        final String WoDate = listWoCivil.get(position).getWoDate();
        final String UserStart = listWoCivil.get(position).getUserStart();
        final String StartDate = listWoCivil.get(position).getStartDate();
        final String UserFinish = listWoCivil.get(position).getUserFinish();
        final String FinishDate = listWoCivil.get(position).getFinishDate();
        final String UserApproved = listWoCivil.get(position).getUserApproved();
        final String ApprovedDate = listWoCivil.get(position).getApprovedDate();
        final String UserReject = listWoCivil.get(position).getUserReject();
        final String RejectDate = listWoCivil.get(position).getRejectDate();
        final String Container = listWoCivil.get(position).getPicture();

        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelWoCivil currentWoCivil = listWoCivil.get(position);
        holder.Wocode.setText("Code: "+Wocode);
        holder.Name.setText("Name: "+Name);
        holder.Location.setText("Floor(th): "+Location);
        holder.Pic.setText("Pic: "+Pic);
        holder.MaintenanceDate.setText("Schedule Date: "+MaintenanceDate);
        holder.Remarks.setText("Remarks: "+Remarks);
        holder.Status.setText("Status: "+Status);
        Picasso.with(context)
                .load(currentWoCivil.getPicture())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_baseline_error_outline_24)
                .fit()
                .centerCrop()
                .into(holder.Container);
        holder.UserInput.setText("Made by : "+UserInput);
        holder.WoDate.setText(" ( "+WoDate + " )");
        holder.UserStart.setText("Handle by :"+UserStart);
        holder.StartDate.setText(" ( " +StartDate + " )");
        holder.UserFinish.setText("Finished by : "+UserFinish);
        holder.FinishDate.setText(" ( "+FinishDate + " )");
        holder.UserApproved.setText("Approved by :"+UserApproved);
        holder.ApprovedDate.setText(" ( " +ApprovedDate + " )");
        holder.UserReject.setText("Rejected by : "+UserReject);
        holder.RejectDate.setText(" ( "+RejectDate + " )");

        //Menampilkan Menu Update dan Delete saat user melakukan long klik pada salah satu item
        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final String[] action = {"Start", "Finish", "Approved", "Reject", "Add History"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:

//                          Berpindah Activity pada halaman layout updateData dan mengambil data pada list, berdasarkan posisinya untuk dikirim pada activity selanjutnya

                                Bundle bundle = new Bundle();
                                bundle.putString("dataWocode", listWoCivil.get(position).getWocode());
                                bundle.putString("dataName", listWoCivil.get(position).getName());
                                bundle.putString("dataLocation", listWoCivil.get(position).getLocation());
                                bundle.putString("dataPic", listWoCivil.get(position).getPic());
                                bundle.putString("dataMaintenanceDate", listWoCivil.get(position).getMaintenanceDate());
                                bundle.putString("dataRemarks", listWoCivil.get(position).getRemarks());
                                bundle.putString("dataStatus", listWoCivil.get(position).getStatus());
                                bundle.putString("dataPicture", listWoCivil.get(position).getPicture());
                                bundle.putString("dataUserInput", listWoCivil.get(position).getUserInput());
                                bundle.putString("dataWoDate", listWoCivil.get(position).getWoDate());
                                bundle.putString("dataUserStart", listWoCivil.get(position).getUserStart());
                                bundle.putString("dataStartDate", listWoCivil.get(position).getStartDate());
                                bundle.putString("dataUserFinish", listWoCivil.get(position).getUserFinish());
                                bundle.putString("dataFinishDate", listWoCivil.get(position).getFinishDate());
                                bundle.putString("dataUserApproved", listWoCivil.get(position).getUserApproved());
                                bundle.putString("dataApprovedDate", listWoCivil.get(position).getApprovedDate());
                                bundle.putString("dataUserReject",listWoCivil.get(position).getUserReject());
                                bundle.putString("dataRejectDate", listWoCivil.get(position).getRejectDate());
                                bundle.putString("getPrimaryKey", listWoCivil.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), Update1WoCivil.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Bundle bundleFinish = new Bundle();
                                bundleFinish.putString("dataWocode", listWoCivil.get(position).getWocode());
                                bundleFinish.putString("dataName", listWoCivil.get(position).getName());
                                bundleFinish.putString("dataLocation", listWoCivil.get(position).getLocation());
                                bundleFinish.putString("dataPic", listWoCivil.get(position).getPic());
                                bundleFinish.putString("dataMaintenanceDate", listWoCivil.get(position).getMaintenanceDate());
                                bundleFinish.putString("dataRemarks", listWoCivil.get(position).getRemarks());
                                bundleFinish.putString("dataStatus",listWoCivil.get(position).getStatus());
                                bundleFinish.putString("dataPicture", listWoCivil.get(position).getPicture());
                                bundleFinish.putString("dataUserInput", listWoCivil.get(position).getUserInput());
                                bundleFinish.putString("dataWoDate", listWoCivil.get(position).getWoDate());
                                bundleFinish.putString("dataUserStart", listWoCivil.get(position).getUserStart());
                                bundleFinish.putString("dataStartDate", listWoCivil.get(position).getStartDate());
                                bundleFinish.putString("dataUserFinish", listWoCivil.get(position).getUserFinish());
                                bundleFinish.putString("dataFinishDate", listWoCivil.get(position).getFinishDate());
                                bundleFinish.putString("dataUserApproved", listWoCivil.get(position).getUserApproved());
                                bundleFinish.putString("dataApprovedDate", listWoCivil.get(position).getApprovedDate());
                                bundleFinish.putString("dataUserReject", listWoCivil.get(position).getUserReject());
                                bundleFinish.putString("dataRejectDate", listWoCivil.get(position).getRejectDate());
                                bundleFinish.putString("getPrimaryKey",listWoCivil.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), Update2WoCivil.class);
                                intent1.putExtras(bundleFinish);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleApproved = new Bundle();
                                bundleApproved.putString("dataWocode", listWoCivil.get(position).getWocode());
                                bundleApproved.putString("dataName", listWoCivil.get(position).getName());
                                bundleApproved.putString("dataLocation", listWoCivil.get(position).getLocation());
                                bundleApproved.putString("dataPic", listWoCivil.get(position).getPic());
                                bundleApproved.putString("dataMaintenanceDate", listWoCivil.get(position).getMaintenanceDate());
                                bundleApproved.putString("dataRemarks", listWoCivil.get(position).getRemarks());
                                bundleApproved.putString("dataStatus", listWoCivil.get(position).getStatus());
                                bundleApproved.putString("dataPicture", listWoCivil.get(position).getPicture());
                                bundleApproved.putString("dataUserInput", listWoCivil.get(position).getUserInput());
                                bundleApproved.putString("dataWoDate", listWoCivil.get(position).getWoDate());
                                bundleApproved.putString("dataUserStart", listWoCivil.get(position).getUserStart());
                                bundleApproved.putString("dataStartDate", listWoCivil.get(position).getStartDate());
                                bundleApproved.putString("dataUserFinish", listWoCivil.get(position).getUserFinish());
                                bundleApproved.putString("dataFinishDate", listWoCivil.get(position).getFinishDate());
                                bundleApproved.putString("dataUserApproved", listWoCivil.get(position).getUserApproved());
                                bundleApproved.putString("dataApprovedDate", listWoCivil.get(position).getApprovedDate());
                                bundleApproved.putString("dataUserReject", listWoCivil.get(position).getUserReject());
                                bundleApproved.putString("dataRejectDate", listWoCivil.get(position).getRejectDate());
                                bundleApproved.putString("getPrimaryKey", listWoCivil.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), Update3WoCivil.class);
                                intent2.putExtras(bundleApproved);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleReject = new Bundle();
                                bundleReject.putString("dataWocode", listWoCivil.get(position).getWocode());
                                bundleReject.putString("dataName", listWoCivil.get(position).getName());
                                bundleReject.putString("dataLocation", listWoCivil.get(position).getLocation());
                                bundleReject.putString("dataPic", listWoCivil.get(position).getPic());
                                bundleReject.putString("dataMaintenanceDate", listWoCivil.get(position).getMaintenanceDate());
                                bundleReject.putString("dataRemarks", listWoCivil.get(position).getRemarks());
                                bundleReject.putString("dataStatus", listWoCivil.get(position).getStatus());
                                bundleReject.putString("dataPicture", listWoCivil.get(position).getPicture());
                                bundleReject.putString("dataUserInput", listWoCivil.get(position).getUserInput());
                                bundleReject.putString("dataWoDate", listWoCivil.get(position).getWoDate());
                                bundleReject.putString("dataUserStart", listWoCivil.get(position).getUserStart());
                                bundleReject.putString("dataStartDate",listWoCivil.get(position).getStartDate());
                                bundleReject.putString("dataUserFinish", listWoCivil.get(position).getUserFinish());
                                bundleReject.putString("dataFinishDate", listWoCivil.get(position).getFinishDate());
                                bundleReject.putString("dataUserApproved", listWoCivil.get(position).getUserApproved());
                                bundleReject.putString("dataApprovedDate", listWoCivil.get(position).getApprovedDate());
                                bundleReject.putString("dataUserReject", listWoCivil.get(position).getUserReject());
                                bundleReject.putString("dataRejectDate", listWoCivil.get(position).getRejectDate());
                                bundleReject.putString("getPrimaryKey", listWoCivil.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), Update4WoCivil.class);
                                intent3.putExtras(bundleReject);
                                context.startActivity(intent3);
                                break;
                            case 4:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataStatus", listWoCivil.get(position).getStatus());
                                bundleHistory.putString("dataLocation", listWoCivil.get(position).getLocation());
                                bundleHistory.putString("dataName", listWoCivil.get(position).getName());
                                bundleHistory.putString("dataPic", listWoCivil.get(position).getPic());
                                bundleHistory.putString("dataFinishDate", listWoCivil.get(position).getFinishDate());
                                bundleHistory.putString("dataRemarks", listWoCivil.get(position).getRemarks());
                                Intent intent4 = new Intent(view.getContext(), AddHistoryPmCivil.class);
                                intent4.putExtras(bundleHistory);
                                context.startActivity(intent4);
                                break;
                            case 5:
                                //Menggunakan interface untuk mengirim data civil, yang akan dihapus
                                listener.onDeleteData(listWoCivil.get(position), position);
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
        return listWoCivil.size();
    }

}


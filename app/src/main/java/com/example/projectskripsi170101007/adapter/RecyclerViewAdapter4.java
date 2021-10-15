//RecyclerViewAdapter untuk Workorder Hvac

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
import com.example.projectskripsi170101007.hvac.AddHistoryPmHvac;
import com.example.projectskripsi170101007.hvac.MyListWoHvac;
import com.example.projectskripsi170101007.hvac.Update1WoHvac;
import com.example.projectskripsi170101007.hvac.Update2WoHvac;
import com.example.projectskripsi170101007.hvac.Update3WoHvac;
import com.example.projectskripsi170101007.hvac.Update4WoHvac;
import com.example.projectskripsi170101007.model.ModelWoHvac;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter4 extends RecyclerView.Adapter<RecyclerViewAdapter4.ViewHolder>{

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelWoHvac data, int position);
    }

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter4.dataListener listener;


    //Deklarasi Variable
    private ArrayList<ModelWoHvac> listWoHvac;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter4(ArrayList<ModelWoHvac> listWoHvac, Context context) {
        this.listWoHvac = listWoHvac;
        this.context = context;
        listener = (MyListWoHvac)context;
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
    public RecyclerViewAdapter4.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design2, parent, false);
        return new RecyclerViewAdapter4.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter4.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Wocode = listWoHvac.get(position).getWocode();
        final String Name = listWoHvac.get(position).getName();
        final String Location = listWoHvac.get(position).getLocation();
        final String Pic = listWoHvac.get(position).getPic();
        final String MaintenanceDate = listWoHvac.get(position).getMaintenanceDate();
        final String Remarks = listWoHvac.get(position).getRemarks();
        final String Status = listWoHvac.get(position).getStatus();
        final String UserInput = listWoHvac.get(position).getUserInput();
        final String WoDate = listWoHvac.get(position).getWoDate();
        final String UserStart = listWoHvac.get(position).getUserStart();
        final String StartDate = listWoHvac.get(position).getStartDate();
        final String UserFinish = listWoHvac.get(position).getUserFinish();
        final String FinishDate = listWoHvac.get(position).getFinishDate();
        final String UserApproved = listWoHvac.get(position).getUserApproved();
        final String ApprovedDate = listWoHvac.get(position).getApprovedDate();
        final String UserReject = listWoHvac.get(position).getUserReject();
        final String RejectDate = listWoHvac.get(position).getRejectDate();
        final String Container = listWoHvac.get(position).getPicture();

        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelWoHvac currentWoHvac = listWoHvac.get(position);
        holder.Wocode.setText("Code: "+Wocode);
        holder.Name.setText("Name: "+Name);
        holder.Location.setText("Floor(th): "+Location);
        holder.Pic.setText("Pic: "+Pic);
        holder.MaintenanceDate.setText("Schedule Date: "+MaintenanceDate);
        holder.Remarks.setText("Remarks: "+Remarks);
        holder.Status.setText("Status: "+Status);
        Picasso.with(context)
                .load(currentWoHvac.getPicture())
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
                                bundle.putString("dataWocode", listWoHvac.get(position).getWocode());
                                bundle.putString("dataName", listWoHvac.get(position).getName());
                                bundle.putString("dataLocation", listWoHvac.get(position).getLocation());
                                bundle.putString("dataPic", listWoHvac.get(position).getPic());
                                bundle.putString("dataMaintenanceDate", listWoHvac.get(position).getMaintenanceDate());
                                bundle.putString("dataRemarks", listWoHvac.get(position).getRemarks());
                                bundle.putString("dataStatus", listWoHvac.get(position).getStatus());
                                bundle.putString("dataPicture", listWoHvac.get(position).getPicture());
                                bundle.putString("dataUserInput", listWoHvac.get(position).getUserInput());
                                bundle.putString("dataWoDate", listWoHvac.get(position).getWoDate());
                                bundle.putString("dataUserStart", listWoHvac.get(position).getUserStart());
                                bundle.putString("dataStartDate", listWoHvac.get(position).getStartDate());
                                bundle.putString("dataUserFinish", listWoHvac.get(position).getUserFinish());
                                bundle.putString("dataFinishDate", listWoHvac.get(position).getFinishDate());
                                bundle.putString("dataUserApproved", listWoHvac.get(position).getUserApproved());
                                bundle.putString("dataApprovedDate", listWoHvac.get(position).getApprovedDate());
                                bundle.putString("dataUserReject", listWoHvac.get(position).getUserReject());
                                bundle.putString("dataRejectDate", listWoHvac.get(position).getRejectDate());
                                bundle.putString("getPrimaryKey", listWoHvac.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), Update1WoHvac.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Bundle bundleFinish = new Bundle();
                                bundleFinish.putString("dataWocode", listWoHvac.get(position).getWocode());
                                bundleFinish.putString("dataName", listWoHvac.get(position).getName());
                                bundleFinish.putString("dataLocation", listWoHvac.get(position).getLocation());
                                bundleFinish.putString("dataPic", listWoHvac.get(position).getPic());
                                bundleFinish.putString("dataMaintenanceDate", listWoHvac.get(position).getMaintenanceDate());
                                bundleFinish.putString("dataRemarks", listWoHvac.get(position).getRemarks());
                                bundleFinish.putString("dataStatus", listWoHvac.get(position).getStatus());
                                bundleFinish.putString("dataPicture", listWoHvac.get(position).getPicture());
                                bundleFinish.putString("dataUserInput", listWoHvac.get(position).getUserInput());
                                bundleFinish.putString("dataWoDate", listWoHvac.get(position).getWoDate());
                                bundleFinish.putString("dataUserStart", listWoHvac.get(position).getUserStart());
                                bundleFinish.putString("dataStartDate", listWoHvac.get(position).getStartDate());
                                bundleFinish.putString("dataUserFinish", listWoHvac.get(position).getUserFinish());
                                bundleFinish.putString("dataFinishDate", listWoHvac.get(position).getFinishDate());
                                bundleFinish.putString("dataUserApproved", listWoHvac.get(position).getUserApproved());
                                bundleFinish.putString("dataApprovedDate", listWoHvac.get(position).getApprovedDate());
                                bundleFinish.putString("dataUserReject", listWoHvac.get(position).getUserReject());
                                bundleFinish.putString("dataRejectDate", listWoHvac.get(position).getRejectDate());
                                bundleFinish.putString("getPrimaryKey", listWoHvac.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), Update2WoHvac.class);
                                intent1.putExtras(bundleFinish);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleApproved = new Bundle();
                                bundleApproved.putString("dataWocode", listWoHvac.get(position).getWocode());
                                bundleApproved.putString("dataName", listWoHvac.get(position).getName());
                                bundleApproved.putString("dataLocation", listWoHvac.get(position).getLocation());
                                bundleApproved.putString("dataPic", listWoHvac.get(position).getPic());
                                bundleApproved.putString("dataMaintenanceDate", listWoHvac.get(position).getMaintenanceDate());
                                bundleApproved.putString("dataRemarks", listWoHvac.get(position).getRemarks());
                                bundleApproved.putString("dataStatus", listWoHvac.get(position).getStatus());
                                bundleApproved.putString("dataPicture", listWoHvac.get(position).getPicture());
                                bundleApproved.putString("dataUserInput",listWoHvac.get(position).getUserInput());
                                bundleApproved.putString("dataWoDate", listWoHvac.get(position).getWoDate());
                                bundleApproved.putString("dataUserStart", listWoHvac.get(position).getUserStart());
                                bundleApproved.putString("dataStartDate", listWoHvac.get(position).getStartDate());
                                bundleApproved.putString("dataUserFinish", listWoHvac.get(position).getUserFinish());
                                bundleApproved.putString("dataFinishDate", listWoHvac.get(position).getFinishDate());
                                bundleApproved.putString("dataUserApproved", listWoHvac.get(position).getUserApproved());
                                bundleApproved.putString("dataApprovedDate", listWoHvac.get(position).getApprovedDate());
                                bundleApproved.putString("dataUserReject", listWoHvac.get(position).getUserReject());
                                bundleApproved.putString("dataRejectDate", listWoHvac.get(position).getRejectDate());
                                bundleApproved.putString("getPrimaryKey", listWoHvac.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), Update3WoHvac.class);
                                intent2.putExtras(bundleApproved);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleReject = new Bundle();
                                bundleReject.putString("dataWocode", listWoHvac.get(position).getWocode());
                                bundleReject.putString("dataName", listWoHvac.get(position).getName());
                                bundleReject.putString("dataLocation", listWoHvac.get(position).getLocation());
                                bundleReject.putString("dataPic", listWoHvac.get(position).getPic());
                                bundleReject.putString("dataMaintenanceDate", listWoHvac.get(position).getMaintenanceDate());
                                bundleReject.putString("dataRemarks", listWoHvac.get(position).getRemarks());
                                bundleReject.putString("dataStatus", listWoHvac.get(position).getStatus());
                                bundleReject.putString("dataPicture", listWoHvac.get(position).getPicture());
                                bundleReject.putString("dataUserInput", listWoHvac.get(position).getUserInput());
                                bundleReject.putString("dataWoDate", listWoHvac.get(position).getWoDate());
                                bundleReject.putString("dataUserStart", listWoHvac.get(position).getUserStart());
                                bundleReject.putString("dataStartDate", listWoHvac.get(position).getStartDate());
                                bundleReject.putString("dataUserFinish", listWoHvac.get(position).getUserFinish());
                                bundleReject.putString("dataFinishDate", listWoHvac.get(position).getFinishDate());
                                bundleReject.putString("dataUserApproved", listWoHvac.get(position).getUserApproved());
                                bundleReject.putString("dataApprovedDate", listWoHvac.get(position).getApprovedDate());
                                bundleReject.putString("dataUserReject", listWoHvac.get(position).getUserReject());
                                bundleReject.putString("dataRejectDate", listWoHvac.get(position).getRejectDate());
                                bundleReject.putString("getPrimaryKey", listWoHvac.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), Update4WoHvac.class);
                                intent3.putExtras(bundleReject);
                                context.startActivity(intent3);
                                break;
                            case 4:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataStatus", listWoHvac.get(position).getStatus());
                                bundleHistory.putString("dataLocation", listWoHvac.get(position).getLocation());
                                bundleHistory.putString("dataName", listWoHvac.get(position).getName());
                                bundleHistory.putString("dataPic", listWoHvac.get(position).getPic());
                                bundleHistory.putString("dataFinishDate", listWoHvac.get(position).getFinishDate());
                                bundleHistory.putString("dataRemarks", listWoHvac.get(position).getRemarks());
                                Intent intent4 = new Intent(view.getContext(), AddHistoryPmHvac.class);
                                intent4.putExtras(bundleHistory);
                                context.startActivity(intent4);
                                break;
                            case 5:
                                //Menggunakan interface untuk mengirim data hvac, yang akan dihapus
                                listener.onDeleteData(listWoHvac.get(position), position);
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
        return listWoHvac.size();
    }

}


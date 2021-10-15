// RecyclerViewAdapter untuk Workorder lift

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
import com.example.projectskripsi170101007.lift.AddHistoryPmLift;
import com.example.projectskripsi170101007.lift.MyListWoLift;
import com.example.projectskripsi170101007.lift.Update1WoLift;
import com.example.projectskripsi170101007.lift.Update2WoLift;
import com.example.projectskripsi170101007.lift.Update3WoLift;
import com.example.projectskripsi170101007.lift.Update4WoLift;
import com.example.projectskripsi170101007.model.ModelWoLift;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter12 extends RecyclerView.Adapter<RecyclerViewAdapter12.ViewHolder>{

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelWoLift data, int position);
    }

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter12.dataListener listener;


    //Deklarasi Variable
    private ArrayList<ModelWoLift> listWoLift;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter12(ArrayList<ModelWoLift> listWoLift, Context context) {
        this.listWoLift = listWoLift;
        this.context = context;
        listener = (MyListWoLift)context;
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
    public RecyclerViewAdapter12.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design2, parent, false);
        return new RecyclerViewAdapter12.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter12.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Wocode = listWoLift.get(position).getWocode();
        final String Name = listWoLift.get(position).getName();
        final String Location = listWoLift.get(position).getLocation();
        final String Pic = listWoLift.get(position).getPic();
        final String MaintenanceDate = listWoLift.get(position).getMaintenanceDate();
        final String Remarks = listWoLift.get(position).getRemarks();
        final String Status = listWoLift.get(position).getStatus();
        final String UserInput = listWoLift.get(position).getUserInput();
        final String WoDate = listWoLift.get(position).getWoDate();
        final String UserStart = listWoLift.get(position).getUserStart();
        final String StartDate = listWoLift.get(position).getStartDate();
        final String UserFinish = listWoLift.get(position).getUserFinish();
        final String FinishDate = listWoLift.get(position).getFinishDate();
        final String UserApproved = listWoLift.get(position).getUserApproved();
        final String ApprovedDate = listWoLift.get(position).getApprovedDate();
        final String UserReject = listWoLift.get(position).getUserReject();
        final String RejectDate = listWoLift.get(position).getRejectDate();
        final String Container = listWoLift.get(position).getPicture();

        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelWoLift currentWoLift = listWoLift.get(position);
        holder.Wocode.setText("Code: "+Wocode);
        holder.Name.setText("Name: "+Name);
        holder.Location.setText("Floor(th): "+Location);
        holder.Pic.setText("Pic: "+Pic);
        holder.MaintenanceDate.setText("Schedule Date: "+MaintenanceDate);
        holder.Remarks.setText("Remarks: "+Remarks);
        holder.Status.setText("Status: "+Status);
        Picasso.with(context)
                .load(currentWoLift.getPicture())
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
                                bundle.putString("dataWocode", listWoLift.get(position).getWocode());
                                bundle.putString("dataName", listWoLift.get(position).getName());
                                bundle.putString("dataLocation", listWoLift.get(position).getLocation());
                                bundle.putString("dataPic", listWoLift.get(position).getPic());
                                bundle.putString("dataMaintenanceDate", listWoLift.get(position).getMaintenanceDate());
                                bundle.putString("dataRemarks", listWoLift.get(position).getRemarks());
                                bundle.putString("dataStatus", listWoLift.get(position).getStatus());
                                bundle.putString("dataPicture", listWoLift.get(position).getPicture());
                                bundle.putString("dataUserInput", listWoLift.get(position).getUserInput());
                                bundle.putString("dataWoDate", listWoLift.get(position).getWoDate());
                                bundle.putString("dataUserStart",listWoLift.get(position).getUserStart());
                                bundle.putString("dataStartDate", listWoLift.get(position).getStartDate());
                                bundle.putString("dataUserFinish", listWoLift.get(position).getUserFinish());
                                bundle.putString("dataFinishDate", listWoLift.get(position).getFinishDate());
                                bundle.putString("dataUserApproved", listWoLift.get(position).getUserApproved());
                                bundle.putString("dataApprovedDate", listWoLift.get(position).getApprovedDate());
                                bundle.putString("dataUserReject", listWoLift.get(position).getUserReject());
                                bundle.putString("dataRejectDate", listWoLift.get(position).getRejectDate());
                                bundle.putString("getPrimaryKey", listWoLift.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), Update1WoLift.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Bundle bundleFinish = new Bundle();
                                bundleFinish.putString("dataWocode", listWoLift.get(position).getWocode());
                                bundleFinish.putString("dataName", listWoLift.get(position).getName());
                                bundleFinish.putString("dataLocation", listWoLift.get(position).getLocation());
                                bundleFinish.putString("dataPic", listWoLift.get(position).getPic());
                                bundleFinish.putString("dataMaintenanceDate", listWoLift.get(position).getMaintenanceDate());
                                bundleFinish.putString("dataRemarks", listWoLift.get(position).getRemarks());
                                bundleFinish.putString("dataStatus", listWoLift.get(position).getStatus());
                                bundleFinish.putString("dataPicture", listWoLift.get(position).getPicture());
                                bundleFinish.putString("dataUserInput", listWoLift.get(position).getUserInput());
                                bundleFinish.putString("dataWoDate", listWoLift.get(position).getWoDate());
                                bundleFinish.putString("dataUserStart", listWoLift.get(position).getUserStart());
                                bundleFinish.putString("dataStartDate", listWoLift.get(position).getStartDate());
                                bundleFinish.putString("dataUserFinish", listWoLift.get(position).getUserFinish());
                                bundleFinish.putString("dataFinishDate", listWoLift.get(position).getFinishDate());
                                bundleFinish.putString("dataUserApproved",listWoLift.get(position).getUserApproved());
                                bundleFinish.putString("dataApprovedDate", listWoLift.get(position).getApprovedDate());
                                bundleFinish.putString("dataUserReject", listWoLift.get(position).getUserReject());
                                bundleFinish.putString("dataRejectDate", listWoLift.get(position).getRejectDate());
                                bundleFinish.putString("getPrimaryKey", listWoLift.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), Update2WoLift.class);
                                intent1.putExtras(bundleFinish);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleApproved = new Bundle();
                                bundleApproved.putString("dataWocode", listWoLift.get(position).getWocode());
                                bundleApproved.putString("dataName", listWoLift.get(position).getName());
                                bundleApproved.putString("dataLocation", listWoLift.get(position).getLocation());
                                bundleApproved.putString("dataPic", listWoLift.get(position).getPic());
                                bundleApproved.putString("dataMaintenanceDate", listWoLift.get(position).getMaintenanceDate());
                                bundleApproved.putString("dataRemarks", listWoLift.get(position).getRemarks());
                                bundleApproved.putString("dataStatus", listWoLift.get(position).getStatus());
                                bundleApproved.putString("dataPicture", listWoLift.get(position).getPicture());
                                bundleApproved.putString("dataUserInput", listWoLift.get(position).getUserInput());
                                bundleApproved.putString("dataWoDate", listWoLift.get(position).getWoDate());
                                bundleApproved.putString("dataUserStart", listWoLift.get(position).getUserStart());
                                bundleApproved.putString("dataStartDate", listWoLift.get(position).getStartDate());
                                bundleApproved.putString("dataUserFinish", listWoLift.get(position).getUserFinish());
                                bundleApproved.putString("dataFinishDate", listWoLift.get(position).getFinishDate());
                                bundleApproved.putString("dataUserApproved", listWoLift.get(position).getUserApproved());
                                bundleApproved.putString("dataApprovedDate", listWoLift.get(position).getApprovedDate());
                                bundleApproved.putString("dataUserReject",listWoLift.get(position).getUserReject());
                                bundleApproved.putString("dataRejectDate", listWoLift.get(position).getRejectDate());
                                bundleApproved.putString("getPrimaryKey", listWoLift.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), Update3WoLift.class);
                                intent2.putExtras(bundleApproved);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleReject = new Bundle();
                                bundleReject.putString("dataWocode", listWoLift.get(position).getWocode());
                                bundleReject.putString("dataName", listWoLift.get(position).getName());
                                bundleReject.putString("dataLocation", listWoLift.get(position).getLocation());
                                bundleReject.putString("dataPic", listWoLift.get(position).getPic());
                                bundleReject.putString("dataMaintenanceDate", listWoLift.get(position).getMaintenanceDate());
                                bundleReject.putString("dataRemarks", listWoLift.get(position).getRemarks());
                                bundleReject.putString("dataStatus", listWoLift.get(position).getStatus());
                                bundleReject.putString("dataPicture", listWoLift.get(position).getPicture());
                                bundleReject.putString("dataUserInput", listWoLift.get(position).getUserInput());
                                bundleReject.putString("dataWoDate", listWoLift.get(position).getWoDate());
                                bundleReject.putString("dataUserStart", listWoLift.get(position).getUserStart());
                                bundleReject.putString("dataStartDate", listWoLift.get(position).getStartDate());
                                bundleReject.putString("dataUserFinish", listWoLift.get(position).getUserFinish());
                                bundleReject.putString("dataFinishDate", listWoLift.get(position).getFinishDate());
                                bundleReject.putString("dataUserApproved", listWoLift.get(position).getUserApproved());
                                bundleReject.putString("dataApprovedDate", listWoLift.get(position).getApprovedDate());
                                bundleReject.putString("dataUserReject", listWoLift.get(position).getUserReject());
                                bundleReject.putString("dataRejectDate", listWoLift.get(position).getRejectDate());
                                bundleReject.putString("getPrimaryKey", listWoLift.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), Update4WoLift.class);
                                intent3.putExtras(bundleReject);
                                context.startActivity(intent3);
                                break;
                            case 4:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataStatus", listWoLift.get(position).getStatus());
                                bundleHistory.putString("dataLocation", listWoLift.get(position).getLocation());
                                bundleHistory.putString("dataName", listWoLift.get(position).getName());
                                bundleHistory.putString("dataPic", listWoLift.get(position).getPic());
                                bundleHistory.putString("dataFinishDate", listWoLift.get(position).getFinishDate());
                                bundleHistory.putString("dataRemarks", listWoLift.get(position).getRemarks());
                                Intent intent4 = new Intent(view.getContext(), AddHistoryPmLift.class);
                                intent4.putExtras(bundleHistory);
                                context.startActivity(intent4);
                                break;
                            case 5:
                                //Menggunakan interface untuk mengirim data lift, yang akan dihapus
                                listener.onDeleteData(listWoLift.get(position), position);
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
        return listWoLift.size();
    }

}


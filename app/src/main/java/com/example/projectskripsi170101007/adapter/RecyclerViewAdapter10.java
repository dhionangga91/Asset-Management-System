// RecyclerViewAdapter untuk Workorder Electronic

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
import com.example.projectskripsi170101007.electronic.AddHistoryPmElectronic;
import com.example.projectskripsi170101007.electronic.MyListWoElectronic;
import com.example.projectskripsi170101007.electronic.Update1WoElectronic;
import com.example.projectskripsi170101007.electronic.Update2WoElectronic;
import com.example.projectskripsi170101007.electronic.Update3WoElectronic;
import com.example.projectskripsi170101007.electronic.Update4WoElectronic;
import com.example.projectskripsi170101007.model.ModelWoElectronic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter10 extends RecyclerView.Adapter<RecyclerViewAdapter10.ViewHolder>{

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelWoElectronic data, int position);
    }

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter10.dataListener listener;


    //Deklarasi Variable
    private ArrayList<ModelWoElectronic> listWoElectronic;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter10(ArrayList<ModelWoElectronic> listWoElectronic, Context context) {
        this.listWoElectronic = listWoElectronic;
        this.context = context;
        listener = (MyListWoElectronic)context;
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
    public RecyclerViewAdapter10.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design2, parent, false);
        return new RecyclerViewAdapter10.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter10.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Wocode = listWoElectronic.get(position).getWocode();
        final String Name = listWoElectronic.get(position).getName();
        final String Location = listWoElectronic.get(position).getLocation();
        final String Pic = listWoElectronic.get(position).getPic();
        final String MaintenanceDate = listWoElectronic.get(position).getMaintenanceDate();
        final String Remarks = listWoElectronic.get(position).getRemarks();
        final String Status = listWoElectronic.get(position).getStatus();
        final String UserInput = listWoElectronic.get(position).getUserInput();
        final String WoDate = listWoElectronic.get(position).getWoDate();
        final String UserStart = listWoElectronic.get(position).getUserStart();
        final String StartDate = listWoElectronic.get(position).getStartDate();
        final String UserFinish = listWoElectronic.get(position).getUserFinish();
        final String FinishDate = listWoElectronic.get(position).getFinishDate();
        final String UserApproved = listWoElectronic.get(position).getUserApproved();
        final String ApprovedDate = listWoElectronic.get(position).getApprovedDate();
        final String UserReject = listWoElectronic.get(position).getUserReject();
        final String RejectDate = listWoElectronic.get(position).getRejectDate();
        final String Container = listWoElectronic.get(position).getPicture();

        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelWoElectronic currentWoElectronic = listWoElectronic.get(position);
        holder.Wocode.setText("Code: "+Wocode);
        holder.Name.setText("Name: "+Name);
        holder.Location.setText("Floor(th): "+Location);
        holder.Pic.setText("Pic: "+Pic);
        holder.MaintenanceDate.setText("Schedule Date: "+MaintenanceDate);
        holder.Remarks.setText("Remarks: "+Remarks);
        holder.Status.setText("Status: "+Status);
        Picasso.with(context)
                .load(currentWoElectronic.getPicture())
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
                                bundle.putString("dataWocode", listWoElectronic.get(position).getWocode());
                                bundle.putString("dataName", listWoElectronic.get(position).getName());
                                bundle.putString("dataLocation", listWoElectronic.get(position).getLocation());
                                bundle.putString("dataPic", listWoElectronic.get(position).getPic());
                                bundle.putString("dataMaintenanceDate", listWoElectronic.get(position).getMaintenanceDate());
                                bundle.putString("dataRemarks", listWoElectronic.get(position).getRemarks());
                                bundle.putString("dataStatus", listWoElectronic.get(position).getStatus());
                                bundle.putString("dataPicture", listWoElectronic.get(position).getPicture());
                                bundle.putString("dataUserInput", listWoElectronic.get(position).getUserInput());
                                bundle.putString("dataWoDate", listWoElectronic.get(position).getWoDate());
                                bundle.putString("dataUserStart", listWoElectronic.get(position).getUserStart());
                                bundle.putString("dataStartDate", listWoElectronic.get(position).getStartDate());
                                bundle.putString("dataUserFinish", listWoElectronic.get(position).getUserFinish());
                                bundle.putString("dataFinishDate", listWoElectronic.get(position).getFinishDate());
                                bundle.putString("dataUserApproved", listWoElectronic.get(position).getUserApproved());
                                bundle.putString("dataApprovedDate", listWoElectronic.get(position).getApprovedDate());
                                bundle.putString("dataUserReject", listWoElectronic.get(position).getUserReject());
                                bundle.putString("dataRejectDate", listWoElectronic.get(position).getRejectDate());
                                bundle.putString("getPrimaryKey", listWoElectronic.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), Update1WoElectronic.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Bundle bundleFinish = new Bundle();
                                bundleFinish.putString("dataWocode", listWoElectronic.get(position).getWocode());
                                bundleFinish.putString("dataName", listWoElectronic.get(position).getName());
                                bundleFinish.putString("dataLocation", listWoElectronic.get(position).getLocation());
                                bundleFinish.putString("dataPic", listWoElectronic.get(position).getPic());
                                bundleFinish.putString("dataMaintenanceDate", listWoElectronic.get(position).getMaintenanceDate());
                                bundleFinish.putString("dataRemarks", listWoElectronic.get(position).getRemarks());
                                bundleFinish.putString("dataStatus", listWoElectronic.get(position).getStatus());
                                bundleFinish.putString("dataPicture", listWoElectronic.get(position).getPicture());
                                bundleFinish.putString("dataUserInput", listWoElectronic.get(position).getUserInput());
                                bundleFinish.putString("dataWoDate", listWoElectronic.get(position).getWoDate());
                                bundleFinish.putString("dataUserStart", listWoElectronic.get(position).getUserStart());
                                bundleFinish.putString("dataStartDate", listWoElectronic.get(position).getStartDate());
                                bundleFinish.putString("dataUserFinish", listWoElectronic.get(position).getUserFinish());
                                bundleFinish.putString("dataFinishDate", listWoElectronic.get(position).getFinishDate());
                                bundleFinish.putString("dataUserApproved", listWoElectronic.get(position).getUserApproved());
                                bundleFinish.putString("dataApprovedDate", listWoElectronic.get(position).getApprovedDate());
                                bundleFinish.putString("dataUserReject", listWoElectronic.get(position).getUserReject());
                                bundleFinish.putString("dataRejectDate", listWoElectronic.get(position).getRejectDate());
                                bundleFinish.putString("getPrimaryKey", listWoElectronic.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), Update2WoElectronic.class);
                                intent1.putExtras(bundleFinish);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleApproved = new Bundle();
                                bundleApproved.putString("dataWocode", listWoElectronic.get(position).getWocode());
                                bundleApproved.putString("dataName", listWoElectronic.get(position).getName());
                                bundleApproved.putString("dataLocation", listWoElectronic.get(position).getLocation());
                                bundleApproved.putString("dataPic", listWoElectronic.get(position).getPic());
                                bundleApproved.putString("dataMaintenanceDate", listWoElectronic.get(position).getMaintenanceDate());
                                bundleApproved.putString("dataRemarks", listWoElectronic.get(position).getRemarks());
                                bundleApproved.putString("dataStatus", listWoElectronic.get(position).getStatus());
                                bundleApproved.putString("dataPicture", listWoElectronic.get(position).getPicture());
                                bundleApproved.putString("dataUserInput", listWoElectronic.get(position).getUserInput());
                                bundleApproved.putString("dataWoDate", listWoElectronic.get(position).getWoDate());
                                bundleApproved.putString("dataUserStart", listWoElectronic.get(position).getUserStart());
                                bundleApproved.putString("dataStartDate", listWoElectronic.get(position).getStartDate());
                                bundleApproved.putString("dataUserFinish", listWoElectronic.get(position).getUserFinish());
                                bundleApproved.putString("dataFinishDate", listWoElectronic.get(position).getFinishDate());
                                bundleApproved.putString("dataUserApproved", listWoElectronic.get(position).getUserApproved());
                                bundleApproved.putString("dataApprovedDate", listWoElectronic.get(position).getApprovedDate());
                                bundleApproved.putString("dataUserReject", listWoElectronic.get(position).getUserReject());
                                bundleApproved.putString("dataRejectDate", listWoElectronic.get(position).getRejectDate());
                                bundleApproved.putString("getPrimaryKey", listWoElectronic.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), Update3WoElectronic.class);
                                intent2.putExtras(bundleApproved);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleReject = new Bundle();
                                bundleReject.putString("dataWocode", listWoElectronic.get(position).getWocode());
                                bundleReject.putString("dataName", listWoElectronic.get(position).getName());
                                bundleReject.putString("dataLocation", listWoElectronic.get(position).getLocation());
                                bundleReject.putString("dataPic", listWoElectronic.get(position).getPic());
                                bundleReject.putString("dataMaintenanceDate", listWoElectronic.get(position).getMaintenanceDate());
                                bundleReject.putString("dataRemarks", listWoElectronic.get(position).getRemarks());
                                bundleReject.putString("dataStatus", listWoElectronic.get(position).getStatus());
                                bundleReject.putString("dataPicture", listWoElectronic.get(position).getPicture());
                                bundleReject.putString("dataUserInput", listWoElectronic.get(position).getUserInput());
                                bundleReject.putString("dataWoDate", listWoElectronic.get(position).getWoDate());
                                bundleReject.putString("dataUserStart", listWoElectronic.get(position).getUserStart());
                                bundleReject.putString("dataStartDate", listWoElectronic.get(position).getStartDate());
                                bundleReject.putString("dataUserFinish", listWoElectronic.get(position).getUserFinish());
                                bundleReject.putString("dataFinishDate", listWoElectronic.get(position).getFinishDate());
                                bundleReject.putString("dataUserApproved", listWoElectronic.get(position).getUserApproved());
                                bundleReject.putString("dataApprovedDate", listWoElectronic.get(position).getApprovedDate());
                                bundleReject.putString("dataUserReject", listWoElectronic.get(position).getUserReject());
                                bundleReject.putString("dataRejectDate", listWoElectronic.get(position).getRejectDate());
                                bundleReject.putString("getPrimaryKey", listWoElectronic.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), Update4WoElectronic.class);
                                intent3.putExtras(bundleReject);
                                context.startActivity(intent3);
                                break;
                            case 4:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataStatus", listWoElectronic.get(position).getStatus());
                                bundleHistory.putString("dataLocation", listWoElectronic.get(position).getLocation());
                                bundleHistory.putString("dataName", listWoElectronic.get(position).getName());
                                bundleHistory.putString("dataPic", listWoElectronic.get(position).getPic());
                                bundleHistory.putString("dataFinishDate", listWoElectronic.get(position).getFinishDate());
                                bundleHistory.putString("dataRemarks", listWoElectronic.get(position).getRemarks());
                                Intent intent4 = new Intent(view.getContext(), AddHistoryPmElectronic.class);
                                intent4.putExtras(bundleHistory);
                                context.startActivity(intent4);
                                break;
                            case 5:
                                //Menggunakan interface untuk mengirim data electrionic, yang akan dihapus
                                listener.onDeleteData(listWoElectronic.get(position), position);
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
        return listWoElectronic.size();
    }

}


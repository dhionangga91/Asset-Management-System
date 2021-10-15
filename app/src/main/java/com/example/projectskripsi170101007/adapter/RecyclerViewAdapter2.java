//RecyclerViewAdapter Untuk Workorder Electrical

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

import com.example.projectskripsi170101007.electrical.AddHistoryPmElectrical;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.electrical.MyListWoElectrical;
import com.example.projectskripsi170101007.electrical.Update1WoElectrical;
import com.example.projectskripsi170101007.electrical.Update2WoElectrical;
import com.example.projectskripsi170101007.electrical.Update3WoElectrical;
import com.example.projectskripsi170101007.electrical.Update4WoElectrical;
import com.example.projectskripsi170101007.model.ModelWoElectrical;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelWoElectrical data, int position);
    }

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter2.dataListener listener;


    //Deklarasi Variable
    private ArrayList<ModelWoElectrical> listWoElectrical;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter2(ArrayList<ModelWoElectrical> listWoElectrical, Context context) {
        this.listWoElectrical = listWoElectrical;
        this.context = context;
        listener = (MyListWoElectrical)context;
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
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design2, parent, false);
        return new RecyclerViewAdapter2.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter2.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Wocode = listWoElectrical.get(position).getWocode();
        final String Name = listWoElectrical.get(position).getName();
        final String Location = listWoElectrical.get(position).getLocation();
        final String Pic = listWoElectrical.get(position).getPic();
        final String MaintenanceDate = listWoElectrical.get(position).getMaintenanceDate();
        final String Remarks = listWoElectrical.get(position).getRemarks();
        final String Status = listWoElectrical.get(position).getStatus();
        final String UserInput = listWoElectrical.get(position).getUserInput();
        final String WoDate = listWoElectrical.get(position).getWoDate();
        final String UserStart = listWoElectrical.get(position).getUserStart();
        final String StartDate = listWoElectrical.get(position).getStartDate();
        final String UserFinish = listWoElectrical.get(position).getUserFinish();
        final String FinishDate = listWoElectrical.get(position).getFinishDate();
        final String UserApproved = listWoElectrical.get(position).getUserApproved();
        final String ApprovedDate = listWoElectrical.get(position).getApprovedDate();
        final String UserReject = listWoElectrical.get(position).getUserReject();
        final String RejectDate = listWoElectrical.get(position).getRejectDate();
        final String Container = listWoElectrical.get(position).getPicture();

        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelWoElectrical currentWoElectrical = listWoElectrical.get(position);
        holder.Wocode.setText("Code: "+Wocode);
        holder.Name.setText("Name: "+Name);
        holder.Location.setText("Floor(th): "+Location);
        holder.Pic.setText("Pic: "+Pic);
        holder.MaintenanceDate.setText("Schedule Date: "+MaintenanceDate);
        holder.Remarks.setText("Remarks: "+Remarks);
        holder.Status.setText("Status: "+Status);
        Picasso.with(context)
                .load(currentWoElectrical.getPicture())
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
                                bundle.putString("dataWocode", listWoElectrical.get(position).getWocode());
                                bundle.putString("dataName", listWoElectrical.get(position).getName());
                                bundle.putString("dataLocation", listWoElectrical.get(position).getLocation());
                                bundle.putString("dataPic", listWoElectrical.get(position).getPic());
                                bundle.putString("dataMaintenanceDate", listWoElectrical.get(position).getMaintenanceDate());
                                bundle.putString("dataRemarks", listWoElectrical.get(position).getRemarks());
                                bundle.putString("dataStatus", listWoElectrical.get(position).getStatus());
                                bundle.putString("dataPicture", listWoElectrical.get(position).getPicture());
                                bundle.putString("dataUserInput", listWoElectrical.get(position).getUserInput());
                                bundle.putString("dataWoDate", listWoElectrical.get(position).getWoDate());
                                bundle.putString("dataUserStart", listWoElectrical.get(position).getUserStart());
                                bundle.putString("dataStartDate", listWoElectrical.get(position).getStartDate());
                                bundle.putString("dataUserFinish", listWoElectrical.get(position).getUserFinish());
                                bundle.putString("dataFinishDate", listWoElectrical.get(position).getFinishDate());
                                bundle.putString("dataUserApproved", listWoElectrical.get(position).getUserApproved());
                                bundle.putString("dataApprovedDate", listWoElectrical.get(position).getApprovedDate());
                                bundle.putString("dataUserReject", listWoElectrical.get(position).getUserReject());
                                bundle.putString("dataRejectDate", listWoElectrical.get(position).getRejectDate());
                                bundle.putString("getPrimaryKey", listWoElectrical.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), Update1WoElectrical.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Bundle bundleFinish = new Bundle();
                                bundleFinish.putString("dataWocode", listWoElectrical.get(position).getWocode());
                                bundleFinish.putString("dataName", listWoElectrical.get(position).getName());
                                bundleFinish.putString("dataLocation", listWoElectrical.get(position).getLocation());
                                bundleFinish.putString("dataPic", listWoElectrical.get(position).getPic());
                                bundleFinish.putString("dataMaintenanceDate", listWoElectrical.get(position).getMaintenanceDate());
                                bundleFinish.putString("dataRemarks", listWoElectrical.get(position).getRemarks());
                                bundleFinish.putString("dataStatus", listWoElectrical.get(position).getStatus());
                                bundleFinish.putString("dataPicture", listWoElectrical.get(position).getPicture());
                                bundleFinish.putString("dataUserInput", listWoElectrical.get(position).getUserInput());
                                bundleFinish.putString("dataWoDate", listWoElectrical.get(position).getWoDate());
                                bundleFinish.putString("dataUserStart", listWoElectrical.get(position).getUserStart());
                                bundleFinish.putString("dataStartDate", listWoElectrical.get(position).getStartDate());
                                bundleFinish.putString("dataUserFinish", listWoElectrical.get(position).getUserFinish());
                                bundleFinish.putString("dataFinishDate", listWoElectrical.get(position).getFinishDate());
                                bundleFinish.putString("dataUserApproved", listWoElectrical.get(position).getUserApproved());
                                bundleFinish.putString("dataApprovedDate", listWoElectrical.get(position).getApprovedDate());
                                bundleFinish.putString("dataUserReject", listWoElectrical.get(position).getUserReject());
                                bundleFinish.putString("dataRejectDate", listWoElectrical.get(position).getRejectDate());
                                bundleFinish.putString("getPrimaryKey", listWoElectrical.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), Update2WoElectrical.class);
                                intent1.putExtras(bundleFinish);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleApproved = new Bundle();
                                bundleApproved.putString("dataWocode", listWoElectrical.get(position).getWocode());
                                bundleApproved.putString("dataName", listWoElectrical.get(position).getName());
                                bundleApproved.putString("dataLocation", listWoElectrical.get(position).getLocation());
                                bundleApproved.putString("dataPic", listWoElectrical.get(position).getPic());
                                bundleApproved.putString("dataMaintenanceDate", listWoElectrical.get(position).getMaintenanceDate());
                                bundleApproved.putString("dataRemarks", listWoElectrical.get(position).getRemarks());
                                bundleApproved.putString("dataStatus", listWoElectrical.get(position).getStatus());
                                bundleApproved.putString("dataPicture", listWoElectrical.get(position).getPicture());
                                bundleApproved.putString("dataUserInput", listWoElectrical.get(position).getUserInput());
                                bundleApproved.putString("dataWoDate", listWoElectrical.get(position).getWoDate());
                                bundleApproved.putString("dataUserStart", listWoElectrical.get(position).getUserStart());
                                bundleApproved.putString("dataStartDate", listWoElectrical.get(position).getStartDate());
                                bundleApproved.putString("dataUserFinish", listWoElectrical.get(position).getUserFinish());
                                bundleApproved.putString("dataFinishDate", listWoElectrical.get(position).getFinishDate());
                                bundleApproved.putString("dataUserApproved", listWoElectrical.get(position).getUserApproved());
                                bundleApproved.putString("dataApprovedDate", listWoElectrical.get(position).getApprovedDate());
                                bundleApproved.putString("dataUserReject", listWoElectrical.get(position).getUserReject());
                                bundleApproved.putString("dataRejectDate", listWoElectrical.get(position).getRejectDate());
                                bundleApproved.putString("getPrimaryKey", listWoElectrical.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), Update3WoElectrical.class);
                                intent2.putExtras(bundleApproved);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleReject = new Bundle();
                                bundleReject.putString("dataWocode", listWoElectrical.get(position).getWocode());
                                bundleReject.putString("dataName", listWoElectrical.get(position).getName());
                                bundleReject.putString("dataLocation", listWoElectrical.get(position).getLocation());
                                bundleReject.putString("dataPic", listWoElectrical.get(position).getPic());
                                bundleReject.putString("dataMaintenanceDate", listWoElectrical.get(position).getMaintenanceDate());
                                bundleReject.putString("dataRemarks", listWoElectrical.get(position).getRemarks());
                                bundleReject.putString("dataStatus", listWoElectrical.get(position).getStatus());
                                bundleReject.putString("dataPicture", listWoElectrical.get(position).getPicture());
                                bundleReject.putString("dataUserInput", listWoElectrical.get(position).getUserInput());
                                bundleReject.putString("dataWoDate", listWoElectrical.get(position).getWoDate());
                                bundleReject.putString("dataUserStart", listWoElectrical.get(position).getUserStart());
                                bundleReject.putString("dataStartDate", listWoElectrical.get(position).getStartDate());
                                bundleReject.putString("dataUserFinish", listWoElectrical.get(position).getUserFinish());
                                bundleReject.putString("dataFinishDate", listWoElectrical.get(position).getFinishDate());
                                bundleReject.putString("dataUserApproved", listWoElectrical.get(position).getUserApproved());
                                bundleReject.putString("dataApprovedDate", listWoElectrical.get(position).getApprovedDate());
                                bundleReject.putString("dataUserReject", listWoElectrical.get(position).getUserReject());
                                bundleReject.putString("dataRejectDate", listWoElectrical.get(position).getRejectDate());
                                bundleReject.putString("getPrimaryKey", listWoElectrical.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), Update4WoElectrical.class);
                                intent3.putExtras(bundleReject);
                                context.startActivity(intent3);
                                break;
                            case 4:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataStatus", listWoElectrical.get(position).getStatus());
                                bundleHistory.putString("dataLocation", listWoElectrical.get(position).getLocation());
                                bundleHistory.putString("dataName", listWoElectrical.get(position).getName());
                                bundleHistory.putString("dataPic", listWoElectrical.get(position).getPic());
                                bundleHistory.putString("dataFinishDate", listWoElectrical.get(position).getFinishDate());
                                bundleHistory.putString("dataRemarks", listWoElectrical.get(position).getRemarks());
                                Intent intent4 = new Intent(view.getContext(), AddHistoryPmElectrical.class);
                                intent4.putExtras(bundleHistory);
                                context.startActivity(intent4);
                                break;
                            case 5:
                                //Menggunakan interface untuk mengirim data electrical, yang akan dihapus
                                listener.onDeleteData(listWoElectrical.get(position), position);
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
        return listWoElectrical.size();
    }

}


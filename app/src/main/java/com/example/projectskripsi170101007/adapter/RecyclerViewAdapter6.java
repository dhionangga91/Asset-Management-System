//RecyclerViewAdapter Untuk Workorder Plumbing

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
import com.example.projectskripsi170101007.model.ModelWoPlumbing;
import com.example.projectskripsi170101007.plumbing.AddHistoryPmPlumbing;
import com.example.projectskripsi170101007.plumbing.MyListWoPlumbing;
import com.example.projectskripsi170101007.plumbing.Update1WoPlumbing;
import com.example.projectskripsi170101007.plumbing.Update2WoPlumbing;
import com.example.projectskripsi170101007.plumbing.Update3WoPlumbing;
import com.example.projectskripsi170101007.plumbing.Update4WoPlumbing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter6 extends RecyclerView.Adapter<RecyclerViewAdapter6.ViewHolder>{

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(ModelWoPlumbing data, int position);
    }

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter6.dataListener listener;


    //Deklarasi Variable
    private ArrayList<ModelWoPlumbing> listWoPlumbing;
    private Context context;


    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter6(ArrayList<ModelWoPlumbing> listWoPlumbing, Context context) {
        this.listWoPlumbing = listWoPlumbing;
        this.context = context;
        listener = (MyListWoPlumbing)context;
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
    public RecyclerViewAdapter6.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_design2, parent, false);
        return new RecyclerViewAdapter6.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter6.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Wocode = listWoPlumbing.get(position).getWocode();
        final String Name = listWoPlumbing.get(position).getName();
        final String Location = listWoPlumbing.get(position).getLocation();
        final String Pic = listWoPlumbing.get(position).getPic();
        final String MaintenanceDate = listWoPlumbing.get(position).getMaintenanceDate();
        final String Remarks = listWoPlumbing.get(position).getRemarks();
        final String Status = listWoPlumbing.get(position).getStatus();
        final String UserInput = listWoPlumbing.get(position).getUserInput();
        final String WoDate = listWoPlumbing.get(position).getWoDate();
        final String UserStart = listWoPlumbing.get(position).getUserStart();
        final String StartDate = listWoPlumbing.get(position).getStartDate();
        final String UserFinish = listWoPlumbing.get(position).getUserFinish();
        final String FinishDate = listWoPlumbing.get(position).getFinishDate();
        final String UserApproved = listWoPlumbing.get(position).getUserApproved();
        final String ApprovedDate = listWoPlumbing.get(position).getApprovedDate();
        final String UserReject = listWoPlumbing.get(position).getUserReject();
        final String RejectDate = listWoPlumbing.get(position).getRejectDate();
        final String Container = listWoPlumbing.get(position).getPicture();

        ///Memasukan Nilai/Value kedalam View (All TextView)
        ModelWoPlumbing currentWoPlumbing = listWoPlumbing.get(position);
        holder.Wocode.setText("Code: "+Wocode);
        holder.Name.setText("Name: "+Name);
        holder.Location.setText("Floor(th): "+Location);
        holder.Pic.setText("Pic: "+Pic);
        holder.MaintenanceDate.setText("Schedule Date: "+MaintenanceDate);
        holder.Remarks.setText("Remarks: "+Remarks);
        holder.Status.setText("Status: "+Status);
        Picasso.with(context)
                .load(currentWoPlumbing.getPicture())
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
                                bundle.putString("dataWocode", listWoPlumbing.get(position).getWocode());
                                bundle.putString("dataName", listWoPlumbing.get(position).getName());
                                bundle.putString("dataLocation", listWoPlumbing.get(position).getLocation());
                                bundle.putString("dataPic", listWoPlumbing.get(position).getPic());
                                bundle.putString("dataMaintenanceDate", listWoPlumbing.get(position).getMaintenanceDate());
                                bundle.putString("dataRemarks", listWoPlumbing.get(position).getRemarks());
                                bundle.putString("dataStatus", listWoPlumbing.get(position).getStatus());
                                bundle.putString("dataPicture", listWoPlumbing.get(position).getPicture());
                                bundle.putString("dataUserInput", listWoPlumbing.get(position).getUserInput());
                                bundle.putString("dataWoDate", listWoPlumbing.get(position).getWoDate());
                                bundle.putString("dataUserStart", listWoPlumbing.get(position).getUserStart());
                                bundle.putString("dataStartDate", listWoPlumbing.get(position).getStartDate());
                                bundle.putString("dataUserFinish",listWoPlumbing.get(position).getUserFinish());
                                bundle.putString("dataFinishDate", listWoPlumbing.get(position).getFinishDate());
                                bundle.putString("dataUserApproved", listWoPlumbing.get(position).getUserApproved());
                                bundle.putString("dataApprovedDate", listWoPlumbing.get(position).getApprovedDate());
                                bundle.putString("dataUserReject", listWoPlumbing.get(position).getUserReject());
                                bundle.putString("dataRejectDate", listWoPlumbing.get(position).getRejectDate());
                                bundle.putString("getPrimaryKey", listWoPlumbing.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), Update1WoPlumbing.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Bundle bundleFinish = new Bundle();
                                bundleFinish.putString("dataWocode", listWoPlumbing.get(position).getWocode());
                                bundleFinish.putString("dataName", listWoPlumbing.get(position).getName());
                                bundleFinish.putString("dataLocation", listWoPlumbing.get(position).getLocation());
                                bundleFinish.putString("dataPic", listWoPlumbing.get(position).getPic());
                                bundleFinish.putString("dataMaintenanceDate", listWoPlumbing.get(position).getMaintenanceDate());
                                bundleFinish.putString("dataRemarks", listWoPlumbing.get(position).getRemarks());
                                bundleFinish.putString("dataStatus", listWoPlumbing.get(position).getStatus());
                                bundleFinish.putString("dataPicture", listWoPlumbing.get(position).getPicture());
                                bundleFinish.putString("dataUserInput", listWoPlumbing.get(position).getUserInput());
                                bundleFinish.putString("dataWoDate", listWoPlumbing.get(position).getWoDate());
                                bundleFinish.putString("dataUserStart", listWoPlumbing.get(position).getUserStart());
                                bundleFinish.putString("dataStartDate", listWoPlumbing.get(position).getStartDate());
                                bundleFinish.putString("dataUserFinish", listWoPlumbing.get(position).getUserFinish());
                                bundleFinish.putString("dataFinishDate",listWoPlumbing.get(position).getFinishDate());
                                bundleFinish.putString("dataUserApproved", listWoPlumbing.get(position).getUserApproved());
                                bundleFinish.putString("dataApprovedDate", listWoPlumbing.get(position).getApprovedDate());
                                bundleFinish.putString("dataUserReject", listWoPlumbing.get(position).getUserReject());
                                bundleFinish.putString("dataRejectDate", listWoPlumbing.get(position).getRejectDate());
                                bundleFinish.putString("getPrimaryKey", listWoPlumbing.get(position).getKey());
                                Intent intent1 = new Intent(view.getContext(), Update2WoPlumbing.class);
                                intent1.putExtras(bundleFinish);
                                context.startActivity(intent1);
                                break;
                            case 2:
                                Bundle bundleApproved = new Bundle();
                                bundleApproved.putString("dataWocode", listWoPlumbing.get(position).getWocode());
                                bundleApproved.putString("dataName", listWoPlumbing.get(position).getName());
                                bundleApproved.putString("dataLocation", listWoPlumbing.get(position).getLocation());
                                bundleApproved.putString("dataPic", listWoPlumbing.get(position).getPic());
                                bundleApproved.putString("dataMaintenanceDate", listWoPlumbing.get(position).getMaintenanceDate());
                                bundleApproved.putString("dataRemarks", listWoPlumbing.get(position).getRemarks());
                                bundleApproved.putString("dataStatus", listWoPlumbing.get(position).getStatus());
                                bundleApproved.putString("dataPicture", listWoPlumbing.get(position).getPicture());
                                bundleApproved.putString("dataUserInput", listWoPlumbing.get(position).getUserInput());
                                bundleApproved.putString("dataWoDate", listWoPlumbing.get(position).getWoDate());
                                bundleApproved.putString("dataUserStart", listWoPlumbing.get(position).getUserStart());
                                bundleApproved.putString("dataStartDate", listWoPlumbing.get(position).getStartDate());
                                bundleApproved.putString("dataUserFinish", listWoPlumbing.get(position).getUserFinish());
                                bundleApproved.putString("dataFinishDate", listWoPlumbing.get(position).getFinishDate());
                                bundleApproved.putString("dataUserApproved", listWoPlumbing.get(position).getUserApproved());
                                bundleApproved.putString("dataApprovedDate", listWoPlumbing.get(position).getApprovedDate());
                                bundleApproved.putString("dataUserReject", listWoPlumbing.get(position).getUserReject());
                                bundleApproved.putString("dataRejectDate", listWoPlumbing.get(position).getRejectDate());
                                bundleApproved.putString("getPrimaryKey", listWoPlumbing.get(position).getKey());
                                Intent intent2 = new Intent(view.getContext(), Update3WoPlumbing.class);
                                intent2.putExtras(bundleApproved);
                                context.startActivity(intent2);
                                break;
                            case 3:
                                Bundle bundleReject = new Bundle();
                                bundleReject.putString("dataWocode", listWoPlumbing.get(position).getWocode());
                                bundleReject.putString("dataName", listWoPlumbing.get(position).getName());
                                bundleReject.putString("dataLocation", listWoPlumbing.get(position).getLocation());
                                bundleReject.putString("dataPic", listWoPlumbing.get(position).getPic());
                                bundleReject.putString("dataMaintenanceDate", listWoPlumbing.get(position).getMaintenanceDate());
                                bundleReject.putString("dataRemarks", listWoPlumbing.get(position).getRemarks());
                                bundleReject.putString("dataStatus", listWoPlumbing.get(position).getStatus());
                                bundleReject.putString("dataPicture", listWoPlumbing.get(position).getPicture());
                                bundleReject.putString("dataUserInput",listWoPlumbing.get(position).getUserInput());
                                bundleReject.putString("dataWoDate", listWoPlumbing.get(position).getWoDate());
                                bundleReject.putString("dataUserStart", listWoPlumbing.get(position).getUserStart());
                                bundleReject.putString("dataStartDate", listWoPlumbing.get(position).getStartDate());
                                bundleReject.putString("dataUserFinish", listWoPlumbing.get(position).getUserFinish());
                                bundleReject.putString("dataFinishDate", listWoPlumbing.get(position).getFinishDate());
                                bundleReject.putString("dataUserApproved", listWoPlumbing.get(position).getUserApproved());
                                bundleReject.putString("dataApprovedDate", listWoPlumbing.get(position).getApprovedDate());
                                bundleReject.putString("dataUserReject", listWoPlumbing.get(position).getUserReject());
                                bundleReject.putString("dataRejectDate", listWoPlumbing.get(position).getRejectDate());
                                bundleReject.putString("getPrimaryKey", listWoPlumbing.get(position).getKey());
                                Intent intent3 = new Intent(view.getContext(), Update4WoPlumbing.class);
                                intent3.putExtras(bundleReject);
                                context.startActivity(intent3);
                                break;
                            case 4:
                                Bundle bundleHistory = new Bundle();
                                bundleHistory.putString("dataStatus", listWoPlumbing.get(position).getStatus());
                                bundleHistory.putString("dataLocation", listWoPlumbing.get(position).getLocation());
                                bundleHistory.putString("dataName", listWoPlumbing.get(position).getName());
                                bundleHistory.putString("dataPic", listWoPlumbing.get(position).getPic());
                                bundleHistory.putString("dataFinishDate", listWoPlumbing.get(position).getFinishDate());
                                bundleHistory.putString("dataRemarks", listWoPlumbing.get(position).getRemarks());
                                Intent intent4 = new Intent(view.getContext(), AddHistoryPmPlumbing.class);
                                intent4.putExtras(bundleHistory);
                                context.startActivity(intent4);
                                break;
                            case 5:
                                //Menggunakan interface untuk mengirim data plumbing, yang akan dihapus
                                listener.onDeleteData(listWoPlumbing.get(position), position);
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
        return listWoPlumbing.size();
    }

}


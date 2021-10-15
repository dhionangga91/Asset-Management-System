package com.example.projectskripsi170101007.electrical;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectskripsi170101007.MainActivity;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.adapter.RecyclerViewAdapter2;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelWoElectrical;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyListWoElectrical extends AppCompatActivity implements RecyclerViewAdapter2.dataListener {
    //Deklarasi Variable untuk RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseRecyclerOptions<ModelWoElectrical> options;
    private FloatingActionButton Home;
    private String AuthAdmin = "admin@myaset.com" , GetUserID ;
    private TextView CurrentUser,ImageURL;
    private ImageButton All,Waitfor,Onprogress,Finish,Approved,Reject;

    //Deklarasi Variable Database Reference dan ArrayList dengan Parameter Class Model kita.
    private DatabaseReference mDatabase;
    private ArrayList<ModelWoElectrical> dataWoElectrical;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_wo_electrical);

        //Action FLoatingButton
        Home = findViewById(R.id.btnhome);
        CurrentUser = findViewById(R.id.authadmin);
        ImageURL = findViewById(R.id.image);
        All = findViewById(R.id.woall);
        Waitfor = findViewById(R.id.waitfor);
        Onprogress = findViewById(R.id.onprogress);
        Finish = findViewById(R.id.finish);
        Approved = findViewById(R.id.approved);
        Reject = findViewById(R.id.reject);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyListWoElectrical.this, MainActivity.class);
                startActivity(intent);
            }

        });


        recyclerView = findViewById(R.id.datalist);
        getSupportActionBar().setTitle("WorkOrder Electrical");
        auth = FirebaseAuth.getInstance();
        cekKoneksi();


    }

    private void cekKoneksi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            MyRecyclerView();
            GetData();
            GetAdmin();

            //  Mengatur setelah opsi untuk FirebaseRecyclerAdapter
            options = new FirebaseRecyclerOptions.Builder<ModelWoElectrical>()
                    //   Referensi Database yang akan digunakan beserta data Modelnya
                    .setQuery(mDatabase.child("Aset").child("Workorder").child("electrical"), ModelWoElectrical.class)
                    .setLifecycleOwner(this) //Untuk menangani perubahan siklus hidup pada Activity/Fragment
                    .build();

            All.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetData();
                }
            });

            Waitfor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataWaitfor();
                }
            });

            Onprogress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataOnProgress();
                }
            });

            Finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataFinish();
                }
            });

            Approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataApproved();
                }
            });

            Reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataReject();
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void GetDataReject() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("Aset").child("Workorder").child("electrical").orderByChild("status").equalTo("Reject")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataWoElectrical = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek electrical
                            ModelWoElectrical modelWoElectrical = snapshot.getValue(ModelWoElectrical.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            modelWoElectrical.setKey(snapshot.getKey());
                            dataWoElectrical.add(modelWoElectrical);
                            ImageURL.setText(modelWoElectrical.getPicture());
                        }

                        //Inisialisasi Adapter dan data Wo dalam bentuk Array
                        adapter = new RecyclerViewAdapter2(dataWoElectrical, MyListWoElectrical.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        // Toast.makeText(getApplicationContext(),"Downloading failed", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    private void GetDataApproved() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("Aset").child("Workorder").child("electrical").orderByChild("status").equalTo("Approved")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataWoElectrical = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek electrical
                            ModelWoElectrical modelWoElectrical = snapshot.getValue(ModelWoElectrical.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            modelWoElectrical.setKey(snapshot.getKey());
                            dataWoElectrical.add(modelWoElectrical);
                            ImageURL.setText(modelWoElectrical.getPicture());
                        }

                        //Inisialisasi Adapter dan data Wo dalam bentuk Array
                        adapter = new RecyclerViewAdapter2(dataWoElectrical, MyListWoElectrical.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        // Toast.makeText(getApplicationContext(),"Downloading failed", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    private void GetDataFinish() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("Aset").child("Workorder").child("electrical").orderByChild("status").equalTo("Finish")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataWoElectrical = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek electrical
                            ModelWoElectrical modelWoElectrical = snapshot.getValue(ModelWoElectrical.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            modelWoElectrical.setKey(snapshot.getKey());
                            dataWoElectrical.add(modelWoElectrical);
                            ImageURL.setText(modelWoElectrical.getPicture());
                        }

                        //Inisialisasi Adapter dan data Wo dalam bentuk Array
                        adapter = new RecyclerViewAdapter2(dataWoElectrical, MyListWoElectrical.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        // Toast.makeText(getApplicationContext(),"Downloading failed", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    private void GetDataWaitfor() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("Aset").child("Workorder").child("electrical").orderByChild("status").equalTo("In Queue")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataWoElectrical = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek electrical
                            ModelWoElectrical modelWoElectrical = snapshot.getValue(ModelWoElectrical.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            modelWoElectrical.setKey(snapshot.getKey());
                            dataWoElectrical.add(modelWoElectrical);
                            ImageURL.setText(modelWoElectrical.getPicture());
                        }

                        //Inisialisasi Adapter dan data Wo dalam bentuk Array
                        adapter = new RecyclerViewAdapter2(dataWoElectrical, MyListWoElectrical.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        // Toast.makeText(getApplicationContext(),"Downloading failed", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    private void GetDataOnProgress() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("Aset").child("Workorder").child("electrical").orderByChild("status").equalTo("On Progress")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataWoElectrical = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek electrical
                            ModelWoElectrical modelWoElectrical = snapshot.getValue(ModelWoElectrical.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            modelWoElectrical.setKey(snapshot.getKey());
                            dataWoElectrical.add(modelWoElectrical);
                            ImageURL.setText(modelWoElectrical.getPicture());
                        }

                        //Inisialisasi Adapter dan data Wo dalam bentuk Array
                        adapter = new RecyclerViewAdapter2(dataWoElectrical, MyListWoElectrical.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        // Toast.makeText(getApplicationContext(),"Downloading failed", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    private void GetAdmin() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("User").child(GetUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                com.example.projectskripsi170101007.model.Admin admin = snapshot.getValue(Admin.class);
                System.out.println(admin.getEmail());
                CurrentUser.setText(admin.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    //Methode yang berisi kumpulan baris kode untuk mengatur RecyclerView
    private void MyRecyclerView(){
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    //Berisi baris kode untuk mengambil data dari Database dan menampilkannya kedalam Adapter
    private void GetData(){
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mDatabase.child("Aset").child("Workorder").child("electrical")
//                .orderByChild("status").equalTo("On Progress")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataWoElectrical = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek electrical
                           ModelWoElectrical modelWoElectrical = snapshot.getValue(ModelWoElectrical.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            modelWoElectrical.setKey(snapshot.getKey());
                            dataWoElectrical.add(modelWoElectrical);
                            ImageURL.setText(modelWoElectrical.getPicture());
                        }

                        //Inisialisasi Adapter dan data Wo dalam bentuk Array
                        adapter = new RecyclerViewAdapter2(dataWoElectrical, MyListWoElectrical.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                       // Toast.makeText(getApplicationContext(),"Downloading failed", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    @Override
    public void onDeleteData(ModelWoElectrical data, int position) {
        /*
         * Kode ini akan dipanggil ketika method onDeleteData
         * dipanggil dari adapter pada RecyclerView melalui interface.
         * kemudian akan menghapus data berdasarkan primary key dari data tersebut
         * Jika berhasil, maka akan memunculkan Toast
         */
        String userID = auth.getUid();
        if( (CurrentUser.getText().toString().equals(AuthAdmin))  && mDatabase != null){
            deleteImage();
            mDatabase.child("Aset")
                    .child("Workorder")
                    .child("electrical")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MyListWoElectrical.this, "Succesfully deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(MyListWoElectrical.this, "Insufficient Access Level", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteImage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(ImageURL.getText().toString());
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("Picture","#deleted");
            }
        });
    }

}
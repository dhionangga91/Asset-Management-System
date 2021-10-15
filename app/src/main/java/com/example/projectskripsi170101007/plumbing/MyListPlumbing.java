package com.example.projectskripsi170101007.plumbing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectskripsi170101007.MainActivity;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.adapter.RecyclerViewAdapter1;
import com.example.projectskripsi170101007.adapter.RecyclerViewAdapter5;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelPlumbing;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyListPlumbing extends AppCompatActivity implements RecyclerViewAdapter5.dataListener {
    //Deklarasi Variable untuk RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseRecyclerOptions<ModelPlumbing> options;
    private FloatingActionButton Add,Home;
    private String AuthAdmin = "admin@myaset.com" , GetUserID ;
    private TextView CurrentUser,ImageURL;
    private Button Active,Inactive,All;

    //Deklarasi Variable Database Reference dan ArrayList dengan Parameter Class Model kita.
    private DatabaseReference mDatabase;
    private ArrayList<ModelPlumbing> dataPlumbing;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_plumbing);

        //Action FLoatingButton
        Add = findViewById(R.id.btnadd);
        Home = findViewById(R.id.btnhome);
        CurrentUser = findViewById(R.id.authadmin);
        ImageURL = findViewById(R.id.image);
        Active =findViewById(R.id.active);
        Inactive =findViewById(R.id.inactive);
        All = findViewById(R.id.all);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyListPlumbing.this, AddPlumbing.class);
                startActivity(intent);
            }

        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyListPlumbing.this, MainActivity.class);
                startActivity(intent);
            }

        });

        recyclerView = findViewById(R.id.datalist);
        getSupportActionBar().setTitle("Data Plumbing");
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
            // Mengatur setelah opsi untuk FirebaseRecyclerAdapter
            options = new FirebaseRecyclerOptions.Builder<ModelPlumbing>()
                    // Referensi Database yang akan digunakan beserta data Modelnya
                    .setQuery(mDatabase.child("Aset").child("Plumbing"), ModelPlumbing.class)
                    .setLifecycleOwner(this) //Untuk menangani perubahan siklus hidup pada Activity/Fragment
                    .build();

            All.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetData();
                }
            });

            Active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetActive();
                }
            });

            Inactive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetInactive();
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void GetInactive() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Aset").child("Plumbing").orderByChild("statusAset").equalTo("Inactive")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataPlumbing = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek plumbing
                            ModelPlumbing plumbing = snapshot.getValue(ModelPlumbing.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            plumbing.setKey(snapshot.getKey());
                            dataPlumbing.add(plumbing);
                        }


                        //Inisialisasi Adapter dan data Hvac dalam bentuk Array
                        adapter = new RecyclerViewAdapter5(dataPlumbing, MyListPlumbing.this);

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

    private void GetActive() {
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Aset").child("Plumbing").orderByChild("statusAset").equalTo("Active")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataPlumbing = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek plumbing
                            ModelPlumbing plumbing = snapshot.getValue(ModelPlumbing.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            plumbing.setKey(snapshot.getKey());
                            dataPlumbing.add(plumbing);
                        }


                        //Inisialisasi Adapter dan data Hvac dalam bentuk Array
                        adapter = new RecyclerViewAdapter5(dataPlumbing, MyListPlumbing.this);

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

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    //Berisi baris kode untuk mengambil data dari Database dan menampilkannya kedalam Adapter
    private void GetData(){
        //Mendapatkan Referensi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Aset").child("Plumbing")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataPlumbing = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek plumbing
                            ModelPlumbing plumbing = snapshot.getValue(ModelPlumbing.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            plumbing.setKey(snapshot.getKey());
                            dataPlumbing.add(plumbing);
                        }


                        //Inisialisasi Adapter dan data Hvac dalam bentuk Array
                        adapter = new RecyclerViewAdapter5(dataPlumbing, MyListPlumbing.this);

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
    public void onDeleteData(ModelPlumbing data, int position) {
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
                    .child("Plumbing")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MyListPlumbing.this, "Succesfully deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(MyListPlumbing.this, "Insufficient Access Level", Toast.LENGTH_SHORT).show();
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

    //search item by name
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(final String query) {
                //Toast.makeText(getApplicationContext(),query, Toast.LENGTH_SHORT).show();

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Aset").child("Plumbing").orderByChild("name").equalTo(query)
                        .addValueEventListener(new ValueEventListener() {


                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //Inisialisasi ArrayList
                                dataPlumbing = new ArrayList<>();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    //Mapping data pada DataSnapshot ke dalam objek plumbing
                                    ModelPlumbing plumbing = snapshot.getValue(ModelPlumbing.class);

                                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                                    plumbing.setKey(snapshot.getKey());
                                    dataPlumbing.add(plumbing);
                                    ImageURL.setText(plumbing.getPicture());
                                }


                                //Inisialisasi Adapter dan data plumbing dalam bentuk Array
                                adapter = new RecyclerViewAdapter5(dataPlumbing, MyListPlumbing.this);

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

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }
}
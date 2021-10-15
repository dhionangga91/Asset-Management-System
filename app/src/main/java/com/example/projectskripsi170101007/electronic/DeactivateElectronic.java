package com.example.projectskripsi170101007.electronic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelElectronic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DeactivateElectronic extends AppCompatActivity {

    private TextView CODE, Name, Brand, Capacity, Location, Type, Voltage, Status, StatusNew, ModifiedDate, User, CurrentUser;
    private ImageView Container;
    private Button Deactivate;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Context context;
    private FirebaseAuth auth;
    private String GetUserID;
    private String AuthAdmin = "supervisor@astra.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate_electronic);

        mDatabase = FirebaseDatabase.getInstance().getReference("Aset/Electronic");
        mStorage= FirebaseStorage.getInstance().getReference("Aset");
        CurrentUser = findViewById(R.id.authadmin);
        CODE = findViewById(R.id.code);
        Name = findViewById(R.id.name);
        Brand = findViewById(R.id.brand);
        Capacity = findViewById(R.id.capacity);
        Location = findViewById(R.id.location);
        Type = findViewById(R.id.type);
        Voltage = findViewById(R.id.voltage);
        ModifiedDate = findViewById(R.id.modifieddate);
        ModifiedDate.setText(getDateToday());
        User = findViewById(R.id.user);
        Voltage = findViewById(R.id.voltage);
        Location = findViewById(R.id.location);
        User = findViewById(R.id.user);
        Status = findViewById(R.id.status);
        StatusNew = findViewById(R.id.statusNew);
        StatusNew.setText("Inactive");
        Container = findViewById(R.id.container);
        Deactivate = findViewById(R.id.disable);
        getData();
        getDataUser();
        getDateToday();

        Deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekAdmin();
            }
        });

    }

    private void cekAdmin() {
        if (CurrentUser.getText().toString().equals(AuthAdmin)){
            uploadData();
        }else {
            Toast.makeText(DeactivateElectronic.this, "Insufficient Access Level", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadData() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if(networkInfo == null){
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
        else if (Status.getText().toString().equals("Inactive")){
            Toast.makeText(DeactivateElectronic.this, "This asset has been Deactivated", Toast.LENGTH_SHORT).show();
        }
        else {
                    /*
                      Menjalankan proses update data.
                      Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                    */
            ModelElectronic setElectronic = new ModelElectronic();
            setElectronic.setCode(CODE.getText().toString());
            setElectronic.setName(Name.getText().toString());
            setElectronic.setBrand(Brand.getText().toString());
            setElectronic.setCapacity(Capacity.getText().toString());
            setElectronic.setType(Type.getText().toString());
            setElectronic.setVoltage(Voltage.getText().toString());
            setElectronic.setLocation(Location.getText().toString());
            setElectronic.setUser(User.getText().toString());
            setElectronic.setModifiedDate(ModifiedDate.getText().toString());
            setElectronic.setStatusAset(StatusNew.getText().toString());
            updateElectronic(setElectronic);
            uploadImage();
        }
    }

    private void uploadImage() {
        // Get the data from an ImageView as bytes
        Container.setDrawingCacheEnabled(true);
        Container.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Container.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();

        String namaFile = UUID.randomUUID() + ".jpg"; //Nama Gambar (Secara Random)
        final String pathImage = "Electronic/" + namaFile; //Lokasi lengkap dimana gambar akan disimpan

        final UploadTask uploadTask = mStorage.child(pathImage).putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){
                    //Mendapatkan URL download dari gambar yang telah disimpan pada Storage
                    mStorage.child(pathImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Menyimpan URL pada Variable String
                            final String downloadURL = uri.toString();
                            final String getKey = getIntent().getExtras().getString("getPrimaryKey");
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("Aset").child("Electronic").child(getKey).child("picture").setValue(downloadURL);
                        }
                    });
                }
            }
        });
    }

    //Menghandle hasil data yang diambil dari kamera atau galeri untuk ditampilkan pada ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Container.setVisibility(View.VISIBLE);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Container.setImageBitmap(bitmap);
            Uri uri = data.getData();
            Container.setImageURI(uri);
        }
    }

    private void getDataUser() {
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("User").child(GetUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                Admin admin = snapshot.getValue(Admin.class);
                System.out.println(admin.getUsername());
                User.setText(admin.getUsername());
                CurrentUser.setText(admin.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void updateElectronic(ModelElectronic setElectronic) {
        String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        mDatabase.child("Aset")
                .child("Electronic")
                .child(getKey)
                .setValue(setElectronic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        CODE.setText("");
                        Name.setText("");
                        Brand.setText("");
                        Capacity.setText("");
                        Type.setText("");
                        Voltage.setText("");
                        Location.setText("");
                        User.setText("");
                        ModifiedDate.setText(getDateToday());
                        StatusNew.setText("Inactive");
                        Toast.makeText(DeactivateElectronic.this, "Successful deactivated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getCODE = getIntent().getExtras().getString("dataCODE");
        final String getName = getIntent().getExtras().getString("dataName");
        final String getBrand = getIntent().getExtras().getString("dataBrand");
        final String getCapacity = getIntent().getExtras().getString("dataCapacity");
        final String getType = getIntent().getExtras().getString("dataType");
        final String getVoltage = getIntent().getExtras().getString("dataVoltage");
        final String getLocation = getIntent().getExtras().getString("dataLocation");
        final String getMaintenanceDate = getIntent().getExtras().getString("dataMaintenanceDate");
        final String getUser = getIntent().getExtras().getString("dataUser");
        final String getModifiedDate = getIntent().getExtras().getString("dataModifiedDate");
        final String getStatus = getIntent().getExtras().getString("dataStatusAset");
        final String getImage_url = getIntent().getExtras().getString("dataPicture");
        CODE.setText(getCODE);
        Name.setText(getName);
        Brand.setText(getBrand);
        Capacity.setText(getCapacity);
        Type.setText(getType);
        Voltage.setText(getVoltage);
        Location.setText(getLocation);
        User.setText(getUser);
        Status.setText(getStatus);
        // Reference to an image file in Cloud Storage
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        reference.child("Electronic/");
        Picasso.with(context)
                .load(getImage_url)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(Container);
    }
}
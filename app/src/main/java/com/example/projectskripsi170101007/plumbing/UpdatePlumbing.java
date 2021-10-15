package com.example.projectskripsi170101007.plumbing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.electrical.UpdateElectrical;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelPlumbing;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class UpdatePlumbing extends AppCompatActivity implements View.OnClickListener {

    //Deklarasi Variable
    private EditText  nameBaru, brandBaru, capacityBaru, locationBaru, maintenanceDateBaru, flowBaru, pressureBaru, StatusCek;
    private TextView codeBaru, userBaru, modifiedDateBaru, Status;
    private String GetUserID;
    private ImageView containerBaru;
    private Button update, UnggahGambar;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekCODE, cekName, cekBrand, cekCapacity, cekLocation, cekMaintenance, cekPressure, cekFlow;
    private ProgressBar progressBar;
    private ImageButton Btn_calendar;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    //Mendapatkan Referensi dari Firebase Storage
    //Deklarasi Variable StorageReference
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private Context context;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plumbing);

        mStorage= FirebaseStorage.getInstance().getReference("Aset");
        mDatabase = FirebaseDatabase.getInstance().getReference("Aset/Plumbing");
        Status = findViewById(R.id.status);
        Status.setText("Active");
        StatusCek = findViewById(R.id.statuscek);
        codeBaru = findViewById(R.id.new_code);
        nameBaru = findViewById(R.id.new_name);
        brandBaru = findViewById(R.id.new_brand);
        capacityBaru = findViewById(R.id.new_capacity);
        pressureBaru = findViewById(R.id.new_pressure);
        flowBaru = findViewById(R.id.new_flow);
        locationBaru = findViewById(R.id.new_location);
        userBaru = findViewById(R.id.new_user);
        maintenanceDateBaru = findViewById(R.id.new_maintenanceDate);
        modifiedDateBaru = findViewById(R.id.new_modifieddate);
        containerBaru = findViewById(R.id.new_imageContainer);
        progressBar = findViewById(R.id.progressBar);
        UnggahGambar = findViewById(R.id.select_Image);
        UnggahGambar.setOnClickListener(this);
        Btn_calendar = findViewById(R.id.btn_calendar);
        update = findViewById(R.id.update);
        //Input format tanggal
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        Btn_calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdatePlumbing.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        getDataUser();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Electrical yang akan dicek
                cekCODE = codeBaru.getText().toString();
                cekName = nameBaru.getText().toString();
                cekBrand = brandBaru.getText().toString();
                cekCapacity = capacityBaru.getText().toString();
                cekFlow = flowBaru.getText().toString();
                cekPressure = pressureBaru.getText().toString();
                cekLocation = locationBaru.getText().toString();
                cekMaintenance = maintenanceDateBaru.getText().toString();
                ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();


                //Mengecek agar tidak ada data yang kosong, saat proses update
                if( isEmpty(cekCODE) ||isEmpty(cekName) || isEmpty(cekBrand) || isEmpty(cekCapacity) || isEmpty(cekFlow) || isEmpty(cekPressure) || isEmpty(cekLocation) || isEmpty(cekMaintenance)){
                    Toast.makeText(UpdatePlumbing.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if (StatusCek.getText().toString().equals("Inactive")){
                    Toast.makeText(UpdatePlumbing.this, "This Asset has been Deactivated", Toast.LENGTH_SHORT).show();
                }
                else if(networkInfo == null){
                    Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
                }
                else {
                    /*
                      Menjalankan proses update data.
                      Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                    */
                    ModelPlumbing setPlumbing = new ModelPlumbing();
                    setPlumbing.setCode(codeBaru.getText().toString());
                    setPlumbing.setName(nameBaru.getText().toString());
                    setPlumbing.setBrand(brandBaru.getText().toString());
                    setPlumbing.setCapacity(capacityBaru.getText().toString());
                    setPlumbing.setFlow(flowBaru.getText().toString());
                    setPlumbing.setPressure(pressureBaru.getText().toString());
                    setPlumbing.setLocation(locationBaru.getText().toString());
                    setPlumbing.setMaintenanceDate(maintenanceDateBaru.getText().toString());
                    setPlumbing.setUser(userBaru.getText().toString());
                    setPlumbing.setModifiedDate(modifiedDateBaru.getText().toString());
                    setPlumbing.setStatusAset(Status.getText().toString());
                    updatePlumbing(setPlumbing);
                    uploadImage();
                }
            }
        });
    }

    private void getDataUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("User").child(GetUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                Admin admin = snapshot.getValue(Admin.class);
                System.out.println(admin.getUsername());
                userBaru.setText(admin.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void uploadImage() {
        // Get the data from an ImageView as bytes
        containerBaru.setDrawingCacheEnabled(true);
        containerBaru.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) containerBaru.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();

        String namaFile = UUID.randomUUID() + ".jpg"; //Nama Gambar (Secara Random)
        final String pathImage = "Plumbing/" + namaFile; //Lokasi lengkap dimana gambar akan disimpan

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
                            mDatabase.child("Aset").child("Plumbing").child(getKey).child("picture").setValue(downloadURL);
                        }
                    });
                }

            }
        });

    }
    //Proses Update data yang sudah ditentukan
    private void updatePlumbing(ModelPlumbing setPlumbing) {
        String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("Aset")
                .child("Plumbing")
                .child(getKey)
                .setValue(setPlumbing)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        codeBaru.setText("");
                        nameBaru.setText("");
                        brandBaru.setText("");
                        capacityBaru.setText("");
                        flowBaru.setText("");
                        pressureBaru.setText("");
                        locationBaru.setText("");
                        maintenanceDateBaru.setText("");
                        userBaru.setText("");
                        Status.setText("Active");
                        modifiedDateBaru.setText(getDateToday());
                        Toast.makeText(UpdatePlumbing.this, "Successful uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


    }

    //Menampilkan data yang akan di update
    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getCODE = getIntent().getExtras().getString("dataCODE");
        final String getName = getIntent().getExtras().getString("dataName");
        final String getBrand = getIntent().getExtras().getString("dataBrand");
        final String getCapacity = getIntent().getExtras().getString("dataCapacity");
        final String getLocation = getIntent().getExtras().getString("dataLocation");
        final String getFlow = getIntent().getExtras().getString("dataFlow");
        final String getPressure = getIntent().getExtras().getString("dataPressure");
        final String getMaintenanceDate = getIntent().getExtras().getString("dataMaintenanceDate");
        final String getUser = getIntent().getExtras().getString("dataUser");
        final String getModifiedDate = getIntent().getExtras().getString("dataModifiedDate");
        final String getStatus = getIntent().getExtras().getString("dataStatusAset");
        final String getImage_url = getIntent().getExtras().getString("dataPicture");
        codeBaru.setText(getCODE);
        nameBaru.setText(getName);
        brandBaru.setText(getBrand);
        capacityBaru.setText(getCapacity);
        flowBaru.setText(getFlow);
        pressureBaru.setText(getPressure);
        locationBaru.setText(getLocation);
        maintenanceDateBaru.setText(getMaintenanceDate);
        userBaru.setText(getUser);
        modifiedDateBaru.setText(getDateToday());
        StatusCek.setText(getStatus);
        // Reference to an image file in Cloud Storage
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        reference.child("Electrical/");
        Picasso.with(context)
                .load(getImage_url)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(containerBaru);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        maintenanceDateBaru.setText(sdf.format(myCalendar.getTime()));
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    //Menghandle hasil data yang diambil dari kamera atau galeri untuk ditampilkan pada ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    containerBaru.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    containerBaru.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    containerBaru.setVisibility(View.VISIBLE);
                    Uri uri = data.getData();
                    containerBaru.setImageURI(uri);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        //Menerapkan kejadian saat tombol pilih gambar di klik
        getImage();
    }

    private void getImage() {
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(UpdatePlumbing.this)
                .setTitle("Upload Image")
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //Mengambil gambar dari Kemara ponsel
                                Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
                                break;

                            case 1:
                                //Mengambil gambar dari galeri
                                Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(imageIntentGallery, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }
}
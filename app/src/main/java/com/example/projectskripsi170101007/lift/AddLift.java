package com.example.projectskripsi170101007.lift;

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
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelLift;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddLift extends AppCompatActivity implements View.OnClickListener {

    //Deklarasi Variable
    private EditText CODE, Name, Brand, Capacity, Location, Type, Power, Rope, Speed, MaintenanceDate;
    private TextView User, ModifiedDate, Status;
    private String GetUserID, CodeRef;
    private FirebaseAuth auth;
    private FloatingActionButton Simpan;
    private ImageButton Btn_calendar;
    private Button Upload;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private ImageView ImageContainer;
    private ProgressBar progressBar;
    private long maxCode=0;

    //Mendapatkan Referensi dari Firebase Storage
    //Deklarasi Variable StorageReference
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mDatabase;


    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    //Cek Fields yang kosong
    private boolean isEmpty(String s) {return TextUtils.isEmpty(s);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lift);

        //Inisialisasi edittext
        Status = findViewById(R.id.status);
        Status.setText("Active");
        CodeRef = "BM-MA-05-0";
        CODE = findViewById(R.id.code);
        Name = findViewById(R.id.name);
        Brand = findViewById(R.id.brand);
        Capacity = findViewById(R.id.capacity);
        Location = findViewById(R.id.location);
        Type = findViewById(R.id.type);
        Power = findViewById(R.id.power);
        Rope = findViewById(R.id.rope);
        Speed = findViewById(R.id.speed);
        MaintenanceDate = findViewById(R.id.maintenancedate);
        ModifiedDate = findViewById(R.id.modifieddate);
        ModifiedDate.setText(getDateToday());
        User = findViewById(R.id.user);
        Btn_calendar = findViewById(R.id.btn_calendar);
        Upload = findViewById(R.id.select_Image);
        Upload.setOnClickListener(this);
        ImageContainer = findViewById(R.id.imageContainer);
        progressBar = findViewById(R.id.progressBar);
        mStorage = FirebaseStorage.getInstance().getReference("Aset");
        mDatabase = FirebaseDatabase.getInstance().getReference("Aset/Lift");

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
                new DatePickerDialog(AddLift.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Mendapakan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance();
        getDataUser();//method pangambilan data di database user

        //Action pada button simpan
        Simpan = findViewById(R.id.save);
        Simpan.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                String getUserID = auth.getCurrentUser().getUid();

                //Menyimpan Data yang diinputkan User kedalam Variable
                String getCODE = CODE.getText().toString();
                String getName = Name.getText().toString();
                String getBrand = Brand.getText().toString();
                String getCapacity = Capacity.getText().toString();
                String getLocation = Location.getText().toString();
                String getMaintenanceDate = MaintenanceDate.getText().toString();
                String getModifiedDate = ModifiedDate.getText().toString();
                String getPower = Power.getText().toString();
                String getRope = Rope.getText().toString();
                String getSpeed = Speed.getText().toString();
                String getType = Type.getText().toString();
                String getUser = User.getText().toString();

                // Mendapatkan Referensi dari Database
                mDatabase = FirebaseDatabase.getInstance().getReference();


                // Mengecek apakah ada data yang kosong
                if (ImageContainer.getDrawable()==null || isEmpty(getName) || isEmpty(getBrand) || isEmpty(getCapacity) || isEmpty(getLocation) || isEmpty(getType) || isEmpty(getPower) || isEmpty(getRope) || isEmpty(getSpeed) ||  isEmpty(getMaintenanceDate) ) {
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(AddLift.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                   cekKoneksi();
                }
            }
        }));
    }

    private void cekKoneksi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            cekDataAset();

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void cekDataAset() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Aset/Lift");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxCode=(snapshot.getChildrenCount());
                }
                uploadImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Aset/Lift");
//        ref.orderByChild("code").equalTo(CodeRef+CODE.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot){
//                if(dataSnapshot.exists()) {
//                    Toast.makeText(AddLift.this, "The Code Already Exist", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    uploadImage();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
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
                User.setText(admin.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void uploadImage(){
        //Mendapatkan data dari ImageView sebagai Bytes
        ImageContainer.setDrawingCacheEnabled(true);
        Bitmap bitmap = ((BitmapDrawable) ImageContainer.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //Mengkompress bitmap menjadi JPG dengan kualitas gambar 100%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        String namaFile = UUID.randomUUID()+".jpg"; //Nama Gambar (Secara Random)
        final String pathImage = "Lift/"+namaFile; //Lokasi lengkap dimana gambar akan disimpan

        UploadTask uploadTask = mStorage.child(pathImage).putBytes(bytes);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){
                    //Mendapatkan URL download dari gambar yang telah disimpan pada Storage
                    mStorage.child(pathImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //Menyimpan URL pada Variable String
                            String downloadURL = uri.toString();

                            //Menentukan referensi lokasi data url yang akan disimpan
//                            mDatabase.child("Aset").child("Lift").child(CodeRef+CODE.getText().toString()).setValue(new ModelLift(
//                                    CodeRef+CODE.getText().toString(),
                            mDatabase.child("Aset").child("Lift").child(CodeRef + String.valueOf(maxCode+1)).setValue(new ModelLift(

                                    CodeRef+String.valueOf(maxCode+1),
                                    Name.getText().toString(),
                                    Brand.getText().toString(),
                                    Capacity.getText().toString() ,
                                    Location.getText().toString() + " th",
                                    MaintenanceDate.getText().toString(),
                                    ModifiedDate.getText().toString(),
                                    downloadURL,
                                    Type.getText().toString(),
                                    Power.getText().toString() ,
                                    Rope.getText().toString() ,
                                    Speed.getText().toString() ,
                                    User.getText().toString(),
                                    Status.getText().toString()
                            ));

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AddLift.this, "Uploading Success", Toast.LENGTH_SHORT).show();
                            ImageContainer.setVisibility(View.GONE);
                            Intent i = new Intent(AddLift.this, MyListLift.class);
                            startActivity(i);
                        }
                    });
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddLift.this, "Uploading failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //Method ini digunakan untuk menghitung persentase proses uploading file
                progressBar.setVisibility(View.VISIBLE);
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        MaintenanceDate.setText(sdf.format(myCalendar.getTime()));
    }


    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    @Override
    public void onClick(View v) {
        //Menerapkan kejadian saat tombol pilih gambar di klik
        getImage();
    }

    //Method ini digunakan untuk mengambil gambar dari Kamera
    private void getImage(){
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddLift.this)
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

    //Menghandle hasil data yang diambil dari kamera atau galeri untuk ditampilkan pada ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    ImageContainer.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ImageContainer.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    ImageContainer.setVisibility(View.VISIBLE);
                    Uri uri = data.getData();
                    ImageContainer.setImageURI(uri);
                }
                break;
        }
    }

}
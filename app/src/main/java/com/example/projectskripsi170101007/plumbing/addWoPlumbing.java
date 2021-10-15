package com.example.projectskripsi170101007.plumbing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.electrical.addWoElectrical;
import com.example.projectskripsi170101007.model.Admin;
import com.example.projectskripsi170101007.model.ModelWoPlumbing;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class addWoPlumbing extends AppCompatActivity {

    //Deklarasi Variable
    private EditText Wocode, MaintenanceDate, Remarks, PIC ;
    private TextView CurrentUser,Name, Location, Status, UserInput, UserFinish, WoDate, FinishDate , UserStart, StartDate, UserApproved, ApprovedDate, UserReject, RejectDate, StatusAset;
    private String GetUserID, Coderef;
    private DatabaseReference mDatabase;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth auth;
    private Button Submit;
    private ImageButton Btn_calendar;
    private ProgressBar progressBar;
    private ImageView Container;
    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    private String AuthAdmin = "supervisor@astra.com";
    private long maxCode=0;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    //Cek Fields yang kosong
    private boolean isEmpty(String s){ return TextUtils.isEmpty(s); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wo_plumbing);

        Coderef = "WO-PM-03-0";
        StatusAset = findViewById(R.id.statusAset);
        CurrentUser = findViewById(R.id.authadmin);
        PIC = findViewById(R.id.pic);
        Submit = findViewById(R.id.btn_submit);
        Wocode = findViewById(R.id.wocode);
        Name = findViewById(R.id.name);
        Location = findViewById(R.id.location);
        MaintenanceDate = findViewById(R.id.maintenanceDate);
        UserInput = findViewById(R.id.userInput);
        UserStart = findViewById(R.id.userStart);
        UserStart.setText("-");
        UserFinish = findViewById(R.id.userFinish);
        UserFinish.setText("-");
        UserApproved = findViewById(R.id.userApproved);
        UserApproved.setText("-");
        UserReject = findViewById(R.id.userReject);
        UserReject.setText("-");
        WoDate = findViewById(R.id.woDate);
        WoDate.setText(getDateToday());
        StartDate = findViewById(R.id.startDate);
        FinishDate = findViewById(R.id.finishDate);
        ApprovedDate = findViewById(R.id.approvedDate);
        RejectDate = findViewById(R.id.rejectDate);
        Btn_calendar = findViewById(R.id.btn_calendar);
        Remarks = findViewById(R.id.remarks);
        Submit = findViewById(R.id.btn_submit);
        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Status = findViewById(R.id.status);
        Container = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        mStorage = FirebaseStorage.getInstance().getReference("Aset");
        mDatabase = FirebaseDatabase.getInstance().getReference("Aset/Workorder/plumbing");

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
                new DatePickerDialog(addWoPlumbing.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Mendapakan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance();
        getData();//method pangambilan data di database plumbing
        getDataUser();//method panggil data user

        //Action pada button simpan
        Submit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick (View v){
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                String getUserID = auth.getCurrentUser().getUid();

                //Mendapatkan Instance dari Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference(); // Mendapatkan Referensi dari Database

                //Menyimpan Data yang diinputkan User kedalam Variable


                String getLocation = Location.getText().toString();
                String getMaintenancedate = MaintenanceDate.getText().toString();
                String getName = Name.getText().toString();
                String getPIC = PIC.getText().toString();
                String getRemarks = Remarks.getText().toString();
                String getStatus = Status.getText().toString();
                String getWocode = Wocode.getText().toString();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if ( isEmpty(getName)  || isEmpty(getLocation) || isEmpty(getPIC) || isEmpty(getMaintenancedate) || isEmpty(getRemarks)|| isEmpty(getStatus) ) {
                    Toast.makeText(addWoPlumbing.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if (StatusAset.getText().toString().equals("Inactive")){
                    Toast.makeText(addWoPlumbing.this, "This Asset has been Deactivated", Toast.LENGTH_SHORT).show();
                }
                else {
                    cekKoneksi();
                }
            }
        }));

    }

    private void cekKoneksi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            cekDataWO();

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void cekDataWO() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Aset/Workorder/plumbing");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxCode = (snapshot.getChildrenCount());
                }
                cekAdmin();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        ref.orderByChild("wocode").equalTo(Coderef+Wocode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot){
//                if(dataSnapshot.exists()) {
//                    Toast.makeText(addWoPlumbing.this, "The Code Already Exist", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    cekAdmin();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }

    private void cekAdmin() {
        if ((CurrentUser.getText().toString().equals(AuthAdmin))){
            uploadImage();
        }else {
            Toast.makeText(addWoPlumbing.this, "Insufficient Access Level", Toast.LENGTH_SHORT).show();
        }
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
                UserInput.setText(admin.getUsername());
                CurrentUser.setText(admin.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void uploadImage() {

        // Get the data from an ImageView as bytes
        Container.setImageResource(R.drawable.waitingfor);
        Container.setDrawingCacheEnabled(true);
        Container.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Container.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String namaFile = UUID.randomUUID()+".jpg"; //Nama Gambar (Secara Random)
        final String pathImage = "Workorder/"+namaFile; //Lokasi lengkap dimana gambar akan disimpan

        UploadTask uploadTask = mStorage.child(pathImage).putBytes(data);
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
//                            mDatabase.child("Aset").child("Workorder").child("plumbing").child(Coderef+Wocode.getText().toString()).setValue(new ModelWoPlumbing(
                            mDatabase.child("Aset").child("Workorder").child("plumbing").child(Coderef+ String.valueOf(maxCode+1)).setValue(new ModelWoPlumbing(

//                                    Coderef+Wocode.getText().toString(),
                                    Coderef+String.valueOf(maxCode+1),
                                    Name.getText().toString(),
                                    Location.getText().toString(),
                                    PIC.getText().toString(),
                                    Remarks.getText().toString(),
                                    Status.getText().toString(),
                                    downloadURL,
                                    MaintenanceDate.getText().toString(),
                                    UserInput.getText().toString(),
                                    WoDate.getText().toString(),
                                    UserStart.getText().toString(),
                                    StartDate.getText().toString(),
                                    UserFinish.getText().toString(),
                                    FinishDate.getText().toString(),
                                    UserApproved.getText().toString(),
                                    ApprovedDate.getText().toString(),
                                    UserReject.getText().toString(),
                                    RejectDate.getText().toString()
                            ));

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(addWoPlumbing.this, "Uploading Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(addWoPlumbing.this, MyListWoPlumbing.class);
                            startActivity(i);
                            showNotification();

                        }
                    });
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(addWoPlumbing.this, "Uploading failed", Toast.LENGTH_SHORT).show();
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


    //    Method for sending notification whenever Workorder is created
    private void showNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","Hi Team, New Workorder has been made !");
            notificationObj.put("body","WORKORDER No." + Coderef + String.valueOf(maxCode+1));

            json.put("notification",notificationObj);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("NOTIF", "onResponse: ");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAuBh5D58:APA91bEl0vqzYGzeGY2Dggh4iwjP8NLzhQpKuiMbvNV6RGFOdlvVVU8RtNVHZmS2puSVsQCR4JksS6qgohoFhxT7ZPxX9YudaJP78ezJH6LAFL7nGhtHpfL4npke0YH3va10butqJS-J");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            e.printStackTrace();
        }
    }

    private void getData() {
        final String getName = getIntent().getExtras().getString("dataName");
        final String getLocation = getIntent().getExtras().getString("dataLocation");
        final String getStatusAset = getIntent().getExtras().getString("dataStatusAset");
        Name.setText(getName);
        Location.setText(getLocation);
        StatusAset.setText(getStatusAset);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        MaintenanceDate.setText(sdf.format(myCalendar.getTime()));
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }


}
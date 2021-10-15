package com.example.projectskripsi170101007.plumbing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.text.TextUtils.isEmpty;

public class Update3WoPlumbing extends AppCompatActivity {

    private TextView CurrentUser,Wocode, Name, Pic, MaintenanceDate, Location, Status, UserInput, WoDate, UserStart, StartDate;
    private EditText Remarks,StatusCek, UserFinish, FinishDate,  ApprovedDate, UserApproved, UserReject, RejectDate;
    private Button Submit;
    private String GetUserID;
    private Context context;
    private ImageView Container;
    private FirebaseAuth auth;
    private String cekWocode, cekName, cekPic, cekLocation, cekMaintenance, cekRemarks, cekStatus;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    private String AuthAdmin = "supervisor@astra.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update3_wo_plumbing);

        mStorage= FirebaseStorage.getInstance().getReference("Aset");
        mDatabase = FirebaseDatabase.getInstance().getReference("Aset/Workorder/plumbing");
        CurrentUser = findViewById(R.id.authadmin);
        Wocode = findViewById(R.id.wocode);
        Name = findViewById(R.id.name);
        Pic = findViewById(R.id.pic);
        MaintenanceDate = findViewById(R.id.maintenanceDate);
        Location = findViewById(R.id.location);
        Status = findViewById(R.id.status);
        StatusCek = findViewById(R.id.statuscek);
        Remarks = findViewById(R.id.remarks);
        UserInput= findViewById(R.id.userInput);
        WoDate = findViewById(R.id.woDate);
        UserStart = findViewById(R.id.userStart);
        StartDate = findViewById(R.id.startDate);
        UserFinish = findViewById(R.id.userFinish);
        FinishDate = findViewById(R.id.finishDate);
        UserApproved = findViewById(R.id.userApproved);
        ApprovedDate = findViewById(R.id.approvedDate);
        ApprovedDate.setText(getDateToday());
        UserReject = findViewById(R.id.userReject);
        UserReject.setText("-");
        RejectDate = findViewById(R.id.rejectDate);
        Submit = findViewById(R.id.btn_submit);
        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Container = findViewById(R.id.imageView);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getData();
        getDataUser();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Hvac yang akan dicek
                cekWocode = Wocode.getText().toString();
                cekName = Name.getText().toString();
                cekLocation = Location.getText().toString();
                cekPic = Pic.getText().toString();
                cekMaintenance = MaintenanceDate.getText().toString();
                cekRemarks = Remarks.getText().toString();
                cekStatus = Status.getText().toString();
                ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if( isEmpty(cekRemarks) ) {
                    Toast.makeText(Update3WoPlumbing.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else if (StatusCek.getText().toString().equals("In Queue")){
                    Toast.makeText(Update3WoPlumbing.this, "This Workorder is not over yet", Toast.LENGTH_SHORT).show();
                }
                else if (StatusCek.getText().toString().equals("On Progress")){
                    Toast.makeText(Update3WoPlumbing.this, "This Workorder is not over yet", Toast.LENGTH_SHORT).show();
                }
                else if (StatusCek.getText().toString().equals("Approved")){
                    Toast.makeText(Update3WoPlumbing.this, "This Workorder already approved", Toast.LENGTH_SHORT).show();
                }
                else if (StatusCek.getText().toString().equals("Reject")){
                    Toast.makeText(Update3WoPlumbing.this, "This Workorder has already rejected", Toast.LENGTH_SHORT).show();
                }
                else if(networkInfo == null){
                    Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
                }
                else {
                    /*
                      Menjalankan proses update data.
                      Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                    */
                    cekAdmin();
                }
            }
        });

    }

    private void cekAdmin() {
        if ((CurrentUser.getText().toString().equals(AuthAdmin))){
            updateData();

        }else {
            Toast.makeText(Update3WoPlumbing.this, "Insufficient Access Level", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        ModelWoPlumbing setPlumbing = new ModelWoPlumbing();
        setPlumbing.setWocode(Wocode.getText().toString());
        setPlumbing.setName(Name.getText().toString());
        setPlumbing.setLocation(Location.getText().toString());
        setPlumbing.setPic(Pic.getText().toString());
        setPlumbing.setMaintenanceDate(MaintenanceDate.getText().toString());
        setPlumbing.setRemarks(Remarks.getText().toString());
        setPlumbing.setStatus(Status.getText().toString());
        setPlumbing.setUserInput(UserInput.getText().toString());
        setPlumbing.setWoDate(WoDate.getText().toString());
        setPlumbing.setUserStart(UserStart.getText().toString());
        setPlumbing.setStartDate(StartDate.getText().toString());
        setPlumbing.setUserFinish(UserFinish.getText().toString());
        setPlumbing.setFinishDate(FinishDate.getText().toString());
        setPlumbing.setUserApproved(UserApproved.getText().toString());
        setPlumbing.setApprovedDate(ApprovedDate.getText().toString());
        setPlumbing.setUserReject(UserReject.getText().toString());
        setPlumbing.setRejectDate(RejectDate.getText().toString());
        updateWoPlumbing(setPlumbing);
        uploadImage();
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
                UserApproved.setText(admin.getUsername());
                CurrentUser.setText(admin.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void uploadImage() {
        // Get the data from an ImageView as bytes
        Container.setImageResource(R.drawable.cek);
        Container.setDrawingCacheEnabled(true);
        Container.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Container.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();

        String namaFile = UUID.randomUUID() + ".jpg"; //Nama Gambar (Secara Random)
        final String pathImage = "Workorder/" + namaFile; //Lokasi lengkap dimana gambar akan disimpan

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
                            mDatabase.child("Aset").child("Workorder").child("plumbing").child(getKey).child("picture").setValue(downloadURL);
                        }
                    });
                }
            }
        });
    }

    private void updateWoPlumbing(ModelWoPlumbing setPlumbing) {
        String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        mDatabase.child("Aset")
                .child("Workorder")
                .child("plumbing")
                .child(getKey)
                .setValue(setPlumbing)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Wocode.setText("");
                        Name.setText("");
                        Location.setText("");
                        Pic.setText("");
                        MaintenanceDate.setText("");
                        Remarks.setText("");
                        Status.setText("Approved");
                        UserInput.setText("");
                        WoDate.setText("");
                        UserStart.setText("");
                        StartDate.setText("");
                        UserFinish.setText("");
                        FinishDate.setText("");
                        UserApproved.setText("");
                        ApprovedDate.setText("");
                        UserReject.setText("");
                        RejectDate.setText("");

                        Toast.makeText(Update3WoPlumbing.this, "Successfull Updated", Toast.LENGTH_SHORT).show();
//                        showNotification();
                        finish();
                    }
                });
    }

    private void showNotification() {
        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","A Workorder Has Been Approved");
            notificationObj.put("body","Please Check your App");

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
        mDatabase.child("Aset").child("Workorder").child("plumbing");
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getWocode = getIntent().getExtras().getString("dataWocode");
        final String getName = getIntent().getExtras().getString("dataName");
        final String getLocation = getIntent().getExtras().getString("dataLocation");
        final String getPic = getIntent().getExtras().getString("dataPic");
        final String getMaintenanceDate = getIntent().getExtras().getString("dataMaintenanceDate");
        final String getRemarks = getIntent().getExtras().getString("dataRemarks");
        final String getStatus = getIntent().getExtras().getString("dataStatus");
        final String getUserInput = getIntent().getExtras().getString("dataUserInput");
        final String getWoDate = getIntent().getExtras().getString("dataWoDate");
        final String getUserStart = getIntent().getExtras().getString("dataUserStart");
        final String getStartDate = getIntent().getExtras().getString("dataStartDate");
        final String getuserFinish = getIntent().getExtras().getString("dataUserFinish");
        final String getFinishDate = getIntent().getExtras().getString("dataFinishDate");
        final String getUserApproved = getIntent().getExtras().getString("dataUserApproved");
        final String getApprovedDate = getIntent().getExtras().getString("dataApprovedDate");
        final String getUserReject = getIntent().getExtras().getString("dataUserReject");
        final String getRejectDate = getIntent().getExtras().getString("dataRejectDate");

        Wocode.setText(getWocode);
        Name.setText(getName);
        Location.setText(getLocation);
        Pic.setText(getPic);
        StatusCek.setText(getStatus);
        MaintenanceDate.setText(getMaintenanceDate);
        Remarks.setText(getRemarks);
        UserInput.setText(getUserInput);
        WoDate.setText(getWoDate);
        UserStart.setText(getUserStart);
        StartDate.setText(getStartDate);
        UserFinish.setText(getuserFinish);
        FinishDate.setText(getFinishDate);
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
}
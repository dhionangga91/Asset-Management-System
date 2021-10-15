package com.example.projectskripsi170101007.civil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectskripsi170101007.R;
import com.example.projectskripsi170101007.adapter.RvaHistoryCivil;
import com.example.projectskripsi170101007.model.ModelHistoricalCivil;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryListPmCivil extends AppCompatActivity {

    private TextView Name, Location;
    private FloatingActionButton Export;

    //Deklarasi Variable untuk RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseRecyclerOptions<ModelHistoricalCivil> options;

    //Deklarasi Variable Database Reference dan ArrayList dengan Parameter Class Model kita.
    private DatabaseReference mDatabase;
    private ArrayList<ModelHistoricalCivil> dataHistory;
    private FirebaseAuth auth;
    private static final int STORAGE_CODE = 1000;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;


    public static File pFile;
    private File historyfile;
    List<ModelHistoricalCivil> historylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list_pm_civil);

        Name = findViewById(R.id.name);
        Location = findViewById(R.id.location);
        Export = findViewById(R.id.eksport);
        recyclerView = findViewById(R.id.datalist);
        getSupportActionBar().setTitle("Civil History Repair");
        auth = FirebaseAuth.getInstance();
        cekKoneksi();
    }

    private void cekKoneksi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            MyRecyclerView();
            GetData();

            // Mengatur setelah opsi untuk FirebaseRecyclerAdapter
            options = new FirebaseRecyclerOptions.Builder<ModelHistoricalCivil>()
                    // Referensi Database yang akan digunakan beserta data Modelnya
                    .setQuery(mDatabase.child("Aset").child("History").child("Civil"), ModelHistoricalCivil.class)
                    .setLifecycleOwner(this) //Untuk menangani perubahan siklus hidup pada Activity/Fragment
                    .build();

            Export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //we need to handle runtime permission for devices with marshmallow and above
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                        //system OS >= Marshmallow(6.0), check if permission is enabled or not
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED){
                            //permission was not granted, request it
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions, STORAGE_CODE);
                        }
                        else {
                            //permission already granted, call save pdf method
                            savePdf();
                        }
                    }
                    else {
                        //system OS < Marshmallow, call save pdf method
                        savePdf();
                    }
                }
            });

            historylist = new ArrayList<>();
            //create files in charity care folder
            historyfile = new File("/storage/emulated/0/Report/");

            //check if they exist, if not create them(directory)
            if ( !historyfile.exists()) {
                historyfile.mkdirs();
            }
            pFile = new File(historyfile, "CivilReport"+ new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(System.currentTimeMillis())+".pdf");

            //fetch payment and disabled users details;
            fetchPaymentUsers();

        }else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void GetNameKey() {
        final String getName = getIntent().getExtras().getString("dataName");
        final String getLocation = getIntent().getExtras().getString("dataLocation");
        Name.setText(getName);
        Location.setText(getLocation);
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
        mDatabase.child("Aset").child("History").child("Civil")
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataHistory = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek history
                            ModelHistoricalCivil historicalPM = snapshot.getValue(ModelHistoricalCivil.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            historicalPM.setKey(snapshot.getKey());
                            dataHistory.add(historicalPM);
                        }


                        //Inisialisasi Adapter dan data civil dalam bentuk Array
                        adapter = new RvaHistoryCivil(dataHistory, HistoryListPmCivil.this);

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
                        Log.e("MyListHistory", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    private void fetchPaymentUsers() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Aset").child("History").child("Civil");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//creating an object and setting to displlay
                    ModelHistoricalCivil pays = new ModelHistoricalCivil();
                    pays.setMaintenanceDate(snapshot.child("maintenanceDate").getValue().toString());
                    pays.setLocation(snapshot.child("location").getValue().toString());
                    pays.setRemarks(snapshot.child("remarks").getValue().toString());
                    pays.setPic(snapshot.child("pic").getValue().toString());

                    //this just log details fetched from db(you can use Timber for logging
                    Log.d("History", "maintenaceDate: " + pays.getMaintenanceDate());
                    Log.d("History", "location: " + pays.getLocation());
                    Log.d("History", "remarks: " + pays.getRemarks());
                    Log.d("History", "pic: " + pays.getPic());

                    /* The error before was cause by giving incorrect data type
                    You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
                     */
                    historylist.add(pays);
                }
                //create a pdf file and catch exception beacause file may not be created
                try {
                    createHistoryReport(historylist);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createHistoryReport(List<ModelHistoricalCivil> historylist) throws DocumentException, FileNotFoundException{
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor goldColor = WebColors.getRGBColor("#FFAC13");



        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(pFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6, 20, 15, 15, 15});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        Chunk noText = new Chunk("No.", white);
        PdfPCell noCell = new PdfPCell(new Phrase(noText));
        noCell.setFixedHeight(50);
        noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        noCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk maintenanceDateText = new Chunk("Maintenance Date", white);
        PdfPCell nameCell = new PdfPCell(new Phrase(maintenanceDateText));
        nameCell.setFixedHeight(50);
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk locationText = new Chunk("Location", white);
        PdfPCell locationCell = new PdfPCell(new Phrase(locationText));
        locationCell.setFixedHeight(50);
        locationCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        locationCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk remarksText = new Chunk("Remarks", white);
        PdfPCell phoneCell = new PdfPCell(new Phrase(remarksText));
        phoneCell.setFixedHeight(50);
        phoneCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phoneCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk picText = new Chunk("Pic", white);
        PdfPCell amountCell = new PdfPCell(new Phrase(picText));
        amountCell.setFixedHeight(50);
        amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        amountCell.setVerticalAlignment(Element.ALIGN_CENTER);


        Chunk footerText = new Chunk("\nAsset Maintenance System - Copyright @ 2020");
        PdfPCell footCell = new PdfPCell(new Phrase(footerText));
        footCell.setFixedHeight(50);
        footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footCell.setVerticalAlignment(Element.ALIGN_CENTER);
        footCell.setColspan(5);


        table.addCell(noCell);
        table.addCell(nameCell);
        table.addCell(locationCell);
        table.addCell(phoneCell);
        table.addCell(amountCell);
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();


        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(goldColor);
        }
        for (int i = 0; i < historylist.size(); i++) {
            ModelHistoricalCivil pay = historylist.get(i);

            String id = String.valueOf(i + 1);
            String maintenanceDate = pay.getMaintenanceDate();
            String location = pay.getLocation();
            String remarks = pay.getRemarks();
            String pic = pay.getPic();


            table.addCell(id + ". ");
            table.addCell(maintenanceDate);
            table.addCell(location);
            table.addCell(remarks);
            table.addCell(pic);

        }

        PdfPTable footTable = new PdfPTable(new float[]{6, 20, 15, 15, 15});
        footTable.setTotalWidth(PageSize.A4.getWidth());
        footTable.setWidthPercentage(100);
        footTable.addCell(footCell);

        PdfWriter.getInstance(document, output);
        document.open();
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, goldColor);
        document.add(new Paragraph("Asset Maintenance System Civil Reporting\n\n", g));
        document.add(table);
        document.add(footTable);

        document.close();
    }


    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted from popup, call savepdf method
                    savePdf();
                }
                else {
                    //permission was denied from popup, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void savePdf() {
        //create object of Document class
        Document mDoc = new Document();
        //pdf file name
        String mFileName ="CivilReport"+ new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mFileName + ".pdf";
        try {
            createHistoryReport(historylist);

            Toast.makeText(this, mFileName +".pdf\nis saved to\n"+ mFilePath, Toast.LENGTH_SHORT).show();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

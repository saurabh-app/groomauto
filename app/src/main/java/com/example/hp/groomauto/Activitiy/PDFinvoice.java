package com.example.hp.groomauto.Activitiy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.groomauto.BuildConfig;
import com.example.hp.groomauto.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Android-1 on 2/13/2018.
 */

public class PDFinvoice extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private String UR = "https://www.groomauto.in/android/get_history.php";
    private SharedPreferences preferences;
    private String regId1;
    private PDFView pdfView;
    private String fileName;
    private String price;
    private String vehicleCompany;
    private String vehicleModel;
    private String vehicleNumber;
    private String time;
    private String date;
    private String pickupdrop;
    private String pickupAddress;
    private String pickupTime;
    private String statusText;
    private String packageName;
    private String bokking_id;
    private Button share_pdf;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        preferences = this.getSharedPreferences("Userdata", 0);
        regId1 = preferences.getString("UserId", null);
        Intent get = getIntent();
        getSupportActionBar().setTitle("Groomauto");


        fileName = get.getStringExtra("fileName");
        price = get.getStringExtra("price");
        vehicleCompany = get.getStringExtra("vehicleCompany");
        vehicleModel = get.getStringExtra("vehicleModel");
        vehicleNumber = get.getStringExtra("vehicleNumber");
        time = get.getStringExtra("time");
        date = get.getStringExtra("date");
        pickupdrop = get.getStringExtra("pickupdrop");
        pickupAddress = get.getStringExtra("pickupAddress");
        pickupTime = get.getStringExtra("pickupTime");
        statusText = get.getStringExtra("status");
        packageName = get.getStringExtra("packageName");
        bokking_id = get.getStringExtra("booking_id");


        // Toast.makeText(getApplicationContext(),price,Toast.LENGTH_LONG).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        pdfView = findViewById(R.id.pdfView);
        share_pdf = findViewById(R.id.share_pdf);

        try {

            Document document = new Document();

            File dir = new File(fileName);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "/Groomauto_" + bokking_id + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            TableBuilder tableBuilder = new TableBuilder(PDFinvoice.this);
            TableWithImageInfo tableWithImageInfo = new TableWithImageInfo(PDFinvoice.this);
            InvoceHeader invoceHeader = new InvoceHeader(PDFinvoice.this,bokking_id);
            AmountTableBuilder amountTableBuilder = new AmountTableBuilder(PDFinvoice.this, price, packageName);

            document.open();


            try {
                document.add(tableWithImageInfo.createTable());
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(invoceHeader.createTable());

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(tableBuilder.createTable());

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(amountTableBuilder.createTable());
            document.close();

            Toast.makeText(getApplicationContext(), "PDF Saved to: " + fileName + "/Groomauto_" + bokking_id + ".pdf", Toast.LENGTH_LONG).show();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        displayPDF(fileName + "/Groomauto_" + bokking_id + ".pdf");

        share_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new Intent
                File file = new File(fileName + "/Groomauto_" + bokking_id + ".pdf");

//                Uri path = FileProvider.getUriForFile(PDFinvoice.this, BuildConfig.APPLICATION_ID + ".provider",file);
                Uri path = Uri.fromFile(file);
                Intent pdfOpenintent = new Intent(Intent.ACTION_SEND);
                pdfOpenintent.putExtra(Intent.EXTRA_STREAM,path);
                pdfOpenintent.setDataAndType(path, "application/pdf");
                try {
                    startActivity(pdfOpenintent);
                }
                catch (ActivityNotFoundException e) {

                }
            }

        });

    }

    private void displayPDF(String file) {

        pdfView.fromFile(new File(file))
                .onLoad(this)
                .load();

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}
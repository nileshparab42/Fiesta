package com.example.fiesta;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class PdfActivity extends AppCompatActivity {

    String url="",filepath;

    WebView wvPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        wvPdf = findViewById(R.id.pdfholder);

        wvPdf.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        filepath="https://firebasestorage.googleapis.com/v0/b/fiesta-36c5b.appspot.com/o/Timetables%2FTE%20.pdf?alt=media&token=77cde635-34a7-482a-a209-6aaa6ba76bab";

        wvPdf.getSettings().setJavaScriptEnabled(true);



        wvPdf.setInitialScale(100);
        wvPdf.getSettings().setLoadWithOverviewMode(true);
        wvPdf.getSettings().setUseWideViewPort(true);
        wvPdf.getSettings().setDomStorageEnabled(true);

        wvPdf.getSettings().setLoadWithOverviewMode(true);
        wvPdf.setInitialScale(1);


        wvPdf.getSettings().setBuiltInZoomControls(true);

    try {
        url= URLEncoder.encode(filepath,"UTF-8");

    }catch (Exception ex){}
    wvPdf.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);


    }
}
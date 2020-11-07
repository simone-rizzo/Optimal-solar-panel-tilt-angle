package com.rizzo.sensortest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Metodo per visualizzare il file pdf all'interno di una web view
 */
public class why_it_works extends AppCompatActivity {

    WebView webview;
    private boolean permises=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_it_works);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);

        requestPermission();
        //Handling Page Navigation
        webview.setWebViewClient(new WebViewClient());

        webview.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                if(permises) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://simone-rizzo.github.io/Optimal-solar-panel-tilt-angle/solarpanel-doc.pdf"));
                    CookieManager cookieManager = CookieManager.getInstance();
                    request.allowScanningByMediaScanner();
                    Environment.getExternalStorageDirectory();
                    getApplicationContext().getFilesDir().getPath(); //which returns the internal app files directory path
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SolarPVOptimalSpotDoc.pdf");
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                }
                else {
                    requestPermission();
                }
            }
        });
        //Load a URL on WebView
        webview.loadUrl("https://drive.google.com/file/d/1em5KtU7d_BbG7Glvr_IYrpokBgjjZE00/view?usp=sharing");
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
    public void requestPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                permises=true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                permises=false;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
    }
}
package com.example.android.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    FrameLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.web_view);
        mProgressBar = (FrameLayout) findViewById(R.id.loader);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new POGWebClient());
        mWebView.loadUrl("http://netsach.co.in/benjamin/radio.html");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.loadUrl("");
    }

    public class POGWebClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            mWebView.loadUrl("javascript:(function() { " +
                    "document.getElementsByTagName('nav')[0].style.display = \"none\";" +
                    "document.getElementById('home').style.display = \"none\"; " +
                    "document.getElementById('lessons').style.display = \"none\"; " +
                    "})()");
            mProgressBar.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }
}

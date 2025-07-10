package com.example.airlines;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    
    public static final String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra(EXTRA_URL);
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient()); // This line ensures links open in the WebView
        webView.getSettings().setJavaScriptEnabled(true);
        
        if (url != null && !url.isEmpty()) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        WebView webView = findViewById(R.id.webView);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
} 
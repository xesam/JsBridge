package dev.xesam.android.web.jsbridge.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private WebView vWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vWebView = (WebView) findViewById(R.id.webview);
        vWebView.getSettings().setJavaScriptEnabled(true);
        vWebView.loadUrl("file:///android_asset/index.html");
    }

}

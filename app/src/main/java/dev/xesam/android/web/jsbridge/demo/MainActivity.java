package dev.xesam.android.web.jsbridge.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.client.ClientProxy;

public class MainActivity extends AppCompatActivity {

    private WebView vWebView;
    private Button vBtn1;
    private Button vBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vWebView = (WebView) findViewById(R.id.webview);
        vBtn1 = (Button) findViewById(R.id.invoke_js_without_callback);
        vBtn2 = (Button) findViewById(R.id.invoke_js_with_callback);
        JsBridge.injectWebView(vWebView);
        vWebView.loadUrl("file:///android_asset/index.html");

        vBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClientProxy(vWebView).transact("hello from java");
            }
        });

        vBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

package dev.xesam.android.web.jsbridge.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.MarshallableString;
import dev.xesam.android.web.jsbridge.SimpleTransactHandler;
import dev.xesam.android.web.jsbridge.client.Callback;
import dev.xesam.android.web.jsbridge.server.ServerRequest;

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
        final JsBridge jsBridge = new JsBridge(vWebView);
        jsBridge.register(new SimpleTransactHandler("showPackageName") {
            @Override
            public void handle(ServerRequest serverRequest) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String packageName = getPackageName();
                        Toast.makeText(getApplicationContext(), "showPackageName:" + packageName, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        jsBridge.register(new UserHandler(this));
        vWebView.loadUrl("file:///android_asset/index.html");

        vBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsBridge.invoke("js_fn_1", new MarshallableString("yellow"), new Callback<String>() {
                    @Override
                    public void onReceiveResult(String invokeName, String result) {
                        if ("success".equals(invokeName)) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        vBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsBridge.transact("window.js_fn_1()");
            }
        });
    }
}

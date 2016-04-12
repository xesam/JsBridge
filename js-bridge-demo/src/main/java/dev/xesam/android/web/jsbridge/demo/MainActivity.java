package dev.xesam.android.web.jsbridge.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xesam.android.web.jsbridge.ClientCallback;
import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.MarshallableString;
import dev.xesam.android.web.jsbridge.ServerCallback;
import dev.xesam.android.web.jsbridge.SimpleServerHandler;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.webview)
    public WebView vWebView;

    JsBridge jsBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vWebView = (WebView) findViewById(R.id.webview);
        jsBridge = new JsBridge(vWebView);
        jsBridge.register(new SimpleServerHandler("showPackageName") {
            @Override
            public void handle(String param, ServerCallback serverCallback) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String packageName = getPackageName();
                        Tip.showTip(getApplicationContext(), "showPackageName:" + packageName);
                    }
                });
            }
        });
        jsBridge.register(new UserHandler(this));
        vWebView.loadUrl("file:///android_asset/index.html");
    }

    @OnClick(R.id.eval)
    public void performEval() {
        jsBridge.eval("window.jsFn1()");
    }

    @OnClick(R.id.invoke_1)
    public void invoke1() {
        jsBridge.invoke("jsFn1");
    }

    @OnClick(R.id.invoke_2)
    public void invoke2() {
        jsBridge.invoke("jsFn2", new MarshallableString("xesam"));
    }

    @OnClick(R.id.invoke_3)
    public void invoke3() {
        jsBridge.invoke("jsFn3", new ClientCallback<String>() {
            @Override
            public void onReceiveResult(String invokeName, String invokeParam) {

            }

            @Override
            public String getResult(String param) {
                return null;
            }
        });
    }

    @OnClick(R.id.invoke_4)
    public void invoke4() {
        jsBridge.invoke("jsFn4", new MarshallableString("yellow"), new ClientCallback<String>() {
            @Override
            public void onReceiveResult(String invokeName, String invokeParam) {
                if ("success".equals(invokeName)) {
                    Tip.showTip(getApplicationContext(), invokeParam);
                }
            }

            @Override
            public String getResult(String param) {
                return param;
            }
        });
    }
}

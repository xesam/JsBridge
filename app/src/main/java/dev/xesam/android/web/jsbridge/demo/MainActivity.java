package dev.xesam.android.web.jsbridge.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.Marshallable;
import dev.xesam.android.web.jsbridge.SimpleTransactHandler;
import dev.xesam.android.web.jsbridge.client.ClientProxy;
import dev.xesam.android.web.jsbridge.client.ClientRequest;
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
        jsBridge.register(new SimpleTransactHandler("getUser") {
            @Override
            public void handle(final ServerRequest serverRequest) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        User user = getUser();
                        String serverParams = serverRequest.getServerParams();
                        Map<String, String> map = new Gson().fromJson(serverParams, Map.class);
                        String prefix = map.get("name_prefix");
                        Toast.makeText(getApplicationContext(), "user.getName():" + prefix + "/" + user.getName(), Toast.LENGTH_SHORT).show();
                        final String userMarshalling = new Gson().toJson(user);
                        if ("standard_error".equals(prefix)) {
                            serverRequest.triggerCallback("fail", new Marshallable() {
                                @Override
                                public String toMarshalling() {
                                    return "{\"error\" : \"这里是错误消息\"}";
                                }
                            });
                        } else {
                            serverRequest.triggerCallback("success", new Marshallable() {
                                @Override
                                public String toMarshalling() {
                                    return userMarshalling;
                                }
                            });
                        }
                    }
                });
            }
        });
        vWebView.loadUrl("file:///android_asset/index.html");

        vBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientRequest request = new ClientRequest(null, null);
                new ClientProxy(jsBridge).transact(request);
            }
        });

        vBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClientProxy(jsBridge).transact("window.js_fn_1()");
            }
        });
    }

    private User getUser() {
        return new User();
    }
}

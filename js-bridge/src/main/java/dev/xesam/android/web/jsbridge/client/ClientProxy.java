package dev.xesam.android.web.jsbridge.client;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import java.util.Locale;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientProxy {

    public WebView mWebView;

    public ClientProxy(WebView webView) {
        this.mWebView = webView;
    }

    public void transact(String marshalling) {
        String script = String.format(Locale.getDefault(), "window.server_onTransact('%s')", marshalling);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        } else {
            mWebView.loadUrl("javascript:" + script);
        }
    }
}

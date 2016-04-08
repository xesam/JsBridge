package dev.xesam.android.web.jsbridge.client;

import android.os.Build;
import android.util.Log;
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

    public void transact(ClientRequest request) {
        transact(request.getInvokeInfoMarshalling(), request.getParamMarshalling());
    }

    private void transact(String invokeInfoMarshalling, String paramMarshalling) {
        String script = String.format(Locale.getDefault(), "window.Proxy.server_onTransact('%s', '%s')", invokeInfoMarshalling, paramMarshalling);
        transact(script);
    }

    public void transact(String script) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.e("evaluateJavascript", "evaluateJavascript successful");
                }
            });
        } else {
            mWebView.loadUrl("javascript:" + script);
        }
    }
}

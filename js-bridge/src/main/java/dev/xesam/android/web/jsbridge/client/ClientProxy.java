package dev.xesam.android.web.jsbridge.client;

import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;

import java.util.Locale;

import dev.xesam.android.web.jsbridge.JsBridge;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientProxy {

    private JsBridge mJsBridge;

    public ClientProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
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
            mJsBridge.getWebView().evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.e("evaluateJavascript", "evaluateJavascript successful");
                }
            });
        } else {
            mJsBridge.getWebView().loadUrl("javascript:" + script);
        }
    }
}

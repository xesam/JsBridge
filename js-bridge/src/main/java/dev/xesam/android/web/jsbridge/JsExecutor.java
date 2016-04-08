package dev.xesam.android.web.jsbridge;

import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;

import java.util.Locale;

import dev.xesam.android.web.jsbridge.client.InvokeInfo;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class JsExecutor {

    private JsBridge mJsBridge;

    public JsExecutor(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
    }

    public void onTransact(InvokeInfo invokeInfo, Marshallable param) {
        String script;
        if (param == null) {
            script = String.format(Locale.getDefault(), "window.JsExecutor.server_onTransact('%s')", invokeInfo.toMarshalling());
        } else {
            script = String.format(Locale.getDefault(), "window.JsExecutor.server_onTransact('%s', '%s')", invokeInfo.toMarshalling(), param.toMarshalling());
        }

        onTransact(script);
    }

    public void onTransact(String script) {
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

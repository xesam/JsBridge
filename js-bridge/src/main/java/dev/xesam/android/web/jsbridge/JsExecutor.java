package dev.xesam.android.web.jsbridge;

import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;

import java.util.Locale;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
class JsExecutor {

    public static final String JS_EXECUTOR = "window.JsExecutor";

    private JsBridge mJsBridge;

    public JsExecutor(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
    }

    public void transact(TransactInfo transactInfo, Marshallable param) {
        String script;
        if (param == null) {
            script = String.format(Locale.getDefault(), "%s.server_onTransact(%s)", JS_EXECUTOR, transactInfo.toMarshalling());
        } else {
            script = String.format(Locale.getDefault(), "%s.server_onTransact(%s, %s)", JS_EXECUTOR, transactInfo.toMarshalling(), param.toMarshalling());
        }

        transact(script);
    }

    public void transact(String script) {
        Log.e(getClass().getSimpleName(), script);
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

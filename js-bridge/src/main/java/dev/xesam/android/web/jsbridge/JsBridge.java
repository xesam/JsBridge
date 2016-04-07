package dev.xesam.android.web.jsbridge;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import dev.xesam.android.web.jsbridge.server.ServerProxy;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public final class JsBridge {

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    public static void injectWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        ServerProxy serverProxy = new ServerProxy();
        webView.addJavascriptInterface(serverProxy, ServerProxy.JAVA_BRIDGE);
    }

    void transact() {

    }

    void onTransact() {

    }
}

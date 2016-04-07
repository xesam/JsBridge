package dev.xesam.android.web.jsbridge.client;

import android.webkit.WebView;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientProxy {

    public WebView mWebView;

    public ClientProxy(WebView webView) {
        this.mWebView = webView;
    }

    public void transact(String marshalling) {
        mWebView.loadUrl("javascript:server_onTransact('" + marshalling + "')");
    }
}

package dev.xesam.android.web.jsbridge;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import dev.xesam.android.web.jsbridge.client.Callback;
import dev.xesam.android.web.jsbridge.client.ClientProxy;
import dev.xesam.android.web.jsbridge.client.ClientRequest;
import dev.xesam.android.web.jsbridge.server.ServerProxy;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public final class JsBridge {

    private WebView mWebView;

    private ServerProxy mServerProxy;
    private ClientProxy mClientProxy;

    public JsBridge(WebView webView) {
        this.mWebView = webView;
        this.mServerProxy = new ServerProxy(this);
        this.mClientProxy = new ClientProxy(this);

        inject(mWebView);
    }

    public WebView getWebView() {
        return mWebView;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    public void inject(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(mServerProxy, ServerProxy.JAVA_BRIDGE);
    }

    public void register(TransactHandler transactHandler) {
        mServerProxy.register(transactHandler);
    }

    public void invoke(String method, Marshallable param, Callback callback) {
        InvokeInfo invokeInfo = InvokeInfo.createDirectInvoke(method);
        ClientRequest clientRequest = new ClientRequest(invokeInfo, param, callback);
        mClientProxy.transact(clientRequest);
    }

    public void dispatchCallback(InvokeInfo invokeInfo, String paramMarshalling) {
        mClientProxy.dispatchCallback(invokeInfo, paramMarshalling);
    }

    public void transact(String script) {
        mClientProxy.transact(script);
    }

    public void transact(InvokeInfo invokeInfo) {
        mClientProxy.transact(invokeInfo);
    }

    public void transact(ClientRequest request) {
        mClientProxy.transact(request);
    }
}

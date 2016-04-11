package dev.xesam.android.web.jsbridge;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import dev.xesam.android.web.jsbridge.client.ClientCallback;
import dev.xesam.android.web.jsbridge.client.ClientProxy;
import dev.xesam.android.web.jsbridge.server.ServerHandler;
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

    public void register(ServerHandler serverHandler) {
        mServerProxy.register(serverHandler);
    }

    public void invoke(String method, Marshallable param, ClientCallback clientCallback) {
        InvokeInfo invokeInfo = InvokeInfo.createDirectInvoke(method);
        mClientProxy.transact(invokeInfo, param, clientCallback);
    }

    /**
     * dispatch js callback
     */
    public void dispatchClientCallback(InvokeInfo invokeInfo, String paramMarshalling) {
        mClientProxy.dispatchClientCallback(invokeInfo, paramMarshalling);
    }

    public void transact(String script) {
        mClientProxy.transact(script);
    }

    public void transact(InvokeInfo invokeInfo, Marshallable invokeParam, ClientCallback<?> clientCallback) {
        mClientProxy.transact(invokeInfo, invokeParam, clientCallback);
    }
}

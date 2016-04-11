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

    /**
     * eval js code segment
     */
    public void eval(String script) {
        mClientProxy.transact(script);
    }

    /**
     * invoke js_server method
     */
    public void invoke(String invokeMethod) {
        invoke(invokeMethod, null);
    }

    /**
     * invoke js_server method
     */
    public void invoke(String invokeMethod, Marshallable param) {
        invoke(invokeMethod, param, null);
    }

    /**
     * invoke js_server method
     */
    public void invoke(String invokeMethod, Marshallable param, ClientCallback clientCallback) {
        TransactInfo transactInfo = TransactInfo.createDirectInvoke(invokeMethod);
        mClientProxy.transact(transactInfo, param, clientCallback);
    }

    /**
     * dispatch java_client callback
     */
    public void dispatchClientCallback(TransactInfo transactInfo, String paramMarshalling) {
        mClientProxy.dispatchClientCallback(transactInfo, paramMarshalling);
    }

    /**
     * dispatch java_server callback
     */
    public void dispatchServerCallback(TransactInfo transactInfo, Marshallable invokeParam) {
        mClientProxy.transact(transactInfo, invokeParam, null);
    }
}

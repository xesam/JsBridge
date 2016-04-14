package dev.xesam.android.web.jsbridge;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * JsBridge. invoke js function.and dispatch callback
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public final class JsBridge {

    public static boolean DEBUG = false;

    private WebView mWebView;
    private URL mCurrentUrl;

    private ServerProxy mServerProxy;
    private ClientProxy mClientProxy;

    public JsBridge(WebView webView) {
        this.mWebView = webView;
        this.mServerProxy = new ServerProxy(this);
        this.mClientProxy = new ClientProxy(this);

        inject(mWebView);
    }

    WebView getWebView() {
        return mWebView;
    }

    public void monitor(String urlString) {
        URL newUrl = null;
        try {
            newUrl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (newUrl == null) {
            mClientProxy.onNewPageLoaded();
            return;
        }

        if (mCurrentUrl == null) {
            mCurrentUrl = newUrl;
            mClientProxy.onNewPageLoaded();
            return;
        }

        if (!isSame(mCurrentUrl, newUrl)) {//different page
            mCurrentUrl = newUrl;
            mClientProxy.onNewPageLoaded();
        }
    }

    private boolean isSame(URL urlA, URL urlB) {

        if (!urlA.sameFile(urlB)) {
            return false;
        }

        String queryA = urlA.getQuery();
        String queryB = urlA.getQuery();

        if (queryA == null) {
            return queryB == null;
        } else {
            return queryA.equals(queryB);
        }
    }

    public void destroy() {
        mServerProxy.destroy();
        mClientProxy.destroy();
        mWebView.getSettings().setJavaScriptEnabled(false);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    private void inject(WebView webView) {
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
        invoke(invokeMethod, null, null);
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
    public void invoke(String invokeMethod, ClientCallback clientCallback) {
        invoke(invokeMethod, null, clientCallback);
    }

    /**
     * invoke js_server method
     */
    public void invoke(String invokeMethod, Marshallable param, ClientCallback clientCallback) {
        TransactInfo transactInfo = TransactInfo.createDirectInvoke(invokeMethod);
        mClientProxy.transact(transactInfo, param, clientCallback);
    }

    /**
     * dispatch java_client callback(js -> java)
     */
    void dispatchClientCallback(TransactInfo transactInfo, String paramMarshalling) {
        mClientProxy.dispatchClientCallback(transactInfo, paramMarshalling);
    }

    /**
     * dispatch java_server callback(java -> js)
     */
    void dispatchServerCallback(TransactInfo transactInfo, Marshallable invokeParam) {
        mClientProxy.transact(transactInfo, invokeParam, null);
    }
}

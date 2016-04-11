package dev.xesam.android.web.jsbridge.server;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.TransactHandler;
import dev.xesam.android.web.jsbridge.client.ClientRequest;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerProxy {
    public static final String JAVA_BRIDGE = "JavaExecutor";

    private JsBridge mJsBridge;
    private Map<String, TransactHandler> handlers = new HashMap<>();

    public ServerProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
    }

    @JavascriptInterface
    public void onTransact(String invokeInfoMarshalling, String paramMarshalling) {
        Log.e("ServerProxy#onTransact", invokeInfoMarshalling + "/" + paramMarshalling);
        ServerRequest serverRequest = new ServerRequest(this, invokeInfoMarshalling, paramMarshalling);
        InvokeInfo invokeInfo = serverRequest.getInvokeInfo();
        if (invokeInfo.isCallback()) {
            dispatchCallbackInvoke(serverRequest);
        } else if (invokeInfo.isDirectInvoke()) {
            dispatchDirectInvoke(serverRequest);
        }
    }

    public void register(TransactHandler transactHandler) {
        handlers.put(transactHandler.getHandlerName(), transactHandler);
    }

    /**
     * js -> java ： js 直接调用 java 方法
     */
    private void dispatchDirectInvoke(ServerRequest serverRequest) {
        TransactHandler transactHandler = handlers.get(serverRequest.getInvokeInfo().getInvokeName());
        if (transactHandler != null) {
            transactHandler.handle(serverRequest);
        }
    }

    /**
     * js -> java ： js 回调 java 方法
     */
    private void dispatchCallbackInvoke(ServerRequest serverRequest) {
        mJsBridge.dispatchClientCallback(serverRequest.getInvokeInfo(), serverRequest.getInvokeParam());
    }

    /**
     * java -> js ： java 触发 js 回调
     */
    void triggerCallback(ClientRequest clientRequest) {
        mJsBridge.transact(clientRequest);
    }
}

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
            mJsBridge.dispatchCallback(invokeInfo, paramMarshalling);
        } else if (invokeInfo.isDirectInvoke()) {
            TransactHandler transactHandler = handlers.get(invokeInfo.getInvokeName());
            if (transactHandler != null) {
                transactHandler.handle(serverRequest);
            }
        }
    }

    public void register(TransactHandler transactHandler) {
        handlers.put(transactHandler.getServerMethodName(), transactHandler);
    }

    void dispatchCallback(ServerRequest serverRequest, ClientRequest clientRequest) {
        mJsBridge.transact(clientRequest);
    }
}

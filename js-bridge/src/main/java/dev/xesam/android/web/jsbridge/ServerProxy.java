package dev.xesam.android.web.jsbridge;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * handle [js -> java] transaction
 * Created by xesamguo@gmail.com on 16-4-7.
 */
class ServerProxy {
    public static final String JAVA_BRIDGE = "JavaExecutor";

    private JsBridge mJsBridge;
    private Map<String, ServerHandler> handlers = new HashMap<>();

    public ServerProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
    }

    public void destroy() {
        handlers.clear();
    }

    /**
     * [js -> java]
     */
    @JavascriptInterface
    public final void onTransact(String transactInfoMarshalling, String paramMarshalling) {
        if (JsBridge.DEBUG) {
            Log.d("ServerProxy#onTransact", transactInfoMarshalling + "/" + paramMarshalling);
        }
        TransactInfo transactInfo = TransactInfo.parse(transactInfoMarshalling);
        if (transactInfo.isCallback()) {
            dispatchCallbackInvoke(transactInfo, paramMarshalling);
        } else {
            dispatchDirectInvoke(transactInfo, paramMarshalling);
        }
    }

    public void register(ServerHandler serverHandler) {
        handlers.put(serverHandler.getHandlerName(), serverHandler);
    }

    /**
     * [js -> java]
     * Js finish its invocation and delivery callback to Java
     */
    private void dispatchDirectInvoke(TransactInfo transactInfo, String paramMarshalling) {
        ServerHandler serverHandler = handlers.get(transactInfo.getInvokeName());
        if (serverHandler != null) {
            ServerCallback serverCallback = new ServerCallback(mJsBridge, transactInfo.getCallbackId());
            serverHandler.handle(paramMarshalling, serverCallback);
        } else {
            if (JsBridge.DEBUG) {
                Log.w("dispatchDirectInvoke", "no ServerHandler:" + transactInfo.getInvokeName());
            }
        }
    }

    /**
     * java -> [js -> java] Js call Java method directly
     */
    private void dispatchCallbackInvoke(TransactInfo transactInfo, String paramMarshalling) {
        mJsBridge.dispatchClientCallback(transactInfo, paramMarshalling);
    }
}

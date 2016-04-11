package dev.xesam.android.web.jsbridge;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
class ServerProxy {
    public static final String JAVA_BRIDGE = "JavaExecutor";

    private JsBridge mJsBridge;
    private Map<String, ServerHandler> handlers = new HashMap<>();

    public ServerProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
    }

    @JavascriptInterface
    public void onTransact(String invokeInfoMarshalling, String paramMarshalling) {
        Log.e("ServerProxy#onTransact", invokeInfoMarshalling + "/" + paramMarshalling);
        TransactInfo transactInfo = TransactInfo.parse(invokeInfoMarshalling);
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
     * js -> java ： js 直接调用 java 方法
     */
    private void dispatchDirectInvoke(TransactInfo transactInfo, String paramMarshalling) {
        ServerHandler serverHandler = handlers.get(transactInfo.getInvokeName());
        if (serverHandler != null) {
            ServerCallback serverCallback = new ServerCallback(mJsBridge, transactInfo.getCallbackId());
            serverHandler.handle(paramMarshalling, serverCallback);
        }
    }

    /**
     * js -> java ： js 回调 java 方法
     */
    private void dispatchCallbackInvoke(TransactInfo transactInfo, String paramMarshalling) {
        mJsBridge.dispatchClientCallback(transactInfo, paramMarshalling);
    }
}

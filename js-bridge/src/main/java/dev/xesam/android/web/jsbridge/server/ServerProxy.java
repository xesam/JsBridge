package dev.xesam.android.web.jsbridge.server;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.JsBridge;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerProxy {
    public static final String JAVA_BRIDGE = "JavaExecutor";

    private JsBridge mJsBridge;
    private Map<String, ServerHandler> handlers = new HashMap<>();

    public ServerProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
    }

    @JavascriptInterface
    public void onTransact(String invokeInfoMarshalling, String paramMarshalling) {
        Log.e("ServerProxy#onTransact", invokeInfoMarshalling + "/" + paramMarshalling);
        InvokeInfo invokeInfo = InvokeInfo.parse(invokeInfoMarshalling);
        if (invokeInfo.isCallback()) {
            dispatchCallbackInvoke(invokeInfo, paramMarshalling);
        } else {
            dispatchDirectInvoke(invokeInfo, paramMarshalling);
        }
    }

    public void register(ServerHandler serverHandler) {
        handlers.put(serverHandler.getHandlerName(), serverHandler);
    }

    /**
     * js -> java ： js 直接调用 java 方法
     */
    private void dispatchDirectInvoke(InvokeInfo invokeInfo, String paramMarshalling) {
        ServerHandler serverHandler = handlers.get(invokeInfo.getInvokeName());
        if (serverHandler != null) {
            ServerCallback serverCallback = new ServerCallback(mJsBridge, invokeInfo.getCallbackId());
            serverHandler.handle(paramMarshalling, serverCallback);
        }
    }

    /**
     * js -> java ： js 回调 java 方法
     */
    private void dispatchCallbackInvoke(InvokeInfo invokeInfo, String paramMarshalling) {
        mJsBridge.dispatchClientCallback(invokeInfo, paramMarshalling);
    }
}

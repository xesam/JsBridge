package dev.xesam.android.web.jsbridge.client;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.JsBridge;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientProxy {

    private Map<Long, Callback<?>> callbacks = new HashMap<>();
    private JsBridge mJsBridge;
    private JsExecutor mJsExecutor;

    public ClientProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
        this.mJsExecutor = new JsExecutor(mJsBridge);
    }

    public void transact(String script) {
        mJsExecutor.transact(script);
    }

    public void transact(InvokeInfo invokeInfo) {
        mJsExecutor.transact(invokeInfo, null);
    }

    public void transact(ClientRequest request) {
        if (request.getCallback() != null) {
            final long callbackId = SystemClock.elapsedRealtime();
            callbacks.put(callbackId, request.getCallback());
            request.getInvokeInfo().setCallbackId(callbackId);
        }
        mJsExecutor.transact(request.getInvokeInfo(), request.getInvokeParam());
    }

    /**
     * js -> java ： 回调 java 方法
     */
    public void dispatchCallback(InvokeInfo invokeInfo, String paramMarshalling) {
        if (callbacks.containsKey(invokeInfo.getInvokeId())) {
            Callback callback = callbacks.get(invokeInfo.getInvokeId());
            callback.onReceiveResult(invokeInfo.getInvokeName(), paramMarshalling);
        }
    }
}

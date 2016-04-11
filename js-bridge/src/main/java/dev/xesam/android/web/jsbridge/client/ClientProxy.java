package dev.xesam.android.web.jsbridge.client;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.Marshallable;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientProxy {

    private Map<Long, ClientCallback<?>> callbacks = new HashMap<>();
    private JsBridge mJsBridge;
    private JsExecutor mJsExecutor;

    public ClientProxy(JsBridge mJsBridge) {
        this.mJsBridge = mJsBridge;
        this.mJsExecutor = new JsExecutor(mJsBridge);
    }

    public void transact(String script) {
        mJsExecutor.transact(script);
    }

    public void transact(InvokeInfo invokeInfo, Marshallable invokeParam, ClientCallback<?> clientCallback) {
        if (clientCallback != null) {
            final long callbackId = SystemClock.elapsedRealtime();
            callbacks.put(callbackId, clientCallback);
            invokeInfo.setCallbackId(callbackId);
        }
        mJsExecutor.transact(invokeInfo, invokeParam);
    }

    /**
     * js -> java ： 回调 java 方法
     */
    public void dispatchClientCallback(InvokeInfo invokeInfo, String paramMarshalling) {
        ClientCallback clientCallback = callbacks.get(invokeInfo.getInvokeId());
        if (clientCallback != null) {
            clientCallback.onReceiveResult(invokeInfo.getInvokeName(), clientCallback.getResult(paramMarshalling));
            callbacks.remove(invokeInfo.getInvokeId());
        }
    }
}

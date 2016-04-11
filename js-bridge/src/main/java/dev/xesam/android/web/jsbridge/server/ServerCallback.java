package dev.xesam.android.web.jsbridge.server;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.Marshallable;
import dev.xesam.android.web.jsbridge.client.ClientRequest;

/**
 * java callback
 * <p/>
 * Created by xesamguo@gmail.com on 16-4-11.
 */
public class ServerCallback {
    private JsBridge mJsBridge;
    private long mCallbackId;

    public ServerCallback(JsBridge jsBridge, long callbackId) {
        this.mJsBridge = jsBridge;
        this.mCallbackId = callbackId;
    }

    /**
     * java -> js ： 触发 js 回调
     */
    public void invoke(String callbackName, Marshallable callbackParam) {
        InvokeInfo invokeInfo = InvokeInfo.createCallbackInvoke(mCallbackId, callbackName);
        ClientRequest clientRequest = new ClientRequest(invokeInfo, callbackParam);
        mJsBridge.transact(clientRequest);
    }
}

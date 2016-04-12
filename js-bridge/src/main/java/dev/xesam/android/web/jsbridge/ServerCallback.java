package dev.xesam.android.web.jsbridge;

/**
 * callback registered in js
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
     * js -> [java -> js]
     */
    public void invoke(String callbackName, Marshallable callbackParam) {
        TransactInfo transactInfo = TransactInfo.createCallbackInvoke(mCallbackId, callbackName);
        mJsBridge.dispatchServerCallback(transactInfo, callbackParam);
    }
}

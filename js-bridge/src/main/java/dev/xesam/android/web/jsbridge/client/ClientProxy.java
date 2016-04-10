package dev.xesam.android.web.jsbridge.client;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.JsBridge;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientProxy {

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
        mJsExecutor.transact(request.getInvokeInfo(), request.getParam());
    }
}

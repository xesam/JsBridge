package dev.xesam.android.web.jsbridge.client;

import dev.xesam.android.web.jsbridge.JsBridge;
import dev.xesam.android.web.jsbridge.JsExecutor;

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

    public void transact(ClientRequest request) {
        transact(request.getInvokeInfoMarshalling(), request.getParamMarshalling());
    }

    private void transact(String invokeInfoMarshalling, String paramMarshalling) {
        mJsExecutor.onTransact(invokeInfoMarshalling, paramMarshalling);
    }

    public void transact(String script) {
        mJsExecutor.onTransact(script);
    }
}

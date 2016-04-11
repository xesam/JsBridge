package dev.xesam.android.web.jsbridge.server;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.Marshallable;
import dev.xesam.android.web.jsbridge.client.ClientRequest;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerRequest {

    private ServerProxy mServerProxy;

    private InvokeInfo mInvokeInfo;
    private String invokeParam;

    ServerRequest(ServerProxy serverProxy, String invokeInfoMarshalling, String paramMarshalling) {
        this.mServerProxy = serverProxy;
        mInvokeInfo = InvokeInfo.parse(invokeInfoMarshalling);
        invokeParam = paramMarshalling;
    }

    public InvokeInfo getInvokeInfo() {
        return mInvokeInfo;
    }

    public String getInvokeParam() {
        return invokeParam;
    }

    /**
     * java -> js ： 触发 js 回调
     */
    public void triggerCallback(String callbackName, Marshallable callbackParam) {
        InvokeInfo invokeInfo = InvokeInfo.createCallbackInvoke(mInvokeInfo, callbackName);
        ClientRequest request = new ClientRequest(invokeInfo, callbackParam);
        mServerProxy.triggerCallback(request);
    }
}

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
    private String serverMethodParams;

    public ServerRequest(ServerProxy serverProxy, String invokeInfoMarshalling, String paramMarshalling) {
        this.mServerProxy = serverProxy;
        mInvokeInfo = InvokeInfo.createFromMarshalling(invokeInfoMarshalling);
        serverMethodParams = paramMarshalling;
    }

    public InvokeInfo getInvokeInfo() {
        return mInvokeInfo;
    }

    public String getServerParams() {
        return serverMethodParams;
    }

    public void triggerCallback(String callbackMethodName, Marshallable marshallable) {
        InvokeInfo invokeInfo = InvokeInfo.createServerCallback(mInvokeInfo, callbackMethodName);
        ClientRequest request = new ClientRequest(invokeInfo, marshallable);
        mServerProxy.dispatchCallback(this, request);
    }
}

package dev.xesam.android.web.jsbridge.client;

import dev.xesam.android.web.jsbridge.Marshallable;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientRequest {

    private InvokeInfo mInvokeInfo;
    private Marshallable mParam;

    public ClientRequest(InvokeInfo invokeInfo) {
        this(invokeInfo, null);
    }

    public ClientRequest(InvokeInfo invokeInfo, Marshallable param) {
        mInvokeInfo = invokeInfo;
        mParam = param;
    }

    public InvokeInfo getInvokeInfo() {
        return mInvokeInfo;
    }

    public Marshallable getParam() {
        return mParam;
    }
}

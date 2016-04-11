package dev.xesam.android.web.jsbridge.client;

import dev.xesam.android.web.jsbridge.InvokeInfo;
import dev.xesam.android.web.jsbridge.Marshallable;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientRequest {

    private InvokeInfo mInvokeInfo;
    private Marshallable mInvokeParam;
    private Callback<?> mCallback;

    public ClientRequest(InvokeInfo invokeInfo) {
        this(invokeInfo, null);
    }

    public ClientRequest(InvokeInfo invokeInfo, Marshallable param) {
        mInvokeInfo = invokeInfo;
        mInvokeParam = param;
    }

    public ClientRequest(InvokeInfo invokeInfo, Marshallable param, Callback<?> callback) {
        mInvokeInfo = invokeInfo;
        mInvokeParam = param;
        mCallback = callback;
    }

    public InvokeInfo getInvokeInfo() {
        return mInvokeInfo;
    }

    public Marshallable getInvokeParam() {
        return mInvokeParam;
    }

    public Callback<?> getCallback() {
        return mCallback;
    }
}

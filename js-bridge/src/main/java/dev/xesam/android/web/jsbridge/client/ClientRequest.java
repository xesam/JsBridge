package dev.xesam.android.web.jsbridge.client;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientRequest {

    private InvokeInfo mInvokeInfo;
    private String mParamMarshalling;

    public ClientRequest(InvokeInfo invokeInfo) {
        this(invokeInfo, null);
    }

    public ClientRequest(InvokeInfo invokeInfo, String paramMarshalling) {
        mInvokeInfo = invokeInfo;
        mParamMarshalling = paramMarshalling;
    }

    public String getInvokeInfoMarshalling() {
        return mInvokeInfo.toMarshalling();
    }

    public String getParamMarshalling() {
        return mParamMarshalling;
    }
}

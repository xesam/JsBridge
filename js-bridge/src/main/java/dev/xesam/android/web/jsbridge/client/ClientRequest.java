package dev.xesam.android.web.jsbridge.client;

import dev.xesam.android.web.jsbridge.server.ServerRequest;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ClientRequest {

    public ClientRequest(InvokeInfo invokeInfo) {

    }

    public ClientRequest(InvokeInfo invokeInfo, String paramMarshalling) {

    }

    public ClientRequest(ServerRequest serverRequest) {
    }

    public String getInvokeInfoMarshalling() {
        return "{}";
    }

    public String getParamMarshalling() {
        return "{}";
    }
}

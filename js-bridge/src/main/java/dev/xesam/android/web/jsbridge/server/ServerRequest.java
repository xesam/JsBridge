package dev.xesam.android.web.jsbridge.server;

import org.json.JSONException;
import org.json.JSONObject;

import dev.xesam.android.web.jsbridge.Marshallable;
import dev.xesam.android.web.jsbridge.client.ClientRequest;
import dev.xesam.android.web.jsbridge.client.InvokeInfo;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerRequest {

    public static final long INVALID_CALLBACK = 0;

    public static final String _SERVER_METHOD_NAME = "_server_method_name";
    public static final String _SERVER_METHOD_PARAMS = "_server_method_params";
    public static final String _CLIENT_CALLBACK_ID = "_client_callback";

    private ServerProxy mServerProxy;

    private String serverMethodName;
    private String serverMethodParams;
    private long clientCallbackId = INVALID_CALLBACK;

    public ServerRequest(ServerProxy serverProxy, String invokeInfoMarshalling, String paramMarshalling) {
        this.mServerProxy = serverProxy;
        unmarshalling(invokeInfoMarshalling);
        serverMethodParams = paramMarshalling;
    }

    private void unmarshalling(String marshalling) {
        try {
            JSONObject jsonObject = new JSONObject(marshalling);
            serverMethodName = jsonObject.getString(_SERVER_METHOD_NAME);
            if (jsonObject.has(_CLIENT_CALLBACK_ID)) {
                clientCallbackId = jsonObject.getLong(_CLIENT_CALLBACK_ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getServerMethodName() {
        return serverMethodName;
    }

    public String getServerParams() {
        return serverMethodParams;
    }

    public void triggerCallback(String callbackMethodName, Marshallable marshallable) {
        InvokeInfo invokeInfo = InvokeInfo.createServerCallback(clientCallbackId, callbackMethodName);
        ClientRequest request = new ClientRequest(invokeInfo, marshallable);
        mServerProxy.dispatchCallback(this, request);
    }
}

package dev.xesam.android.web.jsbridge.client;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class InvokeInfo {
    public static final long INVALID_CALLBACK = 0;

    public static final String _SERVER_METHOD_ID = "_server_method_id";
    public static final String _SERVER_METHOD_NAME = "_server_method_name";
    public static final String _CLIENT_CALLBACK_ID = "_client_callback";

    public static final String _SERVER_METHOD_PARAMS = "_server_method_params";

    private long mServerMethodId = INVALID_CALLBACK;
    private String mserverMethodName;
    private long mClientCallbackId = INVALID_CALLBACK;

    public static InvokeInfo createServerCallback(long serverMethodId) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.mServerMethodId = serverMethodId;
        return invokeInfo;
    }

    private InvokeInfo() {
    }

    private void unmarshalling(String marshalling) {
        try {
            JSONObject jsonObject = new JSONObject(marshalling);
            if (jsonObject.has(_SERVER_METHOD_ID)) {
                mServerMethodId = jsonObject.getLong(_SERVER_METHOD_ID);
            }
            if (jsonObject.has(_SERVER_METHOD_NAME)) {
                mserverMethodName = jsonObject.getString(_SERVER_METHOD_NAME);
            }
            if (jsonObject.has(_CLIENT_CALLBACK_ID)) {
                mClientCallbackId = jsonObject.getLong(_CLIENT_CALLBACK_ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toMarshalling() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(_SERVER_METHOD_ID, mServerMethodId);
            jsonObject.put(_SERVER_METHOD_NAME, mserverMethodName);
            jsonObject.put(_CLIENT_CALLBACK_ID, mClientCallbackId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}

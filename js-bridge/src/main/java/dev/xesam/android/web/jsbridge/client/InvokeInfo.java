package dev.xesam.android.web.jsbridge.client;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class InvokeInfo {
    public static final long INVALID_CALLBACK = 0;

    public static final String _SERVER_METHOD_NAME = "_server_method_name";
    public static final String _SERVER_METHOD_PARAMS = "_server_method_params";
    public static final String _CLIENT_CALLBACK_ID = "_client_callback";

    private String serverMethodName;
    private String serverMethodParams;
    private long clientCallbackId = INVALID_CALLBACK;

    public InvokeInfo(String invokeInfoMarshalling) {
        unmarshalling(invokeInfoMarshalling);
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
}

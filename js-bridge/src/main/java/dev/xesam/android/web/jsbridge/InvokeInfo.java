package dev.xesam.android.web.jsbridge;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class InvokeInfo implements Marshallable {
    public static final long INVALID_CALLBACK = 0;

    public static final String _SERVER_METHOD_ID = "_server_method_id";
    public static final String _SERVER_METHOD_NAME = "_server_method_name";
    public static final String _CLIENT_CALLBACK_ID = "_client_callback";

    private long mServerMethodId = INVALID_CALLBACK;
    private String mServerMethodName;
    private long mClientCallbackId = INVALID_CALLBACK;

    /**
     * 直接调用 js
     */
    public static InvokeInfo createServerInvoke(String serverMethodName) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.mServerMethodName = serverMethodName;
        return invokeInfo;
    }

    /**
     * 去回调 js
     */
    public static InvokeInfo createServerCallback(String callbackMethodName) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.mServerMethodName = callbackMethodName;
        return invokeInfo;
    }

    /**
     * 回调 js
     */
    public static InvokeInfo createServerCallback(InvokeInfo serverInvokeInfo, String callbackMethodName) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.mServerMethodId = serverInvokeInfo.mClientCallbackId;
        invokeInfo.mServerMethodName = callbackMethodName;
        return invokeInfo;
    }

    public static InvokeInfo createFromMarshalling(String marshalling) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.unmarshalling(marshalling);
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
                mServerMethodName = jsonObject.getString(_SERVER_METHOD_NAME);
            }
            if (jsonObject.has(_CLIENT_CALLBACK_ID)) {
                mClientCallbackId = jsonObject.getLong(_CLIENT_CALLBACK_ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toMarshalling() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(_SERVER_METHOD_ID, mServerMethodId);
            jsonObject.put(_SERVER_METHOD_NAME, mServerMethodName);
            jsonObject.put(_CLIENT_CALLBACK_ID, mClientCallbackId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public boolean isCallback() {
        return mServerMethodId > 0;
    }

    public boolean isDirectInvoke() {
        return !TextUtils.isEmpty(mServerMethodName);
    }

    public long getServerMethodId() {
        return mServerMethodId;
    }

    public String getServerMethodName() {
        return mServerMethodName;
    }

    public long getClientCallbackId() {
        return mClientCallbackId;
    }
}

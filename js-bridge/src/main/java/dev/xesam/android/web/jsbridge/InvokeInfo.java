package dev.xesam.android.web.jsbridge;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class InvokeInfo implements Marshallable {
    public static final long INVALID_INVOKE_ID = 0;
    public static final long INVALID_CALLBACK_ID = 0;

    public static final String _INVOKE_ID = "_invoke_id";
    public static final String _INVOKE_NAME = "_invoke_name";

    public static final String _CALLBACK_ID = "_callback_id";
    public static final String _CALLBACK_NAME = "_callback_name";

    private long mInvokeId = INVALID_INVOKE_ID;
    private String mInvokeName = null;

    private long mCallbackId = INVALID_CALLBACK_ID;
    private String mCallbackName = null;

    /**
     * 直接调用 js
     */
    public static InvokeInfo createDirectInvoke(String invokeName) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.mInvokeName = invokeName;
        return invokeInfo;
    }

    /**
     * 回调 js
     */
    public static InvokeInfo createServerCallback(InvokeInfo directInvoke, String callbackName) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.mInvokeId = directInvoke.mCallbackId;
        invokeInfo.mInvokeName = callbackName;
        return invokeInfo;
    }

    public static InvokeInfo parse(String marshalling) {
        InvokeInfo invokeInfo = new InvokeInfo();
        invokeInfo.unmarshalling(marshalling);
        return invokeInfo;
    }

    private InvokeInfo() {
    }

    private void unmarshalling(String marshalling) {
        try {
            JSONObject jsonObject = new JSONObject(marshalling);
            if (jsonObject.has(_INVOKE_ID)) {
                mInvokeId = jsonObject.getLong(_INVOKE_ID);
            }
            if (jsonObject.has(_INVOKE_NAME)) {
                mInvokeName = jsonObject.getString(_INVOKE_NAME);
            }
            if (jsonObject.has(_CALLBACK_ID)) {
                mCallbackId = jsonObject.getLong(_CALLBACK_ID);
            }
            if (jsonObject.has(_CALLBACK_NAME)) {
                mCallbackName = jsonObject.getString(_CALLBACK_NAME);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toMarshalling() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(_INVOKE_ID, mInvokeId);
            jsonObject.put(_INVOKE_NAME, mInvokeName);
            jsonObject.put(_CALLBACK_ID, mCallbackId);
            jsonObject.put(_CALLBACK_NAME, mCallbackName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public boolean isCallback() {
        return mInvokeId > INVALID_INVOKE_ID;
    }

    public boolean isDirectInvoke() {
        return !TextUtils.isEmpty(mInvokeName);
    }

    public long getInvokeId() {
        return mInvokeId;
    }

    public String getInvokeName() {
        return mInvokeName;
    }

    public void setCallbackId(long mClientCallbackId) {
        this.mCallbackId = mClientCallbackId;
    }
}

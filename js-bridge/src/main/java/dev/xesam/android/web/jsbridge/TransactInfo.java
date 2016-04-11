package dev.xesam.android.web.jsbridge;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * not include param
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class TransactInfo implements Marshallable {
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
    public static TransactInfo createDirectInvoke(String invokeName) {
        TransactInfo transactInfo = new TransactInfo();
        transactInfo.mInvokeName = invokeName;
        return transactInfo;
    }

    /**
     * 回调 js
     */
    public static TransactInfo createCallbackInvoke(long callbackId, String callbackName) {
        TransactInfo transactInfo = new TransactInfo();
        transactInfo.mInvokeId = callbackId;
        transactInfo.mInvokeName = callbackName;
        return transactInfo;
    }

    public static TransactInfo parse(String marshalling) {
        TransactInfo transactInfo = new TransactInfo();
        transactInfo.unmarshalling(marshalling);
        return transactInfo;
    }

    private TransactInfo() {
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

    public long getInvokeId() {
        return mInvokeId;
    }

    public String getInvokeName() {
        return mInvokeName;
    }

    public long getCallbackId() {
        return mCallbackId;
    }

    public String getCallbackName() {
        return mCallbackName;
    }

    public void setCallbackId(long mClientCallbackId) {
        this.mCallbackId = mClientCallbackId;
    }
}

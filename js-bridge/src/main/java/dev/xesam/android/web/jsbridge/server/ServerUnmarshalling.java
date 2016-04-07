package dev.xesam.android.web.jsbridge.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerUnmarshalling {

    public static final String _SERVER_METHOD_NAME = "_server_method_name";
    public static final String _SERVER_CALLBACK_ID = "_server_callback_id";

    private String serverMehondName;
    private long serverCallbackId;

    public ServerUnmarshalling(String marshalling) {
        parse(marshalling);
    }

    private void parse(String marshalling) {
        try {
            JSONObject jsonObject = new JSONObject(marshalling);
            serverMehondName = jsonObject.getString(_SERVER_METHOD_NAME);
            serverCallbackId = jsonObject.getLong(_SERVER_CALLBACK_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

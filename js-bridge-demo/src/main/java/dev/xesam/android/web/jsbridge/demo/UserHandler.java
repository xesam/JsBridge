package dev.xesam.android.web.jsbridge.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.MarshallableObject;
import dev.xesam.android.web.jsbridge.ServerHandler;
import dev.xesam.android.web.jsbridge.ServerCallback;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class UserHandler implements ServerHandler {

    private Context mContext;

    public UserHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getHandlerName() {
        return "getUser";
    }

    @Override
    public void handle(final String param, final ServerCallback serverCallback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                User user = getUser();
                Map<String, String> map = new Gson().fromJson(param, Map.class);
                String prefix = map.get("name_prefix");
                Toast.makeText(mContext, "user.getName():" + prefix + "/" + user.getName(), Toast.LENGTH_SHORT).show();
                if ("standard_error".equals(prefix)) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("msg", "get user failed");
                    String userMarshalling = new Gson().toJson(map1);
                    serverCallback.invoke("fail", new MarshallableObject(userMarshalling));
                } else {
                    String userMarshalling = new Gson().toJson(user);
                    serverCallback.invoke("success", new MarshallableObject(userMarshalling));
                }
            }
        });
    }

    private User getUser() {
        return new User();
    }
}

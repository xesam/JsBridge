package dev.xesam.android.web.jsbridge.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.MarshallableObject;
import dev.xesam.android.web.jsbridge.TransactHandler;
import dev.xesam.android.web.jsbridge.server.ServerRequest;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class UserHandler implements TransactHandler {

    private Context mContext;

    public UserHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getServerMethodName() {
        return "getUser";
    }

    @Override
    public void handle(final ServerRequest serverRequest) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                User user = getUser();
                String serverParams = serverRequest.getInvokeParam();
                Map<String, String> map = new Gson().fromJson(serverParams, Map.class);
                String prefix = map.get("name_prefix");
                Toast.makeText(mContext, "user.getName():" + prefix + "/" + user.getName(), Toast.LENGTH_SHORT).show();
                if ("standard_error".equals(prefix)) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("error", "这里是错误消息");
                    String userMarshalling = new Gson().toJson(map1);
                    serverRequest.triggerCallback("fail", new MarshallableObject(userMarshalling));
                } else {
                    String userMarshalling = new Gson().toJson(user);
                    serverRequest.triggerCallback("success", new MarshallableObject(userMarshalling));
                }
            }
        });
    }

    private User getUser() {
        return new User();
    }
}

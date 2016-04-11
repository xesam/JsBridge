package dev.xesam.android.web.jsbridge.client;

/**
 * Created by xesamguo@gmail.com on 16-4-10.
 */
public interface ClientCallback<T> {
    void onReceiveResult(String invokeName, T invokeParam);

    T getResult(String param);
}

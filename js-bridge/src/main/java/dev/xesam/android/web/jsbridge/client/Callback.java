package dev.xesam.android.web.jsbridge.client;

/**
 * Created by xesamguo@gmail.com on 16-4-10.
 */
public interface Callback<T> {
    void onReceiveResult(T result);
}

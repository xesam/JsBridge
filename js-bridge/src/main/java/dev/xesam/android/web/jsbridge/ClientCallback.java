package dev.xesam.android.web.jsbridge;

/**
 * callback registered in java
 * Created by xesamguo@gmail.com on 16-4-10.
 */
public interface ClientCallback<T> {
    /**
     * 接收到 JS 端的执行结果之后的回调方法，此处已经获得回调的方法名和参数
     */
    void onReceiveResult(String invokeName, T invokeParam);

    /**
     * 接收到 JS 端的执行结果之后，解析参数
     */
    T getResult(String param);
}

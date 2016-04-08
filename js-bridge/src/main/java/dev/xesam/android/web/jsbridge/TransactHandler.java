package dev.xesam.android.web.jsbridge;

import dev.xesam.android.web.jsbridge.server.ServerRequest;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public interface TransactHandler {
    String getServerMethodName();

    void handle(ServerRequest serverRequest);
}

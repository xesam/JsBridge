package dev.xesam.android.web.jsbridge;

import dev.xesam.android.web.jsbridge.server.ServerRequest;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class SimpleTransactHandler implements TransactHandler {
    private final String name;

    public SimpleTransactHandler(String name) {
        this.name = name;
    }

    @Override
    public String getHandlerName() {
        return name;
    }

    @Override
    public void handle(ServerRequest serverRequest) {

    }

}

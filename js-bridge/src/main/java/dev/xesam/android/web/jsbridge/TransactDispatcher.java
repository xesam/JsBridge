package dev.xesam.android.web.jsbridge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class TransactDispatcher {

    private Map<String, TransactHandler> handlers = new HashMap<>();

    public void register(TransactHandler transactHandler) {
        handlers.put(transactHandler.getServerMethodName(), transactHandler);
    }
}

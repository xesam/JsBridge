package dev.xesam.android.web.jsbridge;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Map;

/**
 * Created by xe on 16-10-18.
 */

public class TransactInfoAndroidTest extends TestCase {
    public void testCreateDirectInvoke() {
        TransactInfo transactInfo = TransactInfo.createDirectInvoke("fn_name");
        Assert.assertEquals("fn_name", transactInfo.getInvokeName());
    }

    public void testCreateCallbackInvoke() {
        TransactInfo transactInfo = TransactInfo.createCallbackInvoke(1000, "cbk_name");
        Assert.assertEquals(1000, transactInfo.getInvokeId());
        Assert.assertEquals("cbk_name", transactInfo.getInvokeName());
    }

    public void testParse() {
        TransactInfo transactInfo = TransactInfo.parse("{\"_invoke_id\":1000,\"_invoke_name\":\"fn_name\",\"_callback_id\":2000,\"_callback_name\":\"cbk_name\"}");
        Assert.assertEquals(1000, transactInfo.getInvokeId());
        Assert.assertEquals("fn_name", transactInfo.getInvokeName());
        Assert.assertEquals(2000, transactInfo.getCallbackId());
        Assert.assertEquals("cbk_name", transactInfo.getCallbackName());
    }

    public void testToMarshalling() {
        TransactInfo transactInfo = TransactInfo.createCallbackInvoke(1000, "fn_name");
        String marshalling = transactInfo.toMarshalling();
        Map<String, String> map = new Gson().fromJson(marshalling, new TypeToken<Map<String, String>>() {}.getType());
        Assert.assertEquals(1000, Integer.valueOf(map.get("_invoke_id")).intValue());
        Assert.assertEquals("fn_name", map.get("_invoke_name"));
        Assert.assertEquals(0, Integer.valueOf(map.get("_callback_id")).intValue());
        Assert.assertEquals(null, map.get("_callback_name"));
    }
}

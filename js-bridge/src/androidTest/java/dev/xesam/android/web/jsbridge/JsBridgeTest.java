package dev.xesam.android.web.jsbridge;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xe on 16-10-18.
 */

public class JsBridgeTest extends TestCase {

    public void testIsSame() {
        Assert.assertEquals(false, isSame(null, null));
        Assert.assertEquals(false, isSame("", null));
        Assert.assertEquals(false, isSame(null, ""));
        Assert.assertEquals(true, isSame("http://www.baidu.com", "http://www.baidu.com#p1"));
        Assert.assertEquals(true, isSame("http://www.baidu.com#p2", "http://www.baidu.com#p1"));

        Assert.assertEquals(false, isSame("http://www.baidu.com", "https://www.baidu.com#p1"));
        Assert.assertEquals(false, isSame("http://www.baidu.com#p2", "https://www.baidu.com#p1"));
    }

    private boolean isSame(String oldUrl, String newUrl) {
        if (oldUrl == null || newUrl == null) {
            return false;
        }
        try {
            URL urlA = new URL(oldUrl);
            URL urlB = new URL(newUrl);
            return urlA.sameFile(urlB);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package dev.xesam.android.web.jsbridge.demo;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * 全局提示工具
 *
 * @Warning 只能在 UI 线程调用
 * Created by xesamguo@gmail.com on 15-7-22.
 */
public final class Tip {

    private static Toast tip;

    public static void init(Context context) {
        tip = Toast.makeText(context.getApplicationContext(), "-", Toast.LENGTH_SHORT);
    }

    public static void showTip(Context context, String message) {
        if (message == null) {
            message = "--";
        }

        if (tip == null) {
            init(context);
        }

        if (tip != null) {
            tip.setText(message);
            tip.show();
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}

# JsBridge
## 简介

Android JsBridge 就是用来在 Android app的原生 java 代码与 javascript 代码中架设通信（调用）桥梁的辅助工具。 

[原文地址点这里](http://xesam.github.io/android/2016/04/11/Android-%E5%A6%82%E4%BD%95%E5%86%99%E4%B8%80%E4%B8%AAJsBridge.html)

[github点这里](https://github.com/xesam/JsBridge)

[使用方式戳这里](#anchor_usage)

有问题请联系 [xesam](http://xesam.github.io/about/)

或者 QQ 群 315658668

## 原理概述

参见 ： [https://xesam.github.io/android/2016/04/11/Android-%E5%A6%82%E4%BD%95%E5%86%99%E4%B8%80%E4%B8%AAJsBridge.html](https://xesam.github.io/android/2016/04/11/Android-%E5%A6%82%E4%BD%95%E5%86%99%E4%B8%80%E4%B8%AAJsBridge.html)

## 使用

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```gradle
dependencies {
    ...
    compile 'com.github.xesam:JsBridge:0.1'
}
```

## 使用方式
<a name="anchor_usage"></a>

### 必要配置

请在对应的 html 页面中引入

```html
    <script src="js-bridge.js"></script>
```

js-bridge.js 地址参见:[https://github.com/xesam/JsBridge/blob/master/js-bridge/src/main/assets/js-bridge.js](https://github.com/xesam/JsBridge/blob/master/js-bridge/src/main/assets/js-bridge.js)

### Java 环境

初始化 JsBridge:

```java
    jsBridge = new JsBridge(vWebView);
```

加入 url 监控：

```java
    vWebView.setWebViewClient(new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("onPageFinished", url);
            jsBridge.monitor(url);
        }
    });
```

Java 注册处理方法：

```java
    jsBridge.register(new SimpleServerHandler("showPackageName") {
        @Override
        public void handle(String param, ServerCallback serverCallback) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    String packageName = getPackageName();
                    Tip.showTip(getApplicationContext(), "showPackageName:" + packageName);
                }
            });
        }
    });
```

Java 在处理方法中回调 Javascript：

```java

    @Override
    public void handle(final String param, final ServerCallback serverCallback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                User user = getUser();
                Map<String, String> map = new Gson().fromJson(param, Map.class);
                String prefix = map.get("name_prefix");
                Tip.showTip(mContext, "user.getName():" + prefix + "/" + user.getName());
                if ("standard_error".equals(prefix)) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("msg", "get user failed");
                    String userMarshalling = new Gson().toJson(map1);
                    serverCallback.invoke("fail", new MarshallableObject(userMarshalling));
                } else {
                    String userMarshalling = new Gson().toJson(user);
                    serverCallback.invoke("success", new MarshallableObject(userMarshalling));
                }
            }
        });
    }
```

Java 执行 Js 函数：

```java

    jsBridge.invoke("jsFn4", new MarshallableString("yellow"), new ClientCallback<String>() {
        @Override
        public void onReceiveResult(String invokeName, final String invokeParam) {
            if ("success".equals(invokeName)) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Tip.showTip(getApplicationContext(), invokeParam);
                    }
                });
            }
        }

        @Override
        public String getResult(String param) {
            return param;
        }
    });
```

销毁 JsBridge

```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        jsBridge.destroy();
    }
```

### Javascript 环境
Javascript 的灵活性比较高，所以要简单一些：

Javascript 注册处理函数：

```javascript
    window.JavaBridge.serverRegister('jsFn4', function (transactInfo, color) {
        log("jsFn4:" + color);
        title.style.background = color;
        log("jsFn4:callback");
        transactInfo.triggerCallback('success', 'background change to ' + color);
    });
```

Javascript 执行 Java 方法：

```javascript

    var sdk = {
        getUser: function (params) {
            var _invokeName = 'getUser';
            var _invokeParam = params;
            var _clientCallback = params;
            window.JavaBridge.invoke(_invokeName, _invokeParam, _clientCallback);
        }
    };

    sdk.getUser({
        "name_prefix": "standard_error",
        "success": function (user) {
            log('sdk.getUser,success:' + user.name);
        },
        "fail": function (error) {
            log('sdk.getUser,fail:' + error.msg);
        }
    })
```

详细 Demo 请参见 [js-bridge-demo](https://github.com/xesam/JsBridge) 工程


# JsBridge

## 原理

要实现 Java 与 Js 的通信，有两条途径可以考虑：

1. 集成一个定制化的 Js 与 Html 渲染引擎，这样可以获得完全的控制权。
2. 使用 Android Sdk 提供的交互方法。

对于第一种途径，代价比较大，而且技术方案比较复杂，一般只有基于 Js 的跨平台开发才会这么做。
所以，现在说中考查第二种途径。

Android 的默认 Sdk 中， Java 与 Js 的一切交互都是依托于 WebView 的，大致有以下几个可用方法：

##### 第一， Java 调用 Js 方法

```java
    webView.loadUrl("javascript:scriptString"); //其中 scriptString 为 js 代码
```
在 KITKAT 之后，又新增了一个方法：

```java
    webView.evaluateJavascript(scriptString, new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
    
        }
    });//其中 scriptString 为 js 代码，ValueCallback 的用来获取 js 的执行结果。这是一个异步掉用。
```

##### 第二， Js 调用 Java 方法

这个调用看起比上面的正常，更像是一个调用。
现在 Js 环境中注入一个 Java 代理：

```java

    class JavaProxy{
        @JavascriptInterface //注意这里的注解。处于安全的考虑，4.1 之后强制要求，不然无法从 js 中发起调用
        public void javaFn(){
            //xxxxxx
        };
    }

    webView.addJavascriptInterface(new JavaProxy();, "java_proxy");
```

然后在 Js 环境中直接在这个 obj_proxy 代理上调用方法即可。

```javascript

    java_proxy.javaFn();
```

## 机制设计

基本的操作 Android 已经提供了，我们只需要在这几个方法上进行测试就行了。
由于天然的隔离，我们可以经这种情况与 IPC 类比起来，看起来可行。所以，我们可以模仿 Android 的 Binder 机制，
来设计一套 Java 与 Js 互相通信（调用）的机制。

当 Java 调用 Js 的时候，Java 扮演 Client 的角色，Js 扮演 Server 的角色。
当 Js 调用 Java 的时候，Java 扮演 Js 的角色，Java 扮演 Server 的角色。

对比一下：


## 需要克服的问题

Java 调用 Js 没有返回值，只能使用回调的形式。

1. 只能传递基础类型（注意，不是基本类型），包括基本类型与字符串，不包括其他对象，函数



2. Js 调用通常会使用匿名回调，

首先需要明确几个关键点：

1. java 是静态强类型，因此，无法动态的定义方法，因此，
2. 在这样的混合设计中，js 的动态性远远高于 java，因此，由 js 主导的调用需要有

## 协议设计

Java -> Javascript

invoke named javascript function,without Java callback:

    server_method
    server_method_params
    
invoke javascript callback(anonymous function),without Java callback:

    server_callback_id
    server_method_params 
    
invoke named javascript function,with Java callback:

    server_method
    server_method_params
    client_callback_id
    
invoke javascript callback(anonymous function),with Java callback:

    server_callback_id
    server_method_params 
    client_callback_id

Javascript -> Java

    window.JavaBridge = (function () {

        var _INVOKE_ID = '_invoke_id';
        var _INVOKE_NAME = '_invoke_name';
        var _CALLBACK_ID = '_callback_id';
        var _CALLBACK_NAME = '_callback_name';

        var _callbacks = {};
        var _handlers = {};

        function TransactInfo(invokeId, invokeName, callbackId, callbackName) {
            this.invokeId = invokeId;
            this.invokeName = invokeName;

            this.callbackId = callbackId;
            this.callbackName = callbackName;
        }

        TransactInfo.prototype = {
            constructor: TransactInfo,

            toJSON: function () {
                var ret = {};
                ret[_INVOKE_ID] = this.invokeId;
                ret[_INVOKE_NAME] = this.invokeName;
                ret[_CALLBACK_ID] = this.callbackId;
                ret[_CALLBACK_NAME] = this.callbackName;
                return ret;
            },

            parse: function (rawTransactInfo) {
                this.invokeId = rawTransactInfo[_INVOKE_ID];
                this.invokeName = rawTransactInfo[_INVOKE_NAME];

                this.callbackId = rawTransactInfo[_CALLBACK_ID];
                this.callbackName = rawTransactInfo[_CALLBACK_NAME];
            },

            isCallback: function () {
                return this.invokeId && this.invokeId > 0;
            },

            invoke: function (invokeParam) {
                if (this.isCallback()) {
                    var callback = _callbacks[this.invokeId];
                    if (callback) { //找到了回调
                        var targetFn = callback[this.invokeName];
                        if (targetFn) {
                            targetFn(invokeParam);
                        }
                        delete _callbacks[this.invokeId];
                    }
                } else {
                    var targetFn = _handlers[this.invokeName] || window[this.invokeName];
                    if (targetFn) {
                        targetFn(this, invokeParam);
                    }
                }
            },

            //js -> java
            triggerCallback: function (callbackName, callbackParam) {
                var transactInfo = new TransactInfo(this.callbackId, callbackName);
                javaOnTransact(transactInfo, callbackParam);
            }
        };

        function javaOnTransact(transactInfo, invokeParam) {
//            console.log("javaOnTransact:" + JSON.stringify(transactInfo) + ":" + JSON.stringify(invokeParam));
            JavaExecutor.onTransact(JSON.stringify(transactInfo), JSON.stringify(invokeParam));
        }

        return {
            invoke: function (invokeName, invokeParam, clientCallback) {
                var _callbackId = 0;
                if (clientCallback) {
                    _callbackId = new Date().getTime();
                    _callbacks[_callbackId] = clientCallback;
                }
                var transactInfo = new TransactInfo(0, invokeName, _callbackId, null);
                javaOnTransact(transactInfo, invokeParam);
            },

            serverRegister: function (handlerName, transactHandler) {
                _handlers[handlerName] = transactHandler;
            },

            serverOnTransact: function (rawTransactInfo, invokeParam) {
//                console.log('serverOnTransact:' + JSON.stringify(rawTransactInfo) + "###" + JSON.stringify(invokeParam));

                if (!rawTransactInfo) {
                    return;
                }
                var transactInfo = new TransactInfo();
                transactInfo.parse(rawTransactInfo);

//                console.log(JSON.stringify(transactInfo));

                transactInfo.invoke(invokeParam);
            }
        };

    })();
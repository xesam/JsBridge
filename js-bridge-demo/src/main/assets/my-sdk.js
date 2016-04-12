var sdk = {
    showPackageName: function (params) {
        var _invokeName = 'showPackageName';
        var _invokeParam = params;
        window.JavaBridge.invoke(_invokeName, _invokeParam, null);
    },
    getUser: function (params) {
        var _invokeName = 'getUser';
        var _invokeParam = params;
        var _clientCallback = params;
        window.JavaBridge.invoke(_invokeName, _invokeParam, _clientCallback);
    }
};
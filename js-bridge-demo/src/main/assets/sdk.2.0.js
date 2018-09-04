var sdk = (function(Bridge) {
    function get_user() {
        Bridge.postMessage(
            "android",
            "get_user",
            ["param 1", "param 2", "param 3"],
            function(res) {},
            function(err) {},
            function(res) {}
        );
    }
    function show_toast() {
        Bridge.postMessage(
            "android",
            "show_toast",
            ["param 1", "param 2", "param 3"],
            function(res) {},
            function(err) {},
            function(res) {}
        );
    }
    return {
        get_user: get_user,
        show_toast: show_toast
    };
})(Bridge);

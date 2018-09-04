## [JavaScript] communicate with the native platform

Bridge.invoke(service_name, service_action, [params], callback_success, callback_error, callback_cancel)

或者

Bridge.invoke(service_name, service_action, [params], callback)

## [Native] communicate with JavaScript

Bridge.invoke(service_name, service_action, [params], callback_success, callback_error, callback_cancel)

或者

Bridge.invoke(service_name, service_action, [params], callback)

## Callback

1. onSuccess
2. onError
3. onCancel
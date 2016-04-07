# JsBridge

## 协议设计

首先需要明确几个关键点：

1. java 是静态强类型，因此，无法动态的定义方法，因此，
2. 在这样的混合设计中，js 的动态性远远高于 java，因此，由 js 主导的调用需要有

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
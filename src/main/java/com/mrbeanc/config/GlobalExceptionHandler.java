package com.mrbeanc.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

/**
 * 全局异常处理（AOP）<br>
 * <a href="https://zhuanlan.zhihu.com/p/407379705">全局异常捕获和处理 - 知乎</a>
 */
@RestControllerAdvice //开启全局异常处理
public class GlobalExceptionHandler {
    /**捕获长轮询异步请求超时异常*/
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(AsyncRequestTimeoutException.class) //捕获特定异常
    public String handleAsyncRequestTimeoutException() {
        return "Just Timeout~";
    }

    /*
    * o.apache.coyote.http11.Http11Processor   : Error parsing HTTP request header
    * java.lang.IllegalArgumentException: Invalid character found in method name. ... HTTP method names must be tokens
    * 注：该异常不能被全局异常处理捕获，因为它发生在请求到达Controller之前，建议采用Filter过滤器处理
    * 这个错误可能是由于【爬虫】使用https请求了http接口，或者是其他非法请求格式导致的
    * 一般只会在线上环境出现，不必过分担心（不影响正常使用）
    * 正式线上环境会采用Nginx反向代理，自动过滤非法请求，不会发送到后端
    * */
}

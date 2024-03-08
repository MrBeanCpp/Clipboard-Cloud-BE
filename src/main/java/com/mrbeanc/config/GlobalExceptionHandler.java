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
}

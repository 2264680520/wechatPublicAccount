package com.zhoumin.wechat.handler;


import com.zhoumin.wechat.result.BaseResponseEntity;
import com.zhoumin.wechat.result.ResponseEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author:容合
 * @create:2019-11-22 17:
 * @description:
 */
@RestControllerAdvice
public class BaseResultHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 全局返回对象
     *
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //如果是json格式或者是全局返回对像就直接返回该对象
        if (o instanceof String || o instanceof BaseResponseEntity) {
            return o;
        }
        //返回统一处理的结果集
        return ResponseEntity.success(o);
    }
}

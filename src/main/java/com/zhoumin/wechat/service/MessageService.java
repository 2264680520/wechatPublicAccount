package com.zhoumin.wechat.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ronghe
 * @create 2020-02-17 15:33
 */
public interface MessageService {
    String newMessageRequest(HttpServletRequest request);
}

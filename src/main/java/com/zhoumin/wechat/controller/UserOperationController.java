package com.zhoumin.wechat.controller;

import com.zhoumin.wechat.dto.SuperAdmin;
import com.zhoumin.wechat.result.ResponseEntity;
import com.zhoumin.wechat.service.MessageService;
import com.zhoumin.wechat.service.UserOperationService;
import com.zhoumin.wechat.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:容合
 * @create:2020-02-23 21:
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserOperationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserOperationController.class);

    @Autowired
    UserOperationService userOperationService;

    @PostMapping("/login")
    public ResponseEntity login(String code) {
        ResponseEntity responseEntity=null;
        try {
            responseEntity = userOperationService.userLogin(code);
        } catch (Exception e) {
            LOGGER.info("获取用户信息失败");
            return ResponseEntity.error();
        }
        return responseEntity;
    }
}

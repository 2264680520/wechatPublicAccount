package com.zhoumin.wechat.controller;

import com.zhoumin.wechat.dto.SuperAdmin;
import com.zhoumin.wechat.result.ResponseEntity;
import com.zhoumin.wechat.service.MessageService;
import com.zhoumin.wechat.service.UserOperationService;
import lombok.extern.slf4j.Slf4j;
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
    @Autowired
    UserOperationService userOperationService;

    @PostMapping("/login")
    public ResponseEntity login(String code) {
        return userOperationService.userLogin(code);
    }
}

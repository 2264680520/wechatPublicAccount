package com.zhoumin.wechat.service;

import com.zhoumin.wechat.dto.SuperAdmin;
import com.zhoumin.wechat.result.ResponseEntity;

public interface UserOperationService {
    ResponseEntity userLogin(String code);
}

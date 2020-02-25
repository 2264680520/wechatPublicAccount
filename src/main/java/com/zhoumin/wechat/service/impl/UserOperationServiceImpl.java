package com.zhoumin.wechat.service.impl;

import com.zhoumin.wechat.dao.SuperAdminDao;
import com.zhoumin.wechat.dao.VerificationDao;
import com.zhoumin.wechat.dto.SuperAdmin;
import com.zhoumin.wechat.result.ResponseEntity;
import com.zhoumin.wechat.service.UserOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:容合
 * @create:2020-02-23 21:
 * @description:
 */
@Slf4j
@Service
public class UserOperationServiceImpl implements UserOperationService {
    @Autowired
    SuperAdminDao superAdminDao;

    @Autowired
    VerificationDao verificationDao;

    @Override
    public ResponseEntity userLogin(String code) {
        String weChatNumber = verificationDao.getWeChatNumberByCode(code);
        log.info("微信号" + weChatNumber);
        SuperAdmin superAdmin = superAdminDao.findByWeChatNumber(weChatNumber);
        return ResponseEntity.success(superAdmin);
    }
}

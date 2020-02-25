package com.zhoumin.wechat.dao;

import com.zhoumin.wechat.dto.SuperAdmin;
import com.zhoumin.wechat.dto.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author:容合
 * @create:2020-02-23 20:
 * @description:
 */
public interface VerificationDao extends JpaRepository<Verification, Long> {
    @Query(value = "select we_chat_number from verification where code=?",nativeQuery = true)
    String getWeChatNumberByCode(String code);
}

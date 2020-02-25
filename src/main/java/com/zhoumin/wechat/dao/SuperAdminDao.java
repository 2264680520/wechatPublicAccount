package com.zhoumin.wechat.dao;

import com.zhoumin.wechat.dto.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:容合
 * @create:2020-02-23 15:
 * @description:
 */
public interface SuperAdminDao extends JpaRepository<SuperAdmin, Long> {
    SuperAdmin findByUserName(String userName);

    SuperAdmin findByWeChatNumber(String weChatNumber);
}

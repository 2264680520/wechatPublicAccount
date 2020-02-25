package com.zhoumin.wechat.dto;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:容合
 * @create:2020-02-23 20:
 * @description:
 */
@Data
@Entity
@Table
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 微信唯一编码
     */
    @Column
    private String weChatNumber;
    /**
     * 验证码
     */
    @Column
    private String code;
}

package com.zhoumin.wechat.dto;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:容合
 * @create:2020-02-23 15:
 * @description:
 */
@Data
@Entity
@Table
public class SuperAdmin {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //超管用户名
    @Column
    private String userName;
    //超管密码
    @Column
    private String password;
    //超管vid
    @Column
    private Long vid;
    //电子邮箱地址
    @Column
    private String email;
    //绑定的微信唯一编码
    @Column
    private String weChatNumber;
}

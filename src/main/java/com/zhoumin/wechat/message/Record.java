package com.zhoumin.wechat.message;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:容合
 * @create:2020-02-20 10:
 * @description:
 */
@Data
@Entity
@Table
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fromUserName;
    @Column
    private String toUserName;
    @Column
    private String msgType;
    @Column
    private String content;

}

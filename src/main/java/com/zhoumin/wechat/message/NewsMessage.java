package com.zhoumin.wechat.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ronghe
 * @create 2020-02-17 10:10
 */
@Setter
@Getter
public class NewsMessage extends BaseMessage{
    /**
     * 图文消息个数，限制为10条以内
     */
    private Integer ArticleCount;

    /**
     * 多条图文消息信息，默认第一个item为大图
     */
    private List<Article> Articles;

}

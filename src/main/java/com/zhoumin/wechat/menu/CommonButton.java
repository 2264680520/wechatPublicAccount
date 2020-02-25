package com.zhoumin.wechat.menu;

import lombok.Getter;
import lombok.Setter;

/**
 * 普通按钮
 *
 * @author ronghe
 * @create 2020-02-17 9:56
 */
@Setter
@Getter
public class CommonButton extends BasicButton {
    private String type;

    private String key;
}

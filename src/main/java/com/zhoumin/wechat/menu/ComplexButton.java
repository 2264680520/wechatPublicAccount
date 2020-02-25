package com.zhoumin.wechat.menu;

import lombok.Getter;
import lombok.Setter;

/**
 * 父按钮
 * @author ronghe
 * @create 2020-02-17 15:24
 */
@Setter
@Getter
public class ComplexButton extends BasicButton {
    private BasicButton[] sub_button;

}

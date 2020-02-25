package com.zhoumin.wechat.menu;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ronghe
 * @create 2020-02-17 15:23
 */
@Setter
@Getter
public class ViewButton extends BasicButton {
    private String type;
    private String name;
    private String url;

}

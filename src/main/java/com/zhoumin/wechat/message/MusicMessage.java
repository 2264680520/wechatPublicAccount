package com.zhoumin.wechat.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ronghe
 * @create 2020-02-17 10:19
 */
@Setter
@Getter
public class MusicMessage extends BaseMessage {
    /**
     * 音乐
     */
    private Music Music;

}

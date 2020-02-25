package com.zhoumin.wechat.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * 凭证
 * @author ronghe
 * @create 2020-02-17 15:22
 */
@Setter
@Getter
public class AccessToken {
	/**
	 *  获取到的凭证
	 */
	private String accessToken;
	
	/**
	 *  凭证有效时间，单位：秒
	 */
	private int expiresIn;
}

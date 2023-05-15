package com.cong.springbootinit.model.dto.user;

import lombok.Data;

/**
 * 微信登录
 * @author lhc
 */
@Data
public class WxLoginRequest {
    /**
     * 微信前端校验码
     */
    private String code;
    /**
     * 加密数据信息
     */
    private String data;
    /**
     * 偏移量
     */
    private String iv;
}

package com.cong.springbootinit.model.vo;

import lombok.Data;

/**
 * 返回给前端需要的值
 * @author lhc
 */
@Data
public class UserVo {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 表示微信用户的名称
     */
    private String nickName;

    /**
     * 性别：0 未知， 1男， 1 女
     */
    private Integer gender;

    /**
     * 微信用户的头像地址
     */
    private String avatarUrl;

    /**
     * KB-Token 用户的身份识别
     */
    private String kbToken;
}

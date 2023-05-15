package com.cong.springbootinit.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户详情实体类
 * @author lhc
 * @date 2022-09-20 14:36
 */
@Data
public class UserDetailVo   {
    /**
     * 表示微信用户的名称
     */
    private String nickName;

    /**
     * 性别：0 未知， 1男， 2 女
     */
    private Integer gender;

    /**
     * 微信用户的头像地址
     */
    private String avatarUrl;

    /**
     * 用户编号id主键，自增
     */
    private Long kbId;
    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     *  手机号码
     */
    private String phone;
    /**
     *  邮箱
     */
    private String email;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * 用户出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthdate;

    /**
     * 星座
     */
    private String constellation;
}

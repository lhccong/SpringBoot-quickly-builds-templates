package com.cong.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详细信息表
 * @author lhc
 * @TableName user_detail
 */
@TableName(value ="user_detail")
@Data
public class UserDetail implements Serializable {
    /**
     * 用户编号id主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Long kbId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     *  手机号码
     */
    private String phone;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * 用户出生日期
     */
    private Date birthdate;

    /**
     * 星座
     */
    private String constellation;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除【逻辑删除 1 表示删除】
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
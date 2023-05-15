package com.cong.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信用户信息表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户表id主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信用户的认证 的id，是唯一的
     */
    private String openId;

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
     * 用户的账号状态（1 表示登录 0 表示被禁用）
     */
    private Integer userStatus;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
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
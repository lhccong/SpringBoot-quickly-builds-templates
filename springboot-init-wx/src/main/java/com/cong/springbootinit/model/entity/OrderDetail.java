package com.cong.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情表
 * @TableName order_detail
 */
@TableName(value ="order_detail")
@Data
public class OrderDetail implements Serializable {
    /**
     * 订单详情表的主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 唯一订单编号
     */
    private String orderNo;

    /**
     * 商品的id
     */
    private Long productId;

    /**
     * 商品的数量
     */
    private Integer productNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除【逻辑删除 1 表示删除】
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
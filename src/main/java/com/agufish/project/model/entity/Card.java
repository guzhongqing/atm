package com.agufish.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName card
 */
@TableName(value ="card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 存款类型id
     */
    @TableField(value = "deposit_id")
    private Integer depositId;

    /**
     * 卡号
     */
    @TableField(value = "number")
    private String number;

    /**
     * 密码(默认888888)
     */
    @TableField(value = "password")
    private String password;

    /**
     * 开户日期
     */
    @TableField(value = "open_date")
    private Date openDate;

    /**
     * 开户金额
     */
    @TableField(value = "open_money")
    private BigDecimal openMoney;

    /**
     * 余额
     */
    @TableField(value = "balance")
    private BigDecimal balance;

    /**
     * 是否挂失(0-正常，1-挂失)
     */
    @TableField(value = "is_loss")
    private Integer isLoss;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除(0-未删，1-已删)
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
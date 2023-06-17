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
 * @TableName trade
 */
@TableName(value ="trade")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 卡号
     */
    @TableField(value = "card_id")
    private Integer cardId;

    /**
     * 交易日期
     */
    @TableField(value = "trade_date")
    private Date tradeDate;

    /**
     * 交易金额
     */
    @TableField(value = "trade_money")
    private BigDecimal tradeMoney;

    /**
     * 交易类型
     */
    @TableField(value = "trade_type")
    private String tradeType;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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
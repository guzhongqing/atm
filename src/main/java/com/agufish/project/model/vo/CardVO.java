package com.agufish.project.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行信息表视图类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardVO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 存款类型名称
     */
    private String depositName;

    /**
     * 卡号
     */
    private String number;

    /**
     * 密码(默认888888)
     */
    private String password;

    /**
     * 开户日期
     */
    private Date openDate;

    /**
     * 开户金额
     */
    private BigDecimal openMoney;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 是否挂失(0-正常，1-挂失)
     */
    private Integer isLoss;
    /**
     * 是否挂失(0-正常，1-挂失)
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删，1-已删)
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
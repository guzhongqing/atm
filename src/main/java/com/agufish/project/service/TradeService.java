package com.agufish.project.service;

import com.agufish.project.model.entity.Trade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author acoolfish
 * @description 针对表【trade】的数据库操作Service
 * @createDate 2023-05-30 13:59:46
 */
public interface TradeService extends IService<Trade> {

    /**
     * 存取款
     */
    void depositAndWithdraw();

    /**
     * 统计银行流通余额和盈利
     */
    void countCurrentBalanceAndSurplus();


    /**
     * 转账
     */
    void transfer();
}

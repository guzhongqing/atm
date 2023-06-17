package com.agufish.project.service;

import com.agufish.project.model.entity.Card;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author acoolfish
 * @description 针对表【card】的数据库操作Service
 * @createDate 2023-05-30 13:59:46
 */
public interface CardService extends IService<Card> {


    /**
     * 办理借记卡
     */
    void addCard();

    /**
     * 办理挂失
     */
    void handleLoss();

    /**
     * 查询本周开户的卡号
     */
    void queryCardByOpenDate();

    /**
     * 本月交易金额最高的卡号
     */
    void getNumberByMaxTradeMoney();

    /**
     * 查询存催款账单
     */
    void getBalanceByCardId();

    /**
     * 查询挂失信息
     */
    void getCardByIsLoss();

    /**
     * 重置密码
     */
    void resetPassword();
}

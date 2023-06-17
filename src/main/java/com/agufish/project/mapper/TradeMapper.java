package com.agufish.project.mapper;

import com.agufish.project.model.entity.Trade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @author acoolfish
 * @description 针对表【trade】的数据库操作Mapper
 * @createDate 2023-05-30 13:59:46
 * @Entity Trade
 */
public interface TradeMapper extends BaseMapper<Trade> {


    /**
     * 根据类型获得总银行金额
     *
     * @param tradeType
     * @return
     */
    @Select("select sum(trade_money) from trade where trade_type=#{tradeType}")
    BigDecimal getSumMoneyByTradeType(String tradeType);


    /**
     * 根据最大交易找到careId
     *
     * @return
     */
    @Select("select max(trade_money) as trade_money,card_id from trade where month(trade_date)=month(now())")
    Trade getMaxTradeByCardId();

}





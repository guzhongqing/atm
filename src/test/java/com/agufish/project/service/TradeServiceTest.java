package com.agufish.project.service;

import com.agufish.project.model.entity.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TradeServiceTest {
    @Resource
    private TradeService tradeService;


    @Test
    public void selectAll() {
        for (Trade trade : tradeService.list()) {
            System.out.println(trade);
        }


    }
}
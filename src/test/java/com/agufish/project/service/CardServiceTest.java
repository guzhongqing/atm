package com.agufish.project.service;

import com.agufish.project.model.entity.Card;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CardServiceTest {

    @Resource
    private CardService cardService;


    @Test
    public void selectAll() {
        for (Card card : cardService.list()) {
            System.out.println(card);
        }
    }


}
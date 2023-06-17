package com.agufish.project.service;

import com.agufish.project.model.entity.Deposit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DepositServiceTest {

    @Resource
    private DepositService depositService;


    @Test
    public void selectAll() {
        for (Deposit deposit : depositService.list()) {
            System.out.println(deposit);
        }

    }
}
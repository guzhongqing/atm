package com.agufish.project.service.impl;

import com.agufish.project.mapper.CardMapper;
import com.agufish.project.mapper.TradeMapper;
import com.agufish.project.model.entity.Card;
import com.agufish.project.model.entity.Trade;
import com.agufish.project.service.TradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

/**
 * @author acoolfish
 * @description 针对表【trade】的数据库操作Service实现
 * @createDate 2023-05-30 13:59:46
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade>
        implements TradeService {

    @Resource
    private CardMapper cardMapper;

    @Transactional
    @Override
    public void depositAndWithdraw() {
        Card card = verify();

        if (card == null) {
            System.out.println("银行卡卡号和密码不匹配");
            return;
        }

        // 输出银行卡金额
        System.out.println("您这张卡当前余额为：" + card.getBalance() + "元");

        // 存取款操作
        System.out.println("请选择操作");
        System.out.println("1：存款");
        System.out.println("2：取款");
        System.out.println("3：返回上一级");
        System.out.println("(默认为存款操作)");

        int i = 0;
        try {
            Scanner scanner1 = new Scanner(System.in);
            i = scanner1.nextInt();
        } catch (Exception ignored) {
        }

        switch (i) {
            case 2:
                withdraw(card);
                return;
            case 3:
                return;
            default:
                deposit(card);
        }
    }


    public void deposit(Card card) {
        Scanner scanner1 = new Scanner(System.in);

        System.out.println("请输入存款金额");
        double money = scanner1.nextDouble();

        System.out.println("请输入存备注，按n跳过");
        String remark = scanner1.next();
        if (remark.equals("n")) {
            remark = "";
        }


        Trade trade = new Trade();
        trade.setCardId(card.getId());
        trade.setTradeMoney(new BigDecimal(money));
        trade.setTradeType("存入");
        trade.setRemark(remark);

        this.trade(trade, card);

        System.out.println("交易成功");
        System.out.println("当前余额为：" + card.getBalance() + "元");

    }

    public void withdraw(Card card) {
        Scanner scanner1 = new Scanner(System.in);


        System.out.println("请输入取款金额");
        double money = scanner1.nextDouble();
        double balance = card.getBalance().doubleValue();

        if (balance < money) {
            System.out.println("余额不足，请先充值");
            return;
        }

        System.out.println("请输入取款备注，按n跳过");
        String remark = scanner1.next();
        if (remark.equals("n")) {
            remark = "";
        }

        Trade trade = new Trade();
        trade.setCardId(card.getId());
        trade.setTradeMoney(new BigDecimal(money));
        trade.setTradeType("支取");
        trade.setRemark(remark);

        this.trade(trade, card);


        System.out.println("交易成功");
        System.out.println("当前余额为：" + card.getBalance() + "元");

    }


    public void trade(Trade trade, Card card) {
        /// 插入trade表
        this.save(trade);
        String tradeType = trade.getTradeType();
        double money = trade.getTradeMoney().doubleValue();
        double balance = card.getBalance().doubleValue();

        // 修改余额
        if (tradeType.equals("存入")) {
            balance += money;
        } else {// 支取
            balance -= money;
        }
        card.setBalance(new BigDecimal(balance));

        // 更新card表
        cardMapper.updateById(card);
//
//        // 手动异常
//        int ex = 1 / 0;
    }


    private Card verify() {
        Scanner scanner = new Scanner(System.in);
        // 输入
        System.out.println("请输入银行卡卡号");
        String number = scanner.next();
        System.out.println("请输入银行卡密码");
        String password = scanner.next();
        // 查数据库
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("number", number).eq("password", password);
        return cardMapper.selectOne(cardQueryWrapper);
    }


    @Override
    public void countCurrentBalanceAndSurplus() {
        double income = this.baseMapper.getSumMoneyByTradeType("存入").doubleValue();
        double outcome = this.baseMapper.getSumMoneyByTradeType("支取").doubleValue();

        double bankBalance = income - outcome;
        double surplus = outcome * 0.008 - income * 0.003;

        System.out.printf("资金流通余额" + "%.2f" + "元，盈利结算" + "%.2f" + "元", bankBalance, surplus);

    }

    @Override
    @Transactional
    public void transfer() {
        Card fromCard = verify();

        if (fromCard == null) {
            System.out.println("银行卡卡号和密码不匹配");
            return;
        }

        if (fromCard.getIsLoss() == 1) {
            System.out.println("您的卡号处于挂失状态，无法进行转账业务");
            return;
        }

        // 输出银行卡金额
        System.out.println("您这张卡当前余额为：" + fromCard.getBalance() + "元");


        Scanner scanner = new Scanner(System.in);
        // 输入
        System.out.println("请输入对方银行卡卡号");
        String number = scanner.next();

        // 查数据库
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("number", number);
        Card toCard = cardMapper.selectOne(cardQueryWrapper);


        if (toCard == null) {
            System.out.println("您输入的卡号有误");
            return;
        }


        System.out.println("输入您转账的金额");
        double money = scanner.nextDouble();
        if (fromCard.getBalance().doubleValue() < money) {
            System.out.println("您卡上的余额不足，请充值");
            return;
        }


        Trade fromTrade = new Trade();
        fromTrade.setCardId(fromCard.getId());
        fromTrade.setTradeMoney(new BigDecimal(money));
        fromTrade.setTradeType("支取");
        fromTrade.setRemark(new Date()
                + fromCard.getNumber() + "向" + toCard.getNumber()
                + "转出" + fromTrade.getTradeMoney() + "元");

        Trade toTrade = new Trade();
        toTrade.setCardId(toCard.getId());
        toTrade.setTradeMoney(new BigDecimal(money));
        toTrade.setTradeType("存入");
        toTrade.setRemark(new Date()
                + fromCard.getNumber() + "向" + toCard.getNumber()
                + "转入" + toTrade.getTradeMoney() + "元");


        // 转出记录
        trade(fromTrade, fromCard);

        // 转入记录
        trade(toTrade, toCard);

        System.out.println("交易成功");
        System.out.println("当前余额为：" + fromCard.getBalance() + "元");


    }


}





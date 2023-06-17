package com.agufish.project.service.impl;

import com.agufish.project.mapper.CardMapper;
import com.agufish.project.mapper.DepositMapper;
import com.agufish.project.mapper.TradeMapper;
import com.agufish.project.model.entity.Card;
import com.agufish.project.model.entity.Deposit;
import com.agufish.project.model.entity.Trade;
import com.agufish.project.model.entity.User;
import com.agufish.project.model.vo.CardVO;
import com.agufish.project.service.CardService;
import com.agufish.project.service.UserService;
import com.agufish.project.utils.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * @author acoolfish
 * @description 针对表【card】的数据库操作Service实现
 * @createDate 2023-05-30 13:59:46
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card>
        implements CardService {

    @Resource
    private UserService userService;

    @Resource
    private DepositMapper depositMapper;

    @Resource
    private TradeMapper tradeMapper;


    @Override
    public void addCard() {
        Scanner scanner = new Scanner(System.in);

        User user = userService.verifyUser();
        if (user == null) {
            System.out.println("未开户，请开户");
            return;
        }

        // 生成随机卡号
        String number = "10103576" + RandomUtil.cardinality();

        // 开户金额
        System.out.println("校验成功，请输入开户金额(元)");
        int money = scanner.nextInt();
        if (money < 1) money = 1;


        // 密码校验
        System.out.println("请设置6位密码");
        String password = scanner.next();
        System.out.println("请确定6位密码");
        String verifyPassword = scanner.next();

        while (Strings.isBlank(password)
                || Strings.isBlank(verifyPassword)
                || !password.equals(verifyPassword)) {

            System.out.println("两次密码输入不一致，请重新输入");
            System.out.println("请设置6位密码");
            password = scanner.next();
            System.out.println("请确定6位密码");
            verifyPassword = scanner.next();
        }

        // 存款类型
        System.out.println("请选择存款类型");
        List<Deposit> deposits = depositMapper.selectList(new QueryWrapper<>());
        for (int i = 0; i < deposits.size(); i++) {
            System.out.println((i + 1) + ":" + deposits.get(i).getName() + "\t" + deposits.get(i).getDescription());
        }
        int depositId;
        try {
            depositId = scanner.nextInt();
            if (depositId <= 0 || depositId > deposits.size()) {
                System.out.println("您选择的存款类型不存在，已经默认为编号1类型");
                depositId = 1;
            }
        } catch (Exception e) {
            System.out.println("您选择的存款类型不存在，已经默认为编号1类型");
            depositId = 1;
        }


        // 插入数据库
        Card card = new Card();
        card.setUserId(user.getId());
        card.setDepositId(depositId);
        card.setNumber(number);
        card.setPassword(password);
        card.setOpenMoney(new BigDecimal(String.valueOf(money)));
        card.setBalance(new BigDecimal(String.valueOf(money)));


        boolean save = this.save(card);

        if (save) {
            System.out.println("借记卡办理成功");
            System.out.println("卡号：" + card.getNumber());
            System.out.println("密码：" + card.getPassword());
        } else {
            System.out.println("办理失败，请联系管理员");
        }


    }


    @Override
    public void handleLoss() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入银行卡卡号");
        String number = scanner.next();

        Card card = this.getOne(new QueryWrapper<Card>().eq("number", number));
        if (card == null) {
            System.out.println("卡号错误，请重新输入");
            return;
        }

        card.setIsLoss(1);

        boolean b = this.updateById(card);
        if (b) {
            System.out.println("办理挂失成功");
        } else {
            System.out.println("办理挂失失败，请联系管理员");
        }


    }

    @Override
    public void queryCardByOpenDate() {
        List<CardVO> cardList = this.baseMapper.queryCardByOpenDate();

        System.out.println("卡号\t\t\t\t\t姓名\t\t存储类型\t开户日期\t\t\t\t\t\t\t开户金额\t\t账户状态");

        for (CardVO cardVO : cardList) {
            if (cardVO.getIsLoss() == 0) {
                cardVO.setStatus("正常");
            } else {
                cardVO.setStatus("挂失");
            }
            System.out.println(cardVO.getNumber() + "\t"
                    + cardVO.getUsername() + "\t"
                    + cardVO.getDepositName() + "\t\t"
                    + cardVO.getOpenDate() + "\t"
                    + cardVO.getBalance() + "\t\t"
                    + cardVO.getStatus() + "\t");
        }
    }

    @Override
    public void getNumberByMaxTradeMoney() {
        Trade trade = tradeMapper.getMaxTradeByCardId();
        String number = this.getById(trade.getCardId()).getNumber();
        System.out.println("本月最大交易金额卡号为" + number);
        System.out.println("交易金额为" + trade.getTradeMoney());
    }

    @Override
    public void getBalanceByCardId() {
        List<Card> cardList = this.list(new QueryWrapper<Card>().lt("balance", 5000));
        System.out.println("姓名\t\t余额\t\t联系电话");
        for (Card card : cardList) {
            User user = userService.getById(card.getUserId());
            System.out.println(user.getUsername() + "\t" + card.getBalance() + "\t" + user.getTelephone());
        }
    }

    @Override
    public void getCardByIsLoss() {
        List<CardVO> cardVOList = this.baseMapper.getCardByIsLoss();
        System.out.println("卡号\t\t\t\t\t开户日期\t\t\t\t\t\t\t余额\t\t持卡人姓名");

        for (CardVO cardVO : cardVOList) {
            System.out.println(cardVO.getNumber() + "\t"
                    + cardVO.getOpenDate() + "\t"
                    + cardVO.getBalance() + "\t"
                    + cardVO.getUsername());
        }
    }

    @Override
    public void resetPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入银行卡号");
        String number = scanner.next();
        Card card = this.getOne(new QueryWrapper<Card>().eq("number", number));
        if (card == null) {
            System.out.println("您输入的银行卡号有误，请重新输入");
            return;
        }


        // 验证卡号对应的身份证号是否与输入的身份证号匹配
        System.out.println("请输入身份证号");
        String PID = scanner.next();
        Integer userId = card.getUserId();
        User user = userService.getById(userId);
        if (!PID.equals(user.getPid())) {
            System.out.println("您输入的身份证号有误，请重新输入");
            return;
        }

        // 修改密码
        System.out.println("请设置6位密码");
        String password = scanner.next();
        System.out.println("请确定6位密码");
        String verifyPassword = scanner.next();

        while (Strings.isBlank(password)
                || Strings.isBlank(verifyPassword)
                || !password.equals(verifyPassword)) {

            System.out.println("两次密码输入不一致，请重新输入");
            System.out.println("请设置6位密码");
            password = scanner.next();
            System.out.println("请确定6位密码");
            verifyPassword = scanner.next();
        }

        card.setPassword(password);
        boolean b = this.updateById(card);
        if (b) {
            System.out.println("重置密码成功");
        } else {
            System.out.println("重置密码成功，请联系管理员");
        }

    }
}





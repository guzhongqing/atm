package com.agufish.project.service.impl;

import com.agufish.project.mapper.CardMapper;
import com.agufish.project.mapper.TradeMapper;
import com.agufish.project.mapper.UserMapper;
import com.agufish.project.model.entity.Card;
import com.agufish.project.model.entity.Trade;
import com.agufish.project.model.entity.User;
import com.agufish.project.model.vo.CardVO;
import com.agufish.project.service.UserService;
import com.agufish.project.utils.IdCardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Scanner;

/**
 * @author acoolfish
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-05-30 13:59:46
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    Scanner scanner = new Scanner(System.in);

    @Resource
    private CardMapper cardMapper;
    @Resource
    private TradeMapper tradeMapper;

    @Override
    public void register() {

        System.out.println("请输入您的真实姓名");
        String username = scanner.next();

        System.out.println("请输入您的身份证号码");
        String PID = scanner.next();

        // 校验
        boolean b = IdCardUtil.isIdcard(PID);

        while (!b) {
            System.out.println("身份证号码错误，重新输入");
            PID = scanner.next();
            b = IdCardUtil.isIdcard(PID);
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("PID", PID);
        long count = this.count(userQueryWrapper);
        if (count > 0) {
            System.out.println("账户已存在，不能重复开户");
            return;
        }

        System.out.println("请输入您的电话号码");
        String telephone = scanner.next();

        boolean flag = false;
        for (char c : telephone.toCharArray()) {
            if (!Character.isDigit(c)) {
                flag = true;
                break;
            }
        }
        while (telephone.length() < 8 || flag) {
            System.out.println("电话号码错误，重新输入");
            telephone = scanner.next();
        }

        System.out.println("请输入您的居住地址，按N跳过");
        String address = scanner.next();

        if (address.trim().equalsIgnoreCase("n")) {
            address = "";
        }

        User user = new User();
        user.setUsername(username);
        user.setPid(PID);
        user.setTelephone(telephone);
        user.setAddress(address);
        boolean save = this.save(user);
        if (save) {
            System.out.println("开户成功");
        } else {
            System.out.println("开户失败");
        }
    }

    @Override
    public void queryInfo() {
        User user = this.verifyUser();
        if (user == null) {
            System.out.println("账户有误，请重新输入");
            return;
        }

        System.out.println("您在本行的银行卡信息如下");

        Integer userId = user.getId();
        List<CardVO> cardVOList = cardMapper.getInfoByUserId(userId);
        System.out.println("卡号\t\t\t\t\t持卡人姓名\t存款类型\t开户日期\t\t\t\t\t\t\t余额\t\t是否挂失");

        for (CardVO cardVO : cardVOList) {
            if (cardVO.getIsLoss() == 0) {
                cardVO.setStatus("否");
            } else {
                cardVO.setStatus("是");
            }

            System.out.println(cardVO.getNumber() + "\t"
                    + cardVO.getUsername() + "\t\t"
                    + cardVO.getDepositName() + "\t\t"
                    + cardVO.getOpenDate() + "\t"
                    + cardVO.getBalance() + "\t"
                    + cardVO.getStatus());
        }


        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("user_id", userId));
        List<Trade> tradeList = tradeMapper.selectList(new QueryWrapper<Trade>().eq("card_id", card.getId()));

        System.out.println("交易记录如下");
        System.out.println("交易日期\t\t\t\t\t\t\t交易金额\t交易类型\t备注");

        for (Trade trade : tradeList) {

            System.out.println(trade.getTradeDate() + "\t"
                    + trade.getTradeMoney() + "\t"
                    + trade.getTradeType() + "\t\t"
                    + trade.getRemark());

        }

    }


    @Nullable
    public User verifyUser() {
        Scanner scanner = new Scanner(System.in);
        // 校验
        System.out.println("请输入身份证号码");
        String PID = "";
        boolean flag = false;
        for (int i = 3; i > 0; i--) {
            PID = scanner.next();
            // 校验
            if (!IdCardUtil.isIdcard(PID)) {
                System.out.println("身份证号码错误，您还有" + (i - 1) + "次机会");
            } else {
                flag = true;
                break;
            }
        }
        if (!flag) return null;

        // 查数据库
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("PID", PID);
        return this.getOne(userQueryWrapper);
    }
}





package com.agufish.project.view;

import com.agufish.project.service.CardService;
import com.agufish.project.service.TradeService;
import com.agufish.project.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Scanner;

@Component
public class Menu {

    @Resource
    private UserService userService;

    @Resource
    private CardService cardService;

    @Resource
    private TradeService tradeService;

    public void mainMenu() {
        while (true) {
            System.out.println("——————————欢迎光临南京特殊教育师范学院银行——————————");
            System.out.println("1：银行开户");
            System.out.println("2：业务办理");
            System.out.println("3：账户设置");
            System.out.println("4：退出");

            int i = 0;
            try {
                Scanner scanner = new Scanner(System.in);
                i = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("请重新输入");
            }

            switch (i) {
                case 1:
                    register();
                    break;
                case 2:
                    businessMenu();
                    break;
                case 3:
                    userSetting();
                    break;
                case 4:
                    System.out.println("感谢使用本系统，欢迎下次再次光临");
                    System.exit(0);
                    break;
            }
        }
    }


    public void register() {
        userService.register();
    }


    private void businessMenu() {
        while (true) {
            System.out.println("请选择你要办理的业务");
            System.out.println("1：办理借记卡");
            System.out.println("2：存取款");
            System.out.println("3：办理挂失");
            System.out.println("4：统计银行资金流通余额和盈利");
            System.out.println("5：查询本周开户的卡号");
            System.out.println("6：查询本月交易金额最高的卡号");
            System.out.println("7：查询催款账单");
            System.out.println("8：转账");
            System.out.println("9：查询挂失信息");
            System.out.println("10：返回上一级");

            int i = 0;
            try {
                Scanner scanner = new Scanner(System.in);
                i = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("请重新输入");
            }

            switch (i) {
                case 1:
                    cardService.addCard();
                    break;
                case 2:
                    try {
                        tradeService.depositAndWithdraw();
                    } catch (Exception e) {
                        System.out.println("交易失败");
                    }
                    break;
                case 3:
                    cardService.handleLoss();
                    break;
                case 4:
                    tradeService.countCurrentBalanceAndSurplus();
                    break;
                case 5:
                    cardService.queryCardByOpenDate();
                    break;
                case 6:
                    cardService.getNumberByMaxTradeMoney();
                    break;
                case 7:
                    cardService.getBalanceByCardId();
                    break;
                case 8:
                    try {
                        tradeService.transfer();
                    } catch (Exception e) {
                        System.out.println("转账失败");
                    }
                    break;
                case 9:
                    cardService.getCardByIsLoss();
                    break;
                case 10:
                    return;

            }
        }
    }


    private void userSetting() {
        while (true) {
            System.out.println("请选择你要办理的业务");
            System.out.println("1：重置密码");
            System.out.println("2：账户信息");
            System.out.println("3：返回上一级");

            int i = 0;
            try {
                Scanner scanner = new Scanner(System.in);
                i = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("请重新输入");
            }

            switch (i) {
                case 1:
                    cardService.resetPassword();
                    break;
                case 2:
                    userService.queryInfo();
                    break;
                case 3:
                    return;
            }
        }
    }


}



package com.agufish.project.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author QiFeng·Luo
 */
public class IdCardUtil {

    /**
     * 数字
     */
    public final static Pattern NUMBERS = Pattern.compile("\\d+");

    /**
     * 中国公民身份证号码最小长度。
     */
    private static final int CHINA_ID_MIN_LENGTH = 15;

    /**
     * 中国公民身份证号码最大长度。
     */
    private static final int CHINA_ID_MAX_LENGTH = 18;

    public static Exception isValidatedAllIdcard(String idcard) throws Exception {
        boolean ret = isIdcard(idcard);
        if (!ret) {
            throw new Exception("身份证格式有误");
        }
        return null;
    }

    final static Map<Integer, String> zoneNum = new HashMap<>();

    /**
     * 身份证省份编码
     * */
    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "国外");
    }

    /**
     * 校验码
     */
    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /**
     * 加权因子wi
     */
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 验证身份证号有效性
     *
     * @param idCard:身份证号
     * @return true/false
     */
    public static boolean isIdcard(String idCard) {
        // 号码长度应为15位或18位
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)) {
            return false;
        }

        // 校验位数
        int power = 0;
        final char[] cs = idCard.toUpperCase().toCharArray();
        for (int i = 0; i < cs.length; i++) {// 循环比正则表达式更快
            if (i == cs.length - 1 && cs[i] == 'X') {
                break;// 最后一位可以是X或者x
            }
            if (cs[i] < '0' || cs[i] > '9') {
                return false;
            }
            if (i < cs.length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }


        // 校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(idCard.substring(0, 2)))) {
            return false;
        }
        // 校验年份
        String year = idCard.length() == 15 ? "19" + idCard.substring(6, 8) : idCard.substring(6, 10);
        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR)) {
            // 1900年的PASS，超过今年的PASS
            return false;
        }
        // 校验月份
        String month = idCard.length() == 15 ? idCard.substring(8, 10) : idCard.substring(10, 12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }
        // 校验天数
        String day = idCard.length() == 15 ? idCard.substring(10, 12) : idCard.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31) {
            return false;
        }
        // 校验一个合法的年月日
        if (!isValidDate(year + month + day)) {
            return false;
        }

        // 校验“校验码”
        if (idCard.length() == 15) {
            return true;
        }
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    /**
     * 判断字符串是否为日期格式(合法)
     *
     * @param inDate:字符串时间
     * @return true/false
     */
    public static boolean isValidDate(String inDate) {
        if (inDate == null) {
            return false;
        }
        // 或yyyy-MM-dd
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        if (inDate.trim().length() != dataFormat.toPattern().length()) {
            return false;
        }
        // 该方法用于设置Calendar严格解析字符串;默认为true,宽松解析
        dataFormat.setLenient(false);
        try {
            dataFormat.parse(inDate.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 转换成日期
     *
     * @param birthday
     * @return
     */
    private static Date toBirthDay(String birthday) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(birthday.substring(0, 4)));
            // 月份从0开始，所以减1
            calendar.set(Calendar.MONTH, Integer.parseInt(birthday.substring(4, 6)) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(birthday.substring(6, 8)));
            // 以下设置时分秒，但是对生日的意义不大
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    private static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }

    /**
     * 将字符串转换成指定格式的日期
     *
     * @param str        日期字符串.
     * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
     * @return
     */
    private static Date strToDate(final String str, String dateFormat) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        try {
            if (dateFormat == null || dateFormat.length() == 0) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            DateFormat fmt = new SimpleDateFormat(dateFormat);
            return fmt.parse(str.trim());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 根据日期获取年
     *
     * @param date 日期
     * @return 年的部分
     */
    public static int year(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.YEAR);
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param iSum 加权和
     * @return 校验位
     */
    private static char getCheckCode18(int iSum) {
        switch (iSum % 11) {
            case 10:
                return '2';
            case 9:
                return '3';
            case 8:
                return '4';
            case 7:
                return '5';
            case 6:
                return '6';
            case 5:
                return '7';
            case 4:
                return '8';
            case 3:
                return '9';
            case 2:
                return 'x';
            case 1:
                return '0';
            case 0:
                return '1';
            default:
                return ' ';
        }
    }

    /**
     * 获得18位身份证校验码
     * 计算方式：
     * 将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 将这17位数字和系数相乘的结果相加
     * 用加出来和除以11，看余数是多少
     * 余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2
     * 通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2
     *
     * @param code17 18位身份证号中的前17位
     * @return 第18位
     */
    private static char getCheckCode18(String code17) {
        int sum = getPowerSum(code17.toCharArray());
        return getCheckCode18(sum);
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param iArr 身份证号码的数组
     * @return 身份证编码
     */
    private static int getPowerSum(char[] iArr) {
        int iSum = 0;
        if (POWER_LIST.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                iSum += Integer.valueOf(String.valueOf(iArr[i])) * POWER_LIST[i];
            }
        }
        return iSum;
    }

    /**
     * 将15位身份证号码转换为18位
     *
     * @param idCard 15位身份编码
     * @return 18位身份编码
     */
    public static String convertIdCard(String idCard) {
        StringBuilder idCard18;
        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
            return null;
        }
        if (isMatch(NUMBERS, idCard)) {
            // 获取出生年月日
            String birthday = idCard.substring(6, 12);
            Date birthDate = strToDate(birthday, "yyMMdd");
            // 获取出生年
            int sYear = year(birthDate);
            // 理论上2000年之后不存在15位身份证，可以不要此判断
            if (sYear > 2000) {
                sYear -= 100;
            }
            idCard18 = new StringBuilder().append(idCard, 0, 6).append(sYear).append(idCard.substring(8));
            // 获取校验位
            char sVal = getCheckCode18(idCard18.toString());
            idCard18.append(sVal);
        } else {
            return null;
        }
        return idCard18.toString();
    }

    /**
     * 从身份证号码中获取生日
     *
     * @param idno
     * @return null表示idno错误，未获取到生日
     */
    public static Date getBirthDay(String idno) {
        if (!isIdcard(idno)) {
            return null;
        }
        if (idno.length() == 15) {
            // 如果是15位转为18位
            idno = convertIdCard(idno);
        }
        return toBirthDay(idno.substring(6, 14));
    }

    /**
     * 从身份证号码中获取生日
     *
     * @param idno
     * @return null表示idno错误，未获取到生日 日期格式为：yyyy-MM-dd
     */
    public static String getBirthDayStr(String idno) {
        if (!isIdcard(idno)) {
            return null;
        }
        if (idno.length() == 15) {
            // 如果是15位转为18位
            idno = convertIdCard(idno);
        }
        Date birthday = toBirthDay(idno.substring(6, 14));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(birthday);
    }

    /**
     * 从身份证号中获取性别
     *
     * @param idno
     * @return 0:男，1:女，-1:证件号码错误
     */
    public static String getGender(String idno) {
        if (!isIdcard(idno)) {
            return "-1";
        }
        if (idno.length() == 15) {
            // 如果是15位转为18位
            idno = convertIdCard(idno);
        }
        // 奇男，偶女
        return (Integer.parseInt(idno.substring(16, 17)) % 2) == 0 ? "1" : "0";
    }

    /**
     * 方法调用测试
     * */
    //public static void main(String[] args) {
    //    String idc= "320114199507233313";
    //    //检查身份证是否合规
    //    boolean idcard = isIdcard(idc);
    //    if (idcard) {
    //        System.out.println("身份证号码合规");
    //        // 获取身份证号码中的生日
    //        Date birthDay = getBirthDay(idc);
    //        System.out.println("当前身份证的生日为："+ getBirthDayStr(idc));
    //        // 获取性别
    //        String gender = getGender(idc);
    //        if ("0".equals(gender)) {
    //            System.out.println("当前身份证的性别为：男性");
    //        } else if ("1".equals(gender)) {
    //            System.out.println("当前身份证的性别为：女性");
    //        } else {
    //            System.out.println("当前身份证格式不正确");
    //        }
    //    }else {
    //        System.out.println("身份证格式有误");
    //    }
    //}

}

